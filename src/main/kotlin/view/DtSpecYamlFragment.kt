package at.willhaben.dt.snowpit.view

import at.willhaben.dt.snowpit.service.model.DtSpecYamlIdentifier
import at.willhaben.dt.snowpit.view.model.DtSpecYamlViewModel
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.image.ImageView
import tornadofx.*

class DtSpecYamlFragment(dtSpecYamlViewModel: DtSpecYamlViewModel) : Fragment() {


    override val root = hbox {
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
                textfield(dtSpecYamlViewModel.description)
            }

            drawer(side = Side.LEFT, multiselect = true) {

                item("Identifier", expanded = true) {
                    hbox {
                        vbox {
                            button(graphic = ImageView(Icons.IconAddIdentifier)) { tooltip("Add Identifier") }
                            button(graphic = ImageView(Icons.IconRemoveIdentifier)) { tooltip("Remove Identifier") }
                        }
                        hbox {
                            val selectedIdentifier = ItemViewModel<DtSpecYamlIdentifier>()
                            listview(dtSpecYamlViewModel.identifiers) {
                                prefWidth = 512.0
                                maxHeight = 256.0
                                //bindSelected { selectedIdentifier }
                                cellFormat { text = it.identifier }
                            }

                            vbox {
                                hbox {
                                    label(text = "Identifier Name: ")
                                    textfield { }
                                }
                                hbox {
                                    vbox {
                                        button(graphic = ImageView(Icons.IconAddCounter)) { tooltip("Add ID Generator") }
                                        button(graphic = ImageView(Icons.IconRemoveCounter)) { tooltip("Remove ID Generator") }
                                    }
                                    //tableview(items=selectedIdentifier.a) {  }
                                }
                            }
                        }

                    }
                }
                item("Sources", expanded = true) {
                    hbox {
                        vbox {
                            button(graphic = ImageView(Icons.IconAddTable)) { tooltip("Add Source") }
                            button(graphic = ImageView(Icons.IconRemoveTable)) { tooltip("Remove Source") }
                        }
                        hbox {

                        }

                    }
                }
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