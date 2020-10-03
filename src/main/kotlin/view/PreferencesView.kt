package at.willhaben.dt.snowpit.view

import at.willhaben.dt.snowpit.service.model.dbt.DbtProfile
import at.willhaben.dt.snowpit.view.document.model.PreferencesViewModel
import javafx.beans.binding.BooleanExpression
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import tornadofx.*

class PreferencesView : View("Preferences", ImageView(Icons.IconPreferences)) {

    val preferencesViewModel: PreferencesViewModel by inject()

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
                        preferencesViewModel.dbtProfilesYamlPath = file.absolutePath
                    }
                }
            }
            text(preferencesViewModel.dbtProfilesYamlPathProperty)
            field("Profile") {
                combobox<DbtProfile>()
            }
        }
        fieldset("Interface")
        {
            field("Qualify Tables") {
                checkbox() {
                    bind(preferencesViewModel.qualifyTableNamesProperty)
                }
            }
        }
        hbox {
            button("Cancel").action {
                preferencesViewModel.rollback()
                close()
            }
            button("OK").action {
                preferencesViewModel.commit()
                preferencesViewModel.save()
                close()
            }
        }
    }


}