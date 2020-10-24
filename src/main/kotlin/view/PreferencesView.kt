package at.willhaben.dt.snowpit.view

import at.willhaben.dt.snowpit.controller.MetadataController
import at.willhaben.dt.snowpit.controller.PreferencesController
import javafx.scene.control.Alert
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class PreferencesView : View("Preferences", ImageView(Icons.IconPreferences)) {

    private val controller: PreferencesController by inject(scope)
    private val metadataController: MetadataController by inject(scope)

    init {
        try {
            metadataController.reloadProfiles()
        } catch (e: Exception) {
            alert(Alert.AlertType.ERROR, "Error", e.message)
        }

    }

    override val root = form {
        prefWidth = 1000.0
        fieldset("dbt Profiles Yaml") {
            text(controller.dbtProfilesYamlPathProperty)
            field("Choose file") {
                button(text = "profiles.yml", graphic = ImageView(Icons.IconDbt)).action {
                    val fileChooser = FileChooser().apply {
                        extensionFilters.add(FileChooser.ExtensionFilter("dbt Profile Yaml", "profiles.yml"))
                        title = "Choose dbt Profiles Yaml..."
                    }
                    if (File(controller.dbtProfilesYamlPath).exists()) {
                        fileChooser.initialDirectory = File(File(controller.dbtProfilesYamlPath).parent)
                    }
                    val file = fileChooser.showOpenDialog(null)
                    if (file != null) {
                        controller.dbtProfilesYamlPath = file.absolutePath
                        try {
                            metadataController.reloadProfiles()
                        } catch (e: Exception) {
                            alert(Alert.AlertType.ERROR, "Error", e.message)
                        }
                    }
                }
            }
            field("Profile") {
                combobox<String>(
                        property = controller.dbtProfileProperty,
                        values = metadataController.dbtProfileListProperty
                )
            }
        }
        fieldset("Interface")
        {
            field("Qualify Tables") {
                checkbox {
                    bind(controller.qualifyTableNamesProperty)
                }
            }
            field("Font Size") {
                combobox(
                        property = controller.fontSizeProperty,
                        values = (8..48).toList()
                )
                label("(Change will take effect after restart)")
            }
        }
        hbox {
            button("Reset").action {
                controller.load() // reload from disk to restore old settings
            }
            button("OK").action {
                controller.save()
                close()
            }
        }
    }


}