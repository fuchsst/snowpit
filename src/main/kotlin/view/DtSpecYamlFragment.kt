package at.willhaben.dt.snowpit.view

import at.willhaben.dt.snowpit.view.model.DtSpecYamlViewModel
import javafx.collections.FXCollections
import javafx.geometry.Side
import tornadofx.*

class DtSpecYamlFragment(dtSpecYamlViewModel: DtSpecYamlViewModel) : Fragment() {


    override val root = hbox {
        fitToParentSize()
        vbox(spacing = 16) {
            fitToParentSize()
            hbox(spacing = 8) {

                label { text = "Profile: " }
                combobox<String> {
                    items = FXCollections.observableArrayList("Dev", "UAT", "Prod", "Test")
                }
                button { text = "Connect" }
            }
            hbox(spacing = 8) {
                label { text = "Description: " }
                textfield().bind(dtSpecYamlViewModel.description).apply { prefWidth = 512.0 }
            }

            drawer(side = Side.LEFT, multiselect = true) {
                item("Identifier", expanded = true) {

                }
                item("Sources", expanded = true) {

                }
                item("Targets", expanded = true) {

                }
                item("Scenarios", expanded = true) {

                }
            }
        }
    }
}