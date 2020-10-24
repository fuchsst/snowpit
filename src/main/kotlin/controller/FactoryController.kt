package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.view.document.model.DtSpecFactoryViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecScenarioCaseFactoryViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.beans.InvalidationListener
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.Controller
import tornadofx.alert
import tornadofx.asObservable

class FactoryController : Controller() {
    val dtSpecViewModel: DtSpecViewModel by inject()

    val selectedFactory = SimpleObjectProperty<DtSpecFactoryViewModel>()

    val selectedFactorySources: SimpleListProperty<DtSpecScenarioCaseFactoryViewModel>?
        get() = selectedFactory.value?.dataFactoriesProperty

    val selectedSource = SimpleObjectProperty<DtSpecScenarioCaseFactoryViewModel>()


    val availableSourceNames = mutableListOf<String>().asObservable()

    init {
        val sourceListListener = InvalidationListener {
            val currentList = dtSpecViewModel.sources.map { it.source }
            availableSourceNames.clear()
            availableSourceNames.addAll(currentList)
            availableSourceNames.sorted()
        }
        dtSpecViewModel.sourcesProperty.addListener { _, oldItem, newItem ->
            if (oldItem != null) {
                oldItem.forEach { it.sourceProperty.removeListener(sourceListListener) }
                oldItem.removeListener(sourceListListener)
            }
            if (newItem != null) {
                newItem.forEach { it.sourceProperty.addListener(sourceListListener) }
                newItem.addListener(sourceListListener)
            }
        }
    }

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

    fun addSource() {
        selectedFactorySources?.add(
                DtSpecScenarioCaseFactoryViewModel(
                        source = dtSpecViewModel.sources.firstOrNull()?.source ?: "undefined_source",
                        table = ""
                )
        )
    }

    fun removeSource() {
        selectedFactorySources?.remove(selectedSource.value)
    }


    fun DtSpecFactoryViewModel.isFactoryUsed(): Boolean =
            false // TODO check if factory is used in scenarios
}