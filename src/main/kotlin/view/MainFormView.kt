package at.willhaben.dt.snowpit.view


import at.willhaben.dt.snowpit.DbtProfileException
import at.willhaben.dt.snowpit.controller.MainFormController
import at.willhaben.dt.snowpit.view.document.fragments.DtSpecDocumentFragment
import at.willhaben.dt.snowpit.view.document.model.DbtTargetViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.TabPane
import javafx.scene.image.ImageView
import tornadofx.*

class MainFormView : View("Snowpit - DtSpec Yaml Editor", ImageView(Icons.AppIcon)) {

    private val controller: MainFormController by inject()

    private val tabPane = TabPane().apply { fitToParentWidth() }

    init {
        try {
            controller.reloadProfiles()
        } catch (e: DbtProfileException) {
            alert(
                    type = Alert.AlertType.ERROR,
                    title = "Failed loading dbt profiles!",
                    header = e.message ?: e.toString(),
                    content = "Check preferences to configure the dbt_profiles.yml location.",
                    buttons = *arrayOf(ButtonType.OK)
            )
        }

    }

    override val root = form {
        setPrefSize(1280.0, 720.0)

        menubar {
            menu("File") {
                item(name = "New", keyCombination = "Shortcut+N", graphic = ImageView(Icons.IconNew)).action {
                    menuItemNewHandler()
                }

                item(name = "Open", keyCombination = "Shortcut+O", graphic = ImageView(Icons.IconOpen)).action {
                    menuItemOpenHandler()
                }

                item(name = "Save", keyCombination = "Shortcut+S", graphic = ImageView(Icons.IconSave)).action {
                    menuItemSaveHandler()
                }

                item(name = "Save as ...", keyCombination = "Shift+Shortcut+N", graphic = ImageView(Icons.IconSaveAs)).action {
                    menuItemSaveAsHandler()
                }

                separator()

                item(name = "Preferences", keyCombination = "Shortcut+P", graphic = ImageView(Icons.IconPreferences)).action {
                    menuItemPreferencesHandler()
                }

                separator()

                item(name = "Quit", keyCombination = "Shortcut+Q", graphic = ImageView(Icons.IconClose)).action {
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
        vbox {
            fitToParentWidth()
            hbox {

                buttonbar {
                    button {
                        graphic = ImageView(Icons.IconNew)
                        tooltip("New")
                    }.action { menuItemNewHandler() }

                    button {
                        graphic = ImageView(Icons.IconOpen)
                        tooltip("Open DtSpec Yaml...")
                    }.action { menuItemOpenHandler() }

                    button {
                        graphic = ImageView(Icons.IconSave)
                        tooltip("Save")
                    }.action { menuItemSaveHandler() }

                    button {
                        graphic = ImageView(Icons.IconSaveAs)
                        tooltip("Save as...")
                    }.action { menuItemSaveAsHandler() }

                    button {
                        graphic = ImageView(Icons.IconPreferences)
                        tooltip("Preferences...")
                    }

                    button {
                        graphic = ImageView(Icons.IconClose)
                        tooltip("Close")
                    }.action { menuItemCloseHandler() }
                }

            }
            hbox(spacing = 8) {

                label(text = "Profile: ")
                val profilesCombobox = combobox<DbtTargetViewModel> {
                    items = controller.targets
                    alignment = Pos.CENTER_LEFT
                    cellFormat {
                        text = it.name
                    }
                    bindSelected(controller.selectedTarget)
                }
                button(text = "Fetch/Refresh Metadata", graphic = ImageView(Icons.IconConnect)) {
                    enableWhen { controller.selectedTarget.isNotNull }
                }.action {
                    controller.reloadProfiles()
                }
            }
            this += tabPane.apply { fitToParentSize() }
        }
    }

    private fun menuItemCloseHandler() {
        controller.quitApp()
    }

    private fun menuItemPreferencesHandler() {

    }

    private fun menuItemSaveAsHandler() {
        if (tabPane.tabs.isNotEmpty()) {
            val selectedTab = tabPane.tabs.first { it.isSelected }
            val dtSpecYamlViewModel = selectedTab.userData as DtSpecViewModel
            controller.saveFile(dtSpecYamlViewModel, promptFilename = true)
        }
    }

    private fun menuItemSaveHandler() {
        if (tabPane.tabs.isNotEmpty()) {
            val selectedTab = tabPane.tabs.first { it.isSelected }
            val promptForFilename = (selectedTab.text.contains("<"))
            val dtSpecYamlViewModel = selectedTab.userData as DtSpecViewModel
            controller.saveFile(dtSpecYamlViewModel, promptForFilename)
        }
    }

    private fun menuItemOpenHandler() {
        val dtSpecYamlViewModel = controller.openFile()
        if (dtSpecYamlViewModel != null) {
            val newTab = tabPane.tab("<${dtSpecYamlViewModel.filename}> *")
            newTab.userData = dtSpecYamlViewModel
            newTab.add(DtSpecDocumentFragment(dtSpecYamlViewModel))
            newTab.select()
        }
    }

    private fun menuItemNewHandler() {
        val name = "new ${tabPane.tabs.size}"
        val dtSpecYamlViewModel = controller.newFile("$name.yml")
        val newTab = tabPane.tab("<$name> *")
        newTab.userData = dtSpecYamlViewModel
        newTab.add(DtSpecDocumentFragment(dtSpecYamlViewModel))
        newTab.select()
    }
}