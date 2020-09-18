package at.willhaben.dt.snowpit.view


import at.willhaben.dt.snowpit.controller.MainFormController
import at.willhaben.dt.snowpit.view.model.DtSpecYamlViewModel
import javafx.scene.control.TabPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import tornadofx.*

class MainFormView : View("Snowpit - DtSpec Yaml Editor", ImageView(Image("icon/app_icon.png"))) {

    private val controller: MainFormController by inject()

    val tabPane = TabPane()


    override val root = form {
        setPrefSize(1280.0, 720.0)

        menubar {
            menu("File") {
               item(name = "New", keyCombination = "Shortcut+N", graphic = ImageView(Image("icon/new.png"))).action {
                   menuItemNewHandler()
               }

                item(name = "Open", keyCombination = "Shortcut+O", graphic = ImageView(Image("icon/open.png"))).action {
                    menuItemOpenHandler()
                }

                item(name = "Save", keyCombination = "Shortcut+S", graphic = ImageView(Image("icon/save.png"))).action {
                    menuItemSaveHandler()
                }

                item(name = "Save as ...", keyCombination = "Shift+Shortcut+N", graphic = ImageView(Image("icon/save-as.png"))).action {
                    menuItemSaveAsHandler()
                }

                separator()

                item(name = "Preferences", keyCombination = "Shortcut+P", graphic = ImageView(Image("icon/gear.png"))).action {
                    menuItemPreferencesHandler()
                }

                separator()

                item(name = "Quit", keyCombination = "Shortcut+Q", graphic = ImageView(Image("icon/close.png"))).action {
                    menuItemCloseHandler()
                }

                setOnShowing { _ ->
                    val canSave = tabPane.tabs.isNotEmpty()
                    this.items
                            .filter { it.text != null && it.text.startsWith("Save") }
                            .forEach { it.isDisable = !canSave }
                }
            }
        }
        hbox {
            button {
                graphic = ImageView(Image("icon/new.png"))
                tooltip("New")
            }.action { menuItemNewHandler() }

            button {
                graphic = ImageView(Image("icon/open.png"))
                tooltip("Open DtSpec Yaml...")
            }.action { menuItemOpenHandler() }

            button {
                graphic = ImageView(Image("icon/save.png"))
                tooltip("Save")
            }.action { menuItemSaveHandler() }

            button {
                graphic = ImageView(Image("icon/save-as.png"))
                tooltip("Save as...")
            }.action { menuItemSaveAsHandler() }

            button {
                graphic = ImageView(Image("icon/gear.png"))
                tooltip("Preferences...")
            }

            button {
                graphic = ImageView(Image("icon/close.png"))
                tooltip("Close")
            }.action { menuItemCloseHandler() }
        }
        this += tabPane.apply { fitToParentSize() }

    }

    private fun menuItemCloseHandler() {
        controller.quitApp()
    }

    private fun menuItemPreferencesHandler() {

    }

    private fun menuItemSaveAsHandler() {
        if (tabPane.tabs.isNotEmpty()) {
            val selectedTab = tabPane.tabs.first { it.isSelected }
            val dtSpecYamlViewModel = selectedTab.userData as DtSpecYamlViewModel
            controller.saveFile(dtSpecYamlViewModel, promptFilename = true)
        }
    }

    private fun menuItemSaveHandler() {
        if (tabPane.tabs.isNotEmpty()) {
            val selectedTab = tabPane.tabs.first { it.isSelected }
            val promptForFilename = (selectedTab.text.contains("<"))
            val dtSpecYamlViewModel = selectedTab.userData as DtSpecYamlViewModel
            controller.saveFile(dtSpecYamlViewModel, promptForFilename)
        }
    }

    private fun menuItemOpenHandler() {
        val dtSpecYamlViewModel = controller.openFile()
        if (dtSpecYamlViewModel != null) {
            val newTab = tabPane.tab("<${dtSpecYamlViewModel.filename}> *")
            newTab.userData = dtSpecYamlViewModel
            newTab.add(DtSpecYamlFragment(dtSpecYamlViewModel))
            newTab.select()
        }
    }

    private fun menuItemNewHandler() {
        val name = "new ${tabPane.tabs.size}"
        val dtSpecYamlViewModel = controller.newFile("$name.yml")
        val newTab = tabPane.tab("<$name> *")
        newTab.userData = dtSpecYamlViewModel
        newTab.add(DtSpecYamlFragment(dtSpecYamlViewModel))
        newTab.select()
    }
}