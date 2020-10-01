package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.controller.IdentifierController
import at.willhaben.dt.snowpit.service.isValidFieldName
import at.willhaben.dt.snowpit.service.model.dtspec.DtSpecYamlIdentifierGenerator
import at.willhaben.dt.snowpit.view.Icons
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeViewModel
import javafx.geometry.Pos
import javafx.scene.image.ImageView
import tornadofx.*

class IdentifierFragment : Fragment() {

    private val controller: IdentifierController by inject()

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

            val identifiersListView = listview(controller.dtSpecViewModel.identifiers) {
                prefWidth = 512.0
                maxHeight = 256.0
                cellFormat { text = it.identifier }
                bindSelected(controller.selectedIdentifier)
            }

            vbox {
                paddingAll = 4.0
                visibleWhen { controller.selectedIdentifier.isNotNull }
                hbox {
                    paddingAll = 4.0
                    label(text = "Identifier Name: ") { alignment = Pos.CENTER_LEFT }
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
                    paddingAll = 4.0
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
                        isEditable = true

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
                }
            }
        }

    }
}
