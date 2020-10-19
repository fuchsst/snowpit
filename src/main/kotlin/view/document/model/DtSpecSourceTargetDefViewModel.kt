package at.willhaben.dt.snowpit.view.document.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.*


class DtSpecSourceViewModel(source: String,
                            columnIdentifierMapping: ObservableList<DtSpecColumnIdentifierMappingViewModel>) : ViewModel() {

    val sourceProperty = SimpleStringProperty(source)
    val identifierMapProperty = SimpleListProperty(columnIdentifierMapping)

    var source by sourceProperty
    var identifierMap by identifierMapProperty
}

class DtSpecTargetViewModel(target: String,
                            columnIdentifierMapping: ObservableList<DtSpecColumnIdentifierMappingViewModel>) : ViewModel() {

    val targetProperty = SimpleStringProperty(target)
    val identifierMapProperty = SimpleListProperty(columnIdentifierMapping)

    var target by targetProperty
    var identifierMap by identifierMapProperty
}

class DtSpecColumnIdentifierMappingViewModel(column: String,
                                             identifier: DtSpecIdentifierAttributeMappingViewModel) : ViewModel() {

    val columnProperty = SimpleStringProperty(column)
    val identifierProperty = SimpleObjectProperty(identifier)

    var column by columnProperty
    var identifier by identifierProperty
}

class DtSpecIdentifierAttributeMappingViewModel(identifier: DtSpecIdentifierViewModel?,
                                                attribute: DtSpecIdentifierAttributeViewModel?) : ViewModel() {

    val identifierProperty = SimpleObjectProperty(identifier)
    val attributeProperty = SimpleObjectProperty(attribute)

    var identifier by identifierProperty
    var attribute by attributeProperty

    override fun toString() = "${identifier?.identifier}: ${attribute?.field}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DtSpecIdentifierAttributeMappingViewModel) return false

        return (identifier?.identifier == other.identifier?.identifier) && (attribute?.field == other.attribute?.field)
    }

    override fun hashCode(): Int = 31 * identifierProperty.hashCode() + attributeProperty.hashCode()

}