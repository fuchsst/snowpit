package at.willhaben.dt.snowpit.view


import at.willhaben.dt.snowpit.controller.MainFormController
import at.willhaben.dt.snowpit.controller.MetadataController
import at.willhaben.dt.snowpit.controller.PreferencesController
import at.willhaben.dt.snowpit.view.document.fragments.DtSpecDocumentFragment
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.TabPane
import javafx.scene.image.ImageView
import javafx.stage.Modality
import tornadofx.*
import java.util.logging.Level

class MainFormView : View("Snowpit - DtSpec Yaml Editor", ImageView(Icons.AppIcon)) {

    private val metadataController: MetadataController by inject(scope)
    private val preferencesController: PreferencesController by inject(scope)
    private val mainFormController: MainFormController by inject(scope)

    private val preferencesView: PreferencesView by inject(scope)

    private val tabPane = TabPane().apply { fitToParentWidth() }

    init {
        try {
            metadataController.reloadProfiles()
            metadataController.reloadTargets()
        } catch (e: Exception) {
            alert(
                    type = Alert.AlertType.ERROR,
                    title = "Failed loading dbt profiles!",
                    header = e.message ?: e.toString(),
                    content = "Check preferences to configure the dbt_profiles.yml.",
                    buttons = arrayOf(ButtonType.OK)
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

                item(name = "Save as ...", keyCombination = "Shift+Shortcut+S", graphic = ImageView(Icons.IconSaveAs)).action {
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
                    }.action { menuItemPreferencesHandler() }

                    button {
                        graphic = ImageView(Icons.IconClose)
                        tooltip("Close")
                    }.action { menuItemCloseHandler() }
                }

            }
            hbox(spacing = 8) {

                label(text = "Target: ")
                val targetsCombobox = combobox<String>(
                        property = preferencesController.dbtProfileTargetProperty,
                        values = metadataController.dbtProfileTargetList
                ) {
                    alignment = Pos.CENTER_LEFT
                }.setOnAction {
                    try {
                        preferencesController.save()
                    } catch (npe: NullPointerException) {
                        log.log(Level.WARNING, "NPE when trying to save preferences targetsCombobox.onAction")
                    }

                }
                button(text = "Fetch/Refresh Metadata", graphic = ImageView(Icons.IconConnect)) {
                    enableWhen { preferencesController.dbtProfileTargetProperty.isNotNull }
                }.action {
                    handleRefreshTableMetadata()
                }
            }
            this += tabPane.apply { fitToParentSize() }
        }
    }

    private fun handleRefreshTableMetadata() {
        try {
            metadataController.reloadDbTableMetadata()

            val numSchemas = metadataController.dbTableMetadataList.count()
            val numTables = metadataController.dbTableMetadataList.count()
            val dbName = metadataController.dbTableMetadataList.first().database

            alert(
                    type = Alert.AlertType.INFORMATION,
                    title = "Metadata loaded",
                    header = "Loaded Table Metadata from ${preferencesController.dbtProfile}.${preferencesController.dbtProfileTarget}",
                    content = "Fetched $numTables tables in $numSchemas schemas from database $dbName"
            )
        } catch (e: Exception) {
            alert(
                    type = Alert.AlertType.ERROR,
                    title = "Failed loading DB table metadata for profile target " +
                            "${preferencesController.dbtProfile}.${preferencesController.dbtProfileTarget}!",
                    header = e.message ?: e.toString(),
                    content = "Check preferences to configure the dbt_profiles.yml location and profile " +
                            "and check in the dbt_profiles.yml that the target is configured correctly."
            )
        }
    }

    private fun menuItemCloseHandler() {
        mainFormController.quitApp()
    }

    private fun menuItemPreferencesHandler() {
        preferencesView.openModal(
                owner = this.currentWindow,
                modality = Modality.APPLICATION_MODAL,
                escapeClosesWindow = true,
                block = true)
        metadataController.reloadTargets()
    }

    private fun menuItemSaveAsHandler() {
        if (tabPane.tabs.isNotEmpty()) {
            val selectedTab = tabPane.tabs.first { it.isSelected }
            val dtSpecYamlViewModel = selectedTab.userData as DtSpecViewModel
            mainFormController.saveFile(dtSpecYamlViewModel, promptFilename = true)
        }
    }

    private fun menuItemSaveHandler() {
        if (tabPane.tabs.isNotEmpty()) {
            val selectedTab = tabPane.tabs.first { it.isSelected }
            val promptForFilename = (selectedTab.text.contains("<"))
            val dtSpecYamlViewModel = selectedTab.userData as DtSpecViewModel
            mainFormController.saveFile(dtSpecYamlViewModel, promptForFilename)
        }
    }

    private fun menuItemOpenHandler() {
        val dtSpecYamlViewModel = mainFormController.openFile()
        if (dtSpecYamlViewModel != null) {
            val newTab = tabPane.tab("<${dtSpecYamlViewModel.filename}> *")
            newTab.userData = dtSpecYamlViewModel
            newTab.add(DtSpecDocumentFragment(scope, dtSpecYamlViewModel))
            newTab.select()
        }
    }

    private fun menuItemNewHandler() {
        val name = "new ${tabPane.tabs.size}"
        val dtSpecYamlViewModel = mainFormController.newFile("$name.yml")
        val newTab = tabPane.tab("<$name> *")
        newTab.userData = dtSpecYamlViewModel
        newTab.add(DtSpecDocumentFragment(scope, dtSpecYamlViewModel))
        newTab.select()
    }
}