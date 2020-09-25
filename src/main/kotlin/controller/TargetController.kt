package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.view.document.model.DtSpecColumnIdentifierMappingViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeMappingViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecTargetViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.beans.InvalidationListener
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*

class TargetController : Controller() {
    val dtSpecViewModel: DtSpecViewModel by inject()

    val selectedTarget = SimpleObjectProperty<DtSpecTargetViewModel>()

    val selectedColumnIdentifierMapping: SimpleListProperty<DtSpecColumnIdentifierMappingViewModel>
        get() = selectedTarget.value.identifierMapProperty

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

    fun addTarget() {
        dtSpecViewModel.targets.add(
                DtSpecTargetViewModel(
                        target = "target_${dtSpecViewModel.targets.size}",
                        columnIdentifierMapping = mutableListOf<DtSpecColumnIdentifierMappingViewModel>().asObservable())
        )
    }

    fun removeTarget() {
        if (!selectedTarget.value.isTargetUsed())
            dtSpecViewModel.targets.remove(selectedTarget.value)
        else
            alert(
                    type = Alert.AlertType.WARNING,
                    header = "Source still in use...",
                    content = "The selected source is still used and cannot be removed!",
                    ButtonType.OK
            )
    }

    fun addTargetFieldMapping() {
        selectedColumnIdentifierMapping.add(
                DtSpecColumnIdentifierMappingViewModel(
                        column = "column_${selectedColumnIdentifierMapping.size}",
                        identifier = DtSpecIdentifierAttributeMappingViewModel(
                                identifier = dtSpecViewModel.identifiers.firstOrNull(),
                                attribute = dtSpecViewModel.identifiers.firstOrNull()?.attributes?.firstOrNull()
                        )
                )
        )
    }

    fun removeTargetFieldMapping() {
        selectedColumnIdentifierMapping.remove(selectedIdentifierMapViewModel.value)
    }


    fun DtSpecTargetViewModel.isTargetUsed(): Boolean =
            dtSpecViewModel
                    .scenarios.any { scenario ->
                        scenario.cases.any { case ->
                            case.expected.any { it.target == this.target }
                        }
                    }
}