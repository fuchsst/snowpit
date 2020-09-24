package at.willhaben.dt.snowpit.view.document.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.*


class DtSpecSourceViewModel(source: String,
                            columnIdentifierMapping: ObservableList<DtSpecColumnIdentifierMappingViewModel>) {

    val sourceProperty = SimpleStringProperty(source)
    val identifierMapProperty = SimpleListProperty(columnIdentifierMapping)

    var source by sourceProperty
    var identifierMap by identifierMapProperty
}

class DtSpecTargetViewModel(target: String,
                            columnIdentifierMapping: ObservableList<DtSpecColumnIdentifierMappingViewModel>) {

    val targetProperty = SimpleStringProperty(target)
    val identifierMapProperty = SimpleListProperty(columnIdentifierMapping)

    var target by targetProperty
    var identifierMap by identifierMapProperty
}

class DtSpecColumnIdentifierMappingViewModel(column: String,
                                             identifier: DtSpecIdentifierAttributeMappingViewModel) {

    val columnProperty = SimpleStringProperty(column)
    val identifierProperty = SimpleObjectProperty(identifier)

    var column by columnProperty
    var identifier by identifierProperty
}

class DtSpecIdentifierAttributeMappingViewModel(name: String?,
                                                attribute: String?) {

    val nameProperty = SimpleStringProperty(name)
    val attributeProperty = SimpleStringProperty(attribute)

    var name by nameProperty
    var attribute by attributeProperty

    override fun toString() = "$name: $attribute"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DtSpecIdentifierAttributeMappingViewModel) return false

        return (name == other.name) && (attribute != other.attribute)
    }

    override fun hashCode(): Int = 31 * nameProperty.hashCode() + attributeProperty.hashCode()

}