package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.view.document.model.DtSpecColumnIdentifierMappingViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeMappingViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecSourceViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.beans.InvalidationListener
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.ContextMenu
import tornadofx.*

class SourceController : Controller() {
    val dtSpecViewModel: DtSpecViewModel by inject()
    val metadataController: MetadataController by inject()

    val selectedSource = SimpleObjectProperty<DtSpecSourceViewModel>()

    val schemaContextMenu = ContextMenu().apply {
        this.items.addAll(metadataController.buildTableContextMenu(selectedSource.value?.sourceProperty))
        this.onShowing = EventHandler {
            this.items.clear()
            this.items.addAll(metadataController.buildTableContextMenu(selectedSource.value?.sourceProperty))
        }
    }

    val tableFieldContextMenu = ContextMenu().apply {
        this.items.addAll(metadataController.buildTableFieldContextMenu(selectedSource.value?.source, selectedIdentifierMapViewModel, selectedColumnIdentifierMapping))
        this.onShowing = EventHandler {
            this.items.clear()
            this.items.addAll(metadataController.buildTableFieldContextMenu(selectedSource.value?.source, selectedIdentifierMapViewModel,selectedColumnIdentifierMapping))

        }
    }

    val selectedColumnIdentifierMapping: SimpleListProperty<DtSpecColumnIdentifierMappingViewModel>?
        get() = selectedSource.value?.identifierMapProperty

    val selectedIdentifierMapViewModel = SimpleObjectProperty<DtSpecColumnIdentifierMappingViewModel>()

    val availableIdentifierAttributes = mutableListOf<DtSpecIdentifierAttributeMappingViewModel>().asObservable()


    init {
        val identifierListListener = InvalidationListener {
            val currentList = dtSpecViewModel.identifiers.flatMap { identifier ->
                identifier.attributes.map { attribute ->
                    DtSpecIdentifierAttributeMappingViewModel(identifier, attribute)
                }
            }
            availableIdentifierAttributes.clear()
            availableIdentifierAttributes.addAll(currentList)
            availableIdentifierAttributes.sorted()
        }
        dtSpecViewModel.identifiersProperty.addListener { _, oldItem, newItem ->
            if (oldItem != null) {
                oldItem.forEach { it.attributesProperty.removeListener(identifierListListener) }
                oldItem.removeListener(identifierListListener)
            }
            if (newItem != null) {
                newItem.forEach { it.attributesProperty.addListener(identifierListListener) }
                newItem.addListener(identifierListListener)
            }
        }
    }

    fun addSource() {
        dtSpecViewModel.sources.add(
                DtSpecSourceViewModel(
                        source = "source_${dtSpecViewModel.sources.size}",
                        columnIdentifierMapping = mutableListOf<DtSpecColumnIdentifierMappingViewModel>().asObservable())
        )
    }

    fun removeSource() {
        if (!selectedSource.value.isSourceUsed())
            dtSpecViewModel.sources.remove(selectedSource.value)
        else
            alert(
                    type = Alert.AlertType.WARNING,
                    header = "Source still in use...",
                    content = "The selected source is still used and cannot be removed!",
                    ButtonType.OK
            )
    }

    fun addSourceFieldMapping() {
        selectedColumnIdentifierMapping?.add(
                DtSpecColumnIdentifierMappingViewModel(
                        column = "column_${selectedColumnIdentifierMapping?.size ?: 0}",
                        identifier = DtSpecIdentifierAttributeMappingViewModel(
                                identifier = dtSpecViewModel.identifiers.firstOrNull(),
                                attribute = dtSpecViewModel.identifiers.firstOrNull()?.attributes?.firstOrNull()
                        )
                )
        )
    }

    fun removeSourceFieldMapping() {
        selectedColumnIdentifierMapping?.remove(selectedIdentifierMapViewModel.value)
    }

    fun DtSpecSourceViewModel.isSourceUsed(): Boolean =
            dtSpecViewModel
                    .scenarios.any { scenario ->
                        scenario.cases.any { case ->
                            case.factory.any { it.source == this.source }
                        }
                    }
}

