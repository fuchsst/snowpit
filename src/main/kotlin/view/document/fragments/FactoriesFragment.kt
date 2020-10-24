package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.controller.FactoryController
import at.willhaben.dt.snowpit.service.isValidFieldName
import at.willhaben.dt.snowpit.view.Icons
import at.willhaben.dt.snowpit.view.document.model.DtSpecScenarioCaseFactoryViewModel
import javafx.geometry.Pos
import javafx.scene.image.ImageView
import tornadofx.*

class FactoriesFragment : Fragment() {

    private val controller: FactoryController by inject()
    val factoryNameTextfield = textfield {
        filterInput { it.controlNewText.isValidFieldName() }
    }
    val sourcesListTable = tableview(controller.selectedFactorySources) {
        maxHeight = 256.0
        isEditable = true
        column("Sources", DtSpecScenarioCaseFactoryViewModel::source) { smartResize() }
                .useComboBox(controller.availableSourceNames)
        bindSelected(controller.selectedSource)
    }

    override val root = hbox {
        prefHeight = 768.0
        vbox {
            button(graphic = ImageView(Icons.IconAddFactory)) {
                tooltip("Add Factory")
            }.action {
                controller.addFactory()
            }

            button(graphic = ImageView(Icons.IconRemoveFactory)) {
                tooltip("Remove Factory")
                enableWhen { controller.selectedFactory.isNotNull }
            }.action {
                controller.removeFactory()
            }
        }
        hbox {

            val factoriesListView = listview(controller.dtSpecViewModel.factories) {
                prefWidth = 512.0
                fitToParentHeight()
                cellFormat { text = it.factory }
                bindSelected(controller.selectedFactory)
            }

            vbox {
                paddingAll = 4.0
                visibleWhen { controller.selectedFactory.isNotNull }
                hbox {
                    paddingAll = 4.0
                    label(text = "Factory Name: ") { alignment = Pos.CENTER_LEFT }
                    this += factoryNameTextfield
                    factoryNameTextfield.prefWidth = 320.0
                }
                hbox {
                    vbox {
                        button(graphic = ImageView(Icons.IconAddTable)) {
                            tooltip("Add Source")
                            enableWhen { controller.selectedFactory.isNotNull }
                        }.action {
                            controller.addSource()
                        }

                        button(graphic = ImageView(Icons.IconRemoveTable)) {
                            tooltip("Remove Source")
                            enableWhen { controller.selectedFactory.isNotNull and controller.selectedSource.isNotNull }
                        }.action {
                            controller.removeSource()
                        }
                    }
                    this += sourcesListTable
                    sourcesListTable.prefWidth = 360.0
                }

                factoriesListView.selectionModel.selectedItemProperty().addListener { _, oldItem, newItem ->
                    if (oldItem != null) {
                        factoryNameTextfield.textProperty().unbindBidirectional(oldItem.factoryProperty)
                        sourcesListTable.itemsProperty().unbindBidirectional(oldItem.dataFactoriesProperty)
                    }
                    if (newItem != null) {
                        factoryNameTextfield.textProperty().bindBidirectional(newItem.factoryProperty)
                        sourcesListTable.itemsProperty().bindBidirectional(newItem.dataFactoriesProperty)
                    }
                }
            }
            vbox {
                visibleWhen { controller.selectedSource.isNotNull }
                label("Source Columns") {  alignment = Pos.CENTER_LEFT  }
                tableview(controller.availableSourceNames /* TODO: replace with proper list of columns of selected source (based on DtSpecScenarioCaseFactoryViewModel.table) */) {  }

            }
        }

    }
}
