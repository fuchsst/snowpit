package at.willhaben.dt.snowpit.view.model


import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.getValue
import tornadofx.setValue

class DtSpecYamlViewModel(filename: String,
                          version: String,
                          description: String?,
                          identifiers: ObservableList<DtSpecIdentifierViewModel>,
                          sources: ObservableList<DtSpecSourceViewModel>,
                          targets: ObservableList<DtSpecTargetViewModel>) {

    val filenameProperty = SimpleStringProperty(filename)
    val versionProperty = SimpleStringProperty(version)
    val descriptionProperty = SimpleStringProperty(description)
    val identifiersProperty = SimpleListProperty(identifiers)
    val sourcesProperty = SimpleListProperty(sources)
    val targetsProperty = SimpleListProperty(targets)

    var filename by filenameProperty
    var version by versionProperty
    var description by descriptionProperty
    var identifiers by identifiersProperty
    var sources by sourcesProperty
    var targets by targetsProperty

}
