package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.controller.IdentifierController
import at.willhaben.dt.snowpit.service.model.DtSpecYamlIdentifierGenerator
import at.willhaben.dt.snowpit.view.Icons
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeViewModel
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.image.ImageView
import tornadofx.*

class IdentifierFragment : Fragment() {

    private val controller: IdentifierController by inject()


    private val selectedIdentifierName = SimpleStringProperty()
    private val selectedIdentifierAttribute = SimpleObjectProperty<DtSpecIdentifierAttributeViewModel>()

    override val root = hbox {
        vbox {
            button(graphic = ImageView(Icons.IconAddIdentifier)) {
                tooltip("Add Identifier")
            }.action {
                controller.addIdentifier()
            }
            button(graphic = ImageView(Icons.IconRemoveIdentifier)) {
                tooltip("Remove Identifier")
                enableWhen { controller.selectedIdentifier.isNotNull }
            }.action {
                controller.removeIdentifier()
            }
        }
        hbox {

            val identifiersListView = listview(controller.dtSpecYamlViewModel.identifiers) {
                prefWidth = 512.0
                maxHeight = 256.0
                cellFormat { text = it.identifier }
                bindSelected(controller.selectedIdentifier)
            }

            vbox {
                visibleWhen { controller.selectedIdentifier.isNotNull }
                hbox {
                    label(text = "Identifier Name: ")
                    val identifierNameTextfield = textfield()

                    identifiersListView.selectionModel.selectedItemProperty().addListener { _, oldItem, newItem ->
                        if (oldItem != null) {
                            identifierNameTextfield.textProperty().unbindBidirectional(oldItem.identifierProperty)

                        }
                        if (newItem != null) {
                            identifierNameTextfield.textProperty().bindBidirectional(newItem.identifierProperty)
                        }
                    }
                }
                hbox {
                    vbox {
                        button(graphic = ImageView(Icons.IconAddCounter)) {
                            tooltip("Add ID Generator")

                        }.action {

                            controller.selectedIdentifierAttributeList.add(
                                    DtSpecIdentifierAttributeViewModel(
                                            field = "field ${controller.dtSpecYamlViewModel.identifiers.size}",
                                            generator = DtSpecYamlIdentifierGenerator.unique_integer.name
                                    )
                            )
                        }
                        button(graphic = ImageView(Icons.IconRemoveCounter)) {
                            tooltip("Remove ID Generator")
                            enableWhen { selectedIdentifierAttribute.isNotNull }
                        }
                    }
                    tableview<DtSpecIdentifierAttributeViewModel> {
                        //table = editModel
                        isEditable = true
                        //columnResizePolicy = SmartResize.POLICY
                        column("Field", DtSpecIdentifierAttributeViewModel::field).makeEditable()
                        column("Generator", DtSpecIdentifierAttributeViewModel::generator)
                                .useComboBox(DtSpecYamlIdentifierGenerator.values().map { it.name }.toList().asObservable())

                        bindSelected(selectedIdentifierAttribute)

                    }
                    //tableview(items=selectedIdentifier.a) {  }
                }
            }
        }

    }
}
