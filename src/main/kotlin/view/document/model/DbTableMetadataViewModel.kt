package at.willhaben.dt.snowpit.view.document.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.*

class DbTableMetadataViewModel(
        database: String,
        schema: String,
        name: String,
        fields: ObservableList<DbTableFieldMetadataViewModel>
) : ViewModel() {
    val databaseProperty = SimpleStringProperty(database)
    val schemaProperty = SimpleStringProperty(schema)
    val nameProperty = SimpleStringProperty(name)
    val fieldsProperty = SimpleListProperty(fields)

    var database by databaseProperty
    var schema by schemaProperty
    var name by nameProperty
    var fields by fieldsProperty
}


class DbTableFieldMetadataViewModel(
        name: String,
        type: String
) : ViewModel() {
    val nameProperty = SimpleStringProperty(name)
    val typeProperty = SimpleStringProperty(type)

    var name by nameProperty
    var type by typeProperty
}
