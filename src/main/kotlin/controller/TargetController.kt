package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.view.document.model.DtSpecColumnIdentifierMappingViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeMappingViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecTargetViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
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

    val selectedTargetIdentifierMappingViewModel = SimpleObjectProperty<DtSpecIdentifierAttributeMappingViewModel>()


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
                                name = dtSpecViewModel.identifiers.firstOrNull()?.identifier,
                                attribute = dtSpecViewModel.identifiers.firstOrNull()?.attributes?.firstOrNull()?.field
                        )
                )
        )
    }

    fun removeTargetFieldMapping() {
        selectedColumnIdentifierMapping.remove(selectedTargetIdentifierMappingViewModel.value)
    }


    fun DtSpecTargetViewModel.isTargetUsed(): Boolean =
            dtSpecViewModel
                    .scenarios.any { scenario ->
                        scenario.cases.any { case ->
                            case.expected.any { it.target == this.target }
                        }
                    }
}