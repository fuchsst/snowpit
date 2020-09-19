package at.willhaben.dt.snowpit.view.model


import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.getValue
import tornadofx.setValue


class DtSpecIdentifierViewModel(identifier: String,
                                attributes: ObservableList<DtSpecIdentifierAttributeViewModel>) {

    val identifierProperty = SimpleStringProperty(identifier)
    val attributesProperty = SimpleListProperty(attributes)

    var identifier by identifierProperty
    var attributes by attributesProperty
}

class DtSpecIdentifierAttributeViewModel(field: String, generator: String) {
    val fieldProperty = SimpleStringProperty(field)
    val generatorProperty = SimpleStringProperty(generator)

    var field by fieldProperty
    var generator by generatorProperty
}