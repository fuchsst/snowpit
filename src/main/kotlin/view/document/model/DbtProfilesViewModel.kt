package at.willhaben.dt.snowpit.view.document.model

import at.willhaben.dt.snowpit.service.model.dbt.DbtTargetType
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

class DbtProfileListViewModel(
        profiles: ObservableList<DbtProfileViewModel>
) : ViewModel() {
    val profilesProperty = SimpleListProperty(profiles)

    var profiles by profilesProperty
}

class DbtProfileViewModel(
        name: String,
        default: String,
        outputs: ObservableList<DbtTargetViewModel>
) {
    val nameProperty = SimpleStringProperty(name)
    val defaultProperty = SimpleObjectProperty(default)
    val outputsProperty = SimpleListProperty(outputs)

    var name by nameProperty
    var default by defaultProperty
    var outputs by outputsProperty
}

class DbtTargetViewModel(
        name: String,
        type: DbtTargetType,
        account: String,
        user: String,
        password: String,
        role: String?,
        threads: String?,
        database: String?,
        warehouse: String?,
        schema: String?
) {
    val nameProperty = SimpleStringProperty(name)
    val typeProperty = SimpleObjectProperty(type)
    val accountProperty = SimpleStringProperty(account)
    val userProperty = SimpleStringProperty(user)
    val passwordProperty = SimpleStringProperty(password)
    val roleProperty = SimpleStringProperty(role)
    val threadsProperty = SimpleStringProperty(threads)
    val databaseProperty = SimpleStringProperty(database)
    val warehouseProperty = SimpleStringProperty(warehouse)
    val schemaProperty = SimpleStringProperty(schema)

    var name by nameProperty
    var type by typeProperty
    var account by accountProperty
    var user by userProperty
    var password by passwordProperty
    var role by roleProperty
    var database by databaseProperty
    var warehouse by warehouseProperty
    var schema by schemaProperty

}