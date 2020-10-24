package at.willhaben.dt.snowpit.view.document.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

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

    val qualifiedTableName: String
        get() = "${schema.toLowerCase()}.${name.toLowerCase()}"
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
