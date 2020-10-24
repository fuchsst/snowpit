package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.controller.TargetController
import at.willhaben.dt.snowpit.service.isValidQualifiedName
import at.willhaben.dt.snowpit.view.Icons
import at.willhaben.dt.snowpit.view.document.model.DtSpecColumnIdentifierMappingViewModel
import javafx.geometry.Pos
import javafx.scene.image.ImageView
import tornadofx.*

class TargetFragment : Fragment() {

    private val controller = find<TargetController>(scope)

    override val root = hbox {
        prefHeight = 768.0
        vbox {
            button(graphic = ImageView(Icons.IconAddTable)) {
                tooltip("Add Target")
            }.action {
                controller.addTarget()
            }

            button(graphic = ImageView(Icons.IconRemoveTable)) {
                tooltip("Remove Source")
                enableWhen { controller.selectedTarget.isNotNull }
            }.action {
                controller.removeTarget()
            }
        }
        hbox {

            val targetsListView = listview(controller.dtSpecViewModel.targets) {
                prefWidth = 512.0
                fitToParentHeight()
                cellFormat { text = it.target }
                bindSelected(controller.selectedTarget)
            }

            vbox {
                paddingAll = 4.0
                visibleWhen { controller.selectedTarget.isNotNull }
                hbox {
                    paddingAll = 4.0
                    label(text = "Target Table: ") { alignment = Pos.CENTER_LEFT }
                    val targetNameTextfield = textfield() {
                        prefWidth = 640.0
                        filterInput { it.controlNewText.isValidQualifiedName() }
                        contextMenu = controller.schemaContextMenu
                    }

                    targetsListView.selectionModel.selectedItemProperty().addListener { _, oldItem, newItem ->
                        if (oldItem != null) {
                            targetNameTextfield.textProperty().unbindBidirectional(oldItem.targetProperty)

                        }
                        if (newItem != null) {
                            targetNameTextfield.textProperty().bindBidirectional(newItem.targetProperty)
                        }
                    }
                }
                hbox {
                    paddingAll = 4.0
                    vbox {
                        button(graphic = ImageView(Icons.IconAddLink)) {
                            tooltip("Add Column/Identifier mapping")

                        }.action {
                            controller.addTargetFieldMapping()
                        }

                        button(graphic = ImageView(Icons.IconRemoveLink)) {
                            tooltip("Remove Column/Identifier mapping")
                            //enableWhen { controller.selectedColumnIdentifierMapping.isNotNull }
                        }.action {
                            controller.removeTargetFieldMapping()
                        }
                    }
                    val identifierAttributesTable = tableview<DtSpecColumnIdentifierMappingViewModel> {
                        prefHeight = 512.0
                        prefWidth = 768.0
                        isEditable = true
                        contextMenu = controller.tableFieldContextMenu

                        column("Column", DtSpecColumnIdentifierMappingViewModel::column) {
                            prefWidth = 384.0
                        }.makeEditable()

                        column("Identifier", DtSpecColumnIdentifierMappingViewModel::identifier) {
                            remainingWidth()
                            isEditable = true
                            cellFormat {
                                text = "${it.identifier}: ${it.attribute}"
                            }
                        }.useComboBox(controller.availableIdentifierAttributes)

                        bindSelected(controller.selectedIdentifierMapViewModel)
                        columnResizePolicy = SmartResize.POLICY
                    }

                    targetsListView.selectionModel.selectedItemProperty().addListener { _, oldItem, newItem ->
                        if (oldItem != null) {
                            identifierAttributesTable.itemsProperty().unbindBidirectional(oldItem.identifierMapProperty)
                        }
                        if (newItem != null) {
                            identifierAttributesTable.itemsProperty().bindBidirectional(newItem.identifierMapProperty)
                        }
                    }
                }
            }
        }

    }
}
