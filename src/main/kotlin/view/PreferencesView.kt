package at.willhaben.dt.snowpit.view

import at.willhaben.dt.snowpit.controller.MetadataController
import at.willhaben.dt.snowpit.controller.PreferencesController
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import tornadofx.*

class PreferencesView : View("Preferences", ImageView(Icons.IconPreferences)) {

    private val controller: PreferencesController by inject()
    private val metadataController: MetadataController by inject()

    init {
        metadataController.reloadProfiles()
    }

    override val root = form {
        prefWidth = 1000.0
        fieldset("dbt Profiles Yaml") {
            field("Choose file") {
                button(graphic = ImageView(Icons.IconDbt)).action {
                    val fileChooser = FileChooser().apply {
                        extensionFilters.add(FileChooser.ExtensionFilter("dbt Profile Yaml", "profiles.yml"))
                        title = "Choose dbt Profiles Yaml..."
                    }

                    val file = fileChooser.showOpenDialog(null)
                    if (file != null) {
                        controller.dbtProfilesYamlPath = file.absolutePath
                        metadataController.reloadProfiles()
                    }
                }
            }
            text(controller.dbtProfilesYamlPathProperty)
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
                checkbox() {
                    bind(controller.qualifyTableNamesProperty)
                }
            }
        }
        hbox {
            button("Cancel").action {
                controller.load() // reload from disk to restore old settings
                close()
            }
            button("OK").action {
                controller.save()
                close()
            }
        }
    }


}