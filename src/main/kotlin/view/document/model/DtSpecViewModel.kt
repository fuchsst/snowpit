package at.willhaben.dt.snowpit.view.document.model


import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.*

class DtSpecViewModel(filename: String,
                      version: String,
                      description: String?,
                      identifiers: ObservableList<DtSpecIdentifierViewModel>,
                      sources: ObservableList<DtSpecSourceViewModel>,
                      targets: ObservableList<DtSpecTargetViewModel>,
                      factories: ObservableList<DtSpecFactoryViewModel>,
                      scenarios: ObservableList<DtSpecScenarioViewModel>) : ViewModel() {

    val filenameProperty = SimpleStringProperty(filename)
    val versionProperty = SimpleStringProperty(version)
    val descriptionProperty = SimpleStringProperty(description)
    val identifiersProperty = SimpleListProperty(identifiers)
    val sourcesProperty = SimpleListProperty(sources)
    val targetsProperty = SimpleListProperty(targets)
    val factoriesProperty = SimpleListProperty(factories)
    val scenariosProperty = SimpleListProperty(scenarios)

    var filename by filenameProperty
    var version by versionProperty
    var description by descriptionProperty
    var identifiers by identifiersProperty
    var sources by sourcesProperty
    var targets by targetsProperty
    var factories by factoriesProperty
    var scenarios by scenariosProperty
}
