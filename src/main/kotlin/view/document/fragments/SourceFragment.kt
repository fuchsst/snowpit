package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.controller.SourceController
import at.willhaben.dt.snowpit.service.isValidQualifiedName
import at.willhaben.dt.snowpit.view.Icons
import at.willhaben.dt.snowpit.view.document.model.DtSpecColumnIdentifierMappingViewModel
import javafx.geometry.Pos
import javafx.scene.image.ImageView
import tornadofx.*

class SourceFragment : Fragment() {

    private val controller: SourceController by inject()

    override val root = hbox {
        vbox {
            button(graphic = ImageView(Icons.IconAddTable)) {
                tooltip("Add Source")
            }.action {
                controller.addSource()
            }

            button(graphic = ImageView(Icons.IconRemoveTable)) {
                tooltip("Remove Source")
                enableWhen { controller.selectedSource.isNotNull }
            }.action {
                controller.removeSource()
            }
        }
        hbox {

            val sourcesListView = listview(controller.dtSpecViewModel.sources) {
                prefWidth = 512.0
                maxHeight = 256.0
                cellFormat { text = it.source }
                bindSelected(controller.selectedSource)
            }

            vbox {
                paddingAll = 4.0
                visibleWhen { controller.selectedSource.isNotNull }
                hbox {
                    paddingAll = 4.0
                    label(text = "Source Table: ") { alignment = Pos.CENTER_LEFT }
                    val sourceNameTextfield = textfield() {
                        filterInput { it.controlNewText.isValidQualifiedName() }
                    }

                    sourcesListView.selectionModel.selectedItemProperty().addListener { _, oldItem, newItem ->
                        if (oldItem != null) {
                            sourceNameTextfield.textProperty().unbindBidirectional(oldItem.sourceProperty)

                        }
                        if (newItem != null) {
                            sourceNameTextfield.textProperty().bindBidirectional(newItem.sourceProperty)
                        }
                    }
                }
                hbox {
                    paddingAll = 4.0
                    vbox {
                        button(graphic = ImageView(Icons.IconAddLink)) {
                            tooltip("Add Column/Identifier mapping")

                        }.action {
                            controller.addSourceFieldMapping()
                        }

                        button(graphic = ImageView(Icons.IconRemoveLink)) {
                            tooltip("Remove Column/Identifier mapping")
                            //enableWhen { controller.selectedColumnIdentifierMapping.isNotNull }
                        }.action {
                            controller.removeSourceFieldMapping()
                        }
                    }
                    val identifierAttributesTable = tableview<DtSpecColumnIdentifierMappingViewModel> {
                        prefWidth = 768.0
                        isEditable = true

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

                    sourcesListView.selectionModel.selectedItemProperty().addListener { _, oldItem, newItem ->
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
