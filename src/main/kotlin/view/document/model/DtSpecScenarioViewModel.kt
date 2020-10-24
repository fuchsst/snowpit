package at.willhaben.dt.snowpit.view.document.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.getValue
import tornadofx.setValue

class DtSpecScenarioViewModel(
        scenario: String,
        cases: ObservableList<DtSpecScenarioCaseViewModel>
) {

    val scenarioProperty = SimpleStringProperty(scenario)
    val casesProperty = SimpleListProperty(cases)

    var scenario by scenarioProperty
    var cases by casesProperty
}


class DtSpecScenarioCaseViewModel(
        case: String,
        description: String?,
        factory: ObservableList<DtSpecScenarioCaseFactoryViewModel>,
        expected: ObservableList<DtSpecScenarioCaseExpectedDataViewModel>
) {
    val caseProperty = SimpleStringProperty(case)
    val descriptionProperty = SimpleStringProperty(description)
    val factoryProperty = SimpleListProperty(factory)
    val expectedProperty = SimpleListProperty(expected)

    var case by caseProperty
    var description by descriptionProperty
    var factory by factoryProperty
    var expected by expectedProperty
}

class DtSpecScenarioCaseFactoryViewModel(
        source: String,
        table: String
) {
    val sourceProperty = SimpleStringProperty(source)
    val tableProperty = SimpleStringProperty(table)

    var source by sourceProperty
    var table by sourceProperty
}

class DtSpecScenarioCaseExpectedDataViewModel(
        target: String,
        table: String,
        byKeys: ObservableList<String>
) {
    val targetProperty = SimpleStringProperty(target)
    val tableProperty = SimpleStringProperty(table)
    val byKeysProperty = SimpleListProperty(byKeys)

    var target by targetProperty
    var table by tableProperty
    var byKeys by byKeysProperty
}