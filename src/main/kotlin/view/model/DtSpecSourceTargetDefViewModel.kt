package at.willhaben.dt.snowpit.view.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.getValue
import tornadofx.setValue


class DtSpecSourceViewModel(source: String,
                            identifierMap: ObservableList<DtSpecSourceIdentifierMapViewModel>) {

    val sourceProperty = SimpleStringProperty(source)
    val identifierMapProperty = SimpleListProperty(identifierMap)

    var source by sourceProperty
    var identifierMap by identifierMapProperty
}

class DtSpecTargetViewModel(target: String,
                            identifierMap: ObservableList<DtSpecSourceIdentifierMapViewModel>) {

    val targetProperty = SimpleStringProperty(target)
    val identifierMapProperty = SimpleListProperty(identifierMap)

    var target by targetProperty
    var identifierMap by identifierMapProperty
}

class DtSpecSourceIdentifierMapViewModel(column: String,
                                         identifier: DtSpecSourceIdentifierMappingViewModel) {

    val columnProperty = SimpleStringProperty(column)
    val identifierProperty = SimpleObjectProperty(identifier)

    var column by columnProperty
    var identifier by identifierProperty
}

class DtSpecSourceIdentifierMappingViewModel(name: String,
                                             attribute: String) {

    val nameProperty = SimpleStringProperty(name)
    val attributeProperty = SimpleStringProperty(attribute)

    var name by nameProperty
    var attribute by attributeProperty
}