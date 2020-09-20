package at.willhaben.dt.snowpit.controller


import at.willhaben.dt.snowpit.converter.convert
import at.willhaben.dt.snowpit.service.DtSpecYamlService
import at.willhaben.dt.snowpit.service.model.DtSpecYaml
import at.willhaben.dt.snowpit.view.document.model.DtSpecYamlViewModel
import javafx.application.Platform
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class MainFormController : Controller() {

    private val dtSpecYamlService = DtSpecYamlService()

    fun newFile(filename: String) = DtSpecYaml(
            description = "DtSpec Test Specification",
            identifiers = mutableListOf(),
            sources = mutableListOf(),
            targets = mutableListOf(),
            scenarios = mutableListOf()
    ).convert(filename)

    fun openFile(): DtSpecYamlViewModel? {
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

    fun saveFile(dtSpecYamlViewModel: DtSpecYamlViewModel, promptFilename: Boolean = false) {
        val file = if (promptFilename) {
            val fileChooser = FileChooser().apply {
                extensionFilters.add(FileChooser.ExtensionFilter("DtSpec Yaml", "*.yml"))
                title = "Save DtSpec Yaml..."
                initialFileName = dtSpecYamlViewModel.filename
            }
            fileChooser.showSaveDialog(null)
        } else {
            File(dtSpecYamlViewModel.filename)
        }
        if (file != null)
            dtSpecYamlService.saveDtSpecYaml(file, dtSpecYamlViewModel.convert())
    }

    fun quitApp() {
        Platform.exit()
    }

}