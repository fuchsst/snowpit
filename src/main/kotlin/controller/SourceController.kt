package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.view.document.model.DtSpecColumnIdentifierMappingViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeMappingViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecSourceViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*

class SourceController : Controller() {
    val dtSpecViewModel: DtSpecViewModel by inject()

    val selectedSource = SimpleObjectProperty<DtSpecSourceViewModel>()

    val selectedColumnIdentifierMapping: SimpleListProperty<DtSpecColumnIdentifierMappingViewModel>
        get() = selectedSource.value.identifierMapProperty

    val selectedIdentifierMapViewModel = SimpleObjectProperty<DtSpecColumnIdentifierMappingViewModel>()


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

    fun removeAttribute() {
        selectedColumnIdentifierMapping.remove(selectedIdentifierMapViewModel.value)
    }

    fun DtSpecSourceViewModel.isSourceUsed(): Boolean =
            dtSpecViewModel
                    .scenarios.any { scenario ->
                        scenario.cases.any { case ->
                            case.factory.any { it.source == this.source }
                        }
                    }

}