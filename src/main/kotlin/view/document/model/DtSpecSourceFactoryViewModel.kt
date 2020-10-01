package at.willhaben.dt.snowpit.view.document.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.*


class DtSpecFactoryViewModel(factory: String,
                             dataFactories: ObservableList<DtSpecScenarioCaseFactoryViewModel>) {

    val factoryProperty = SimpleStringProperty(factory)
    val dataFactoriesProperty = SimpleListProperty(dataFactories)

    var factory by factoryProperty
    var dataFactories by dataFactoriesProperty
}
