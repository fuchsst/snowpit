package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.view.Icons
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.image.ImageView
import tornadofx.*


class DtSpecDocumentFragment(dtSpecViewModel: DtSpecViewModel) : Fragment() {

    override val root = hbox {
        val fragmentScope = Scope()
        setInScope(dtSpecViewModel, fragmentScope)

        fitToParentSize()

        vbox(spacing = 16) {
            fitToParentWidth()
            hbox(spacing = 8) {

                label(text = "Profile: ")
                combobox<String> {
                    items = FXCollections.observableArrayList("Dev", "UAT", "Prod", "Test")
                    alignment = Pos.CENTER_LEFT
                }
                button(text = "Connect", graphic = ImageView(Icons.IconConnect))
            }
            hbox(spacing = 8) {
                fitToParentWidth()
                label(text = "Description: ")
                textfield(dtSpecViewModel.description)
            }

            drawer(side = Side.LEFT, multiselect = true) {
                fitToParentSize()
                item("Identifier", expanded = true) {
                    fitToParentSize()
                    this += find<IdentifierFragment>(fragmentScope)
                }
                item("Sources", expanded = true) { this += find<SourceFragment>(fragmentScope) }

                item("Targets", expanded = true) {
                    hbox {
                        vbox {
                            button(graphic = ImageView(Icons.IconAddTable)) { tooltip("Add Target") }
                            button(graphic = ImageView(Icons.IconRemoveTable)) { tooltip("Remove Target") }
                        }
                        hbox {

                        }
                    }
                }

                item("Scenarios", expanded = true) {
                    hbox {
                        vbox {
                            button(graphic = ImageView(Icons.IconAddScenario)) { tooltip("Add Scenario") }
                            button(graphic = ImageView(Icons.IconRemoveScenario)) { tooltip("Remove Scenario") }
                        }
                        hbox {

                        }
                    }
                }
            }
        }
    }
}