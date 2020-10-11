package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.controller.*
import at.willhaben.dt.snowpit.view.Icons
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.geometry.Side
import javafx.scene.image.ImageView
import tornadofx.*


class DtSpecDocumentFragment(fragmentScope: Scope, dtSpecViewModel: DtSpecViewModel) : Fragment() {

    override val root = hbox {
        setInScope(dtSpecViewModel, fragmentScope)

        fitToParentSize()

        vbox(spacing = 16) {
            fitToParentWidth()

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

                item("Targets", expanded = true) { this += find<TargetFragment>(fragmentScope) }

                item("Factories", expanded = true) { this += find<FactoriesFragment>(fragmentScope) }

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