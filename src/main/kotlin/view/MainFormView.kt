package at.willhaben.dt.snowpit.view


import at.willhaben.dt.snowpit.controller.MainFormController
import at.willhaben.dt.snowpit.view.model.DtSpecYamlViewModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.scene.control.TabPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.controlsfx.glyphfont.FontAwesome
import tornadofx.*

class MainFormView : View("Snowpit - DtSpec Yaml Editor", ImageView(Image("icon/app_icon.png"))) {

    private val controller: MainFormController by inject()

    val tabPane = TabPane()

    override val root = form {
        setPrefSize(1280.0, 720.0)

        menubar {
            menu("File") {
                item(name = "New", keyCombination = "Shortcut+N", graphic = FontAwesome().create(FontAwesomeIcon.FILE)).action {
                    val name = "new ${tabPane.tabs.size}"
                    val dtSpecYamlViewModel = controller.newFile("$name.yml")
                    val newTab = tabPane.tab("<$name> *")
                    newTab.userData = dtSpecYamlViewModel
                    newTab.add(DtSpecYamlFragment(dtSpecYamlViewModel))
                    newTab.select()
                }

                item(name = "Open", keyCombination = "Shortcut+O", graphic = FontAwesome().create(FontAwesomeIcon.FOLDER_OPEN)).action {
                    val dtSpecYamlViewModel = controller.openFile()
                    if (dtSpecYamlViewModel != null) {
                        val newTab = tabPane.tab("<${dtSpecYamlViewModel.filename}> *")
                        newTab.userData = dtSpecYamlViewModel
                        newTab.add(DtSpecYamlFragment(dtSpecYamlViewModel))
                        newTab.select()
                    }
                }

                item(name = "Save", keyCombination = "Shortcut+S", graphic = FontAwesome().create(FontAwesomeIcon.SAVE)).action {
                    if (tabPane.tabs.isNotEmpty()) {
                        val selectedTab = tabPane.tabs.first { it.isSelected }
                        val promptForFilename = (selectedTab.text.contains("<"))
                        val dtSpecYamlViewModel = selectedTab.userData as DtSpecYamlViewModel
                        controller.saveFile(dtSpecYamlViewModel, promptForFilename)
                    }
                }

                item(name = "Save as ...", keyCombination = "Shift+Shortcut+N", graphic = FontAwesome().create(FontAwesomeIcon.DOWNLOAD)).action {
                    if (tabPane.tabs.isNotEmpty()) {
                        val selectedTab = tabPane.tabs.first { it.isSelected }
                        val dtSpecYamlViewModel = selectedTab.userData as DtSpecYamlViewModel
                        controller.saveFile(dtSpecYamlViewModel, promptFilename = true)
                    }
                }

                separator()

                item(name = "Preferences", keyCombination = "Shortcut+P", graphic = FontAwesome().create(FontAwesomeIcon.LIST_ALT))

                separator()

                item(name = "Quit", keyCombination = "Shortcut+Q", graphic = FontAwesome().create(FontAwesomeIcon.CLOSE)).action {
                    controller.quitApp()
                }

                setOnShowing { _ ->
                    val canSave = tabPane.tabs.isNotEmpty()
                    this.items
                            .filter { it.text != null && it.text.startsWith("Save") }
                            .forEach { it.isDisable = !canSave }
                }
            }
        }
        this += tabPane.apply { fitToParentSize() }

    }
}