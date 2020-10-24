package at.willhaben.dt.snowpit.controller


import at.willhaben.dt.snowpit.converter.convert
import at.willhaben.dt.snowpit.service.DtSpecYamlService
import at.willhaben.dt.snowpit.service.model.dtspec.DtSpecYaml
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.getValue
import tornadofx.setValue
import java.io.File

class MainFormController : Controller() {

    private val dtSpecYamlService = DtSpecYamlService()

    val selectedTargetProperty = SimpleStringProperty()
    var selectedTarget by selectedTargetProperty


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