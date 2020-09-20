package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecYamlViewModel
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class IdentifierController : Controller() {
    val dtSpecYamlViewModel: DtSpecYamlViewModel by inject()

    val selectedIdentifier = SimpleObjectProperty<DtSpecIdentifierViewModel>()

    val selectedIdentifierAttributeList: SimpleListProperty<DtSpecIdentifierAttributeViewModel>
        get() = selectedIdentifier.value.attributesProperty

    fun addIdentifier() {
        dtSpecYamlViewModel.identifiers.add(
                DtSpecIdentifierViewModel(
                        identifier = "id_gen ${dtSpecYamlViewModel.identifiers.size}",
                        attributes = emptyList<DtSpecIdentifierAttributeViewModel>().asObservable())
        )
    }

    fun removeIdentifier() {
        dtSpecYamlViewModel.identifiers.remove(selectedIdentifier.value)
    }


}