package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.controller.FactoryController
import at.willhaben.dt.snowpit.service.isValidFieldName
import at.willhaben.dt.snowpit.view.Icons
import javafx.geometry.Pos
import javafx.scene.image.ImageView
import tornadofx.*

class FactoriesFragment : Fragment() {

    private val controller: FactoryController by inject()

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
                maxHeight = 256.0
                cellFormat { text = it.factory }
                bindSelected(controller.selectedFactory)
            }

            vbox {
                paddingAll = 4.0
                visibleWhen { controller.selectedFactory.isNotNull }
                hbox {
                    paddingAll = 4.0
                    label(text = "Factory Name: ") { alignment = Pos.CENTER_LEFT }
                    val factoryNameTextfield = textfield() {
                        filterInput { it.controlNewText.isValidFieldName() }
                    }

                    factoriesListView.selectionModel.selectedItemProperty().addListener { _, oldItem, newItem ->
                        if (oldItem != null) {
                            factoryNameTextfield.textProperty().unbindBidirectional(oldItem.factoryProperty)

                        }
                        if (newItem != null) {
                            factoryNameTextfield.textProperty().bindBidirectional(newItem.factoryProperty)
                        }
                    }
                }

            }
        }

    }
}
