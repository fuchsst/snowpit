package at.willhaben.dt.snowpit.view.model


import at.willhaben.dt.snowpit.service.model.DtSpecYaml
import at.willhaben.dt.snowpit.service.model.DtSpecYamlIdentifier
import at.willhaben.dt.snowpit.service.model.DtSpecYamlIdentifierAttribute
import tornadofx.*

class DtSpecYamlViewModel(private val yaml: DtSpecYaml) : ItemViewModel<DtSpecYaml>(yaml) {
    val version = bind { item?.version?.toProperty() }
    val identifiers = bind { item?.identifiers?.toProperty() }
}

class DtSpecYamlIdentifierViewModel(private val dtSpecIdentifier: DtSpecYamlIdentifier) : ItemViewModel<DtSpecYamlIdentifier>(dtSpecIdentifier) {
    val identifier = bind { item?.identifier?.toProperty() }
    val attributes = bind { item?.attributes?.toProperty() }
}

class DtSpecYamlIdentifierAttributeViewModel(private val dtSpecAttribute: DtSpecYamlIdentifierAttribute) : ItemViewModel<DtSpecYamlIdentifierAttribute>(dtSpecAttribute) {
    val field = bind { item?.field?.toProperty() }
    val generator = bind { item?.generator?.toProperty() }
}