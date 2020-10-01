package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.view.document.model.DtSpecFactoryViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecScenarioCaseFactoryViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*

class FactoryController : Controller() {
    val dtSpecViewModel: DtSpecViewModel by inject()

    val selectedFactory = SimpleObjectProperty<DtSpecFactoryViewModel>()

    val selectedSourceDataFactory: SimpleListProperty<DtSpecScenarioCaseFactoryViewModel>
        get() = selectedFactory.value.dataFactoriesProperty

    val selectedSourceDataFactoryViewModel = SimpleObjectProperty<DtSpecScenarioCaseFactoryViewModel>()

    fun addFactory() {
        dtSpecViewModel.factories.add(
                DtSpecFactoryViewModel(
                        factory = "factory_${dtSpecViewModel.factories.size}",
                        dataFactories = mutableListOf<DtSpecScenarioCaseFactoryViewModel>().asObservable())
        )
    }

    fun removeFactory() {
        if (!selectedFactory.value.isFactoryUsed())
            dtSpecViewModel.targets.remove(selectedFactory.value)
        else
            alert(
                    type = Alert.AlertType.WARNING,
                    header = "Factory still in use...",
                    content = "The selected factory is still used and cannot be removed!",
                    ButtonType.OK
            )
    }

    fun addTargetFieldMapping() {
        selectedSourceDataFactory.add(
                DtSpecScenarioCaseFactoryViewModel(
                        source = dtSpecViewModel.sources.firstOrNull()?.source ?: "undefined_source",
                        table = ""
                )
        )
    }

    fun removeTargetFieldMapping() {
        selectedSourceDataFactory.remove(selectedSourceDataFactoryViewModel.value)
    }


    fun DtSpecFactoryViewModel.isFactoryUsed(): Boolean =
            false // TODO check if factory is used in scenarios
}