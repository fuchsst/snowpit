package at.willhaben.dt.snowpit.controller


import at.willhaben.dt.snowpit.DbtProfileException
import at.willhaben.dt.snowpit.converter.convert
import at.willhaben.dt.snowpit.service.DbtProfilesService
import at.willhaben.dt.snowpit.service.DtSpecYamlService
import at.willhaben.dt.snowpit.service.PreferencesService
import at.willhaben.dt.snowpit.service.model.dtspec.DtSpecYaml
import at.willhaben.dt.snowpit.view.document.model.DbtTargetViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.application.Platform
import javafx.beans.property.SimpleObjectProperty
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class MainFormController : Controller() {

    private val preferencesService = PreferencesService()
    private val dbtProfilesService = DbtProfilesService()
    private val dtSpecYamlService = DtSpecYamlService()

    val targets = mutableListOf<DbtTargetViewModel>().asObservable()
    val selectedTarget = SimpleObjectProperty<DbtTargetViewModel>()


    fun reloadProfiles() {
        val dbtProfilesYamlFile = File(preferencesService.dbtProfilesYamlPath)
        if (dbtProfilesYamlFile.exists()) {
            val profiles = dbtProfilesService.loadTargetProfiles(dbtProfilesYamlFile.inputStream())
            val profile = profiles.find { it.name == preferencesService.dbtProfile }?.convert()
            if (profile != null) {
                targets.clear()
                targets.addAll(profile.outputs)
            } else {
                throw DbtProfileException("The configured Profile '${preferencesService.dbtProfile}' not found in '${preferencesService.dbtProfilesYamlPath}'!")
            }
        } else {
            throw DbtProfileException("The dbt_profiles.yml configuration file '${preferencesService.dbtProfilesYamlPath}' does not exist!")
        }

    }

    fun newFile(filename: String) = DtSpecYaml(
            description = "DtSpec Test Specification",
            identifiers = mutableListOf(),
            sources = mutableListOf(),
            targets = mutableListOf(),
            scenarios = mutableListOf()
    ).convert(filename)

    fun openFile(): DtSpecViewModel? {
        val fileChooser = FileChooser().apply {
            extensionFilters.add(FileChooser.ExtensionFilter("DtSpec Yaml", "*.yml"))
            title = "Open DtSpec Yaml..."
        }

        val file = fileChooser.showOpenDialog(null)
        return if (file != null) {
            val dtSpecYaml = dtSpecYamlService.loadDtSpecYaml(file)
            dtSpecYaml.convert(file.name)
        } else {
            null
        }

    }

    fun saveFile(dtSpecViewModel: DtSpecViewModel, promptFilename: Boolean = false) {
        val file = if (promptFilename) {
            val fileChooser = FileChooser().apply {
                extensionFilters.add(FileChooser.ExtensionFilter("DtSpec Yaml", "*.yml"))
                title = "Save DtSpec Yaml..."
                initialFileName = dtSpecViewModel.filename
            }
            fileChooser.showSaveDialog(null)
        } else {
            File(dtSpecViewModel.filename)
        }
        if (file != null)
            dtSpecYamlService.saveDtSpecYaml(file, dtSpecViewModel.convert())
    }

    fun quitApp() {
        Platform.exit()
    }

}