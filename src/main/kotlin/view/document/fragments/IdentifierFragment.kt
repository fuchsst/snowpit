package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.controller.IdentifierController
import at.willhaben.dt.snowpit.service.isValidFieldName
import at.willhaben.dt.snowpit.service.model.DtSpecYamlIdentifierGenerator
import at.willhaben.dt.snowpit.view.Icons
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeViewModel
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.scene.image.ImageView
import tornadofx.*

class IdentifierFragment : Fragment() {

    private val controller: IdentifierController by inject()


    private val selectedIdentifierName = SimpleStringProperty()


    override val root = hbox {
        vbox {
            button(graphic = ImageView(Icons.IconAddIdentifier)) {
                tooltip("Add Identifier")
            }.action {
                controller.addIdentifier()
            }
            button(graphic = ImageView(Icons.IconRemoveIdentifier)) {
                tooltip("Remove Identifier")
                enableWhen { controller.selectedIdentifier.isNotNull  }
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
                paddingAll = 8.0
                visibleWhen { controller.selectedIdentifier.isNotNull }
                hbox {
                    label(text = "Identifier Name: ")
                    val identifierNameTextfield = textfield() {
                        filterInput { it.controlNewText.isValidFieldName() }
                    }

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
                            controller.addAttribute()
                        }
                        button(graphic = ImageView(Icons.IconRemoveCounter)) {
                            tooltip("Remove ID Generator")
                            enableWhen { controller.selectedIdentifierAttribute.isNotNull }
                        }.action {
                            controller.removeAttribute()
                        }
                    }
                    val identifierAttributesTable = tableview<DtSpecIdentifierAttributeViewModel> {
                        prefWidth = 768.0
                        //table = editModel
                        isEditable = true
                        //columnResizePolicy = SmartResize.POLICY
                        column("Field", DtSpecIdentifierAttributeViewModel::field) {
                            remainingWidth()
                        }.makeEditable()
                        column("Generator", DtSpecIdentifierAttributeViewModel::generator) {
                            prefWidth = 256.0
                        }.useComboBox(DtSpecYamlIdentifierGenerator.values().map { it.name }.toList().asObservable())

                        bindSelected(controller.selectedIdentifierAttribute)
                        columnResizePolicy = SmartResize.POLICY
                    }

                    identifiersListView.selectionModel.selectedItemProperty().addListener { _, oldItem, newItem ->
                        if (oldItem != null) {
                            identifierAttributesTable.itemsProperty().unbindBidirectional(oldItem.attributesProperty)

                        }
                        if (newItem != null) {
                            identifierAttributesTable.itemsProperty().bindBidirectional(newItem.attributesProperty)
                        }
                    }
                    //tableview(items=selectedIdentifier.a) {  }
                }
            }
        }

    }
}
