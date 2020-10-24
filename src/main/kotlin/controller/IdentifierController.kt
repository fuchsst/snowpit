package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.service.model.dtspec.DtSpecYamlIdentifierGenerator
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecViewModel
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.Controller
import tornadofx.alert
import tornadofx.asObservable

class IdentifierController : Controller() {
    val dtSpecViewModel: DtSpecViewModel by inject()

    val selectedIdentifier = SimpleObjectProperty<DtSpecIdentifierViewModel>()

    val selectedIdentifierAttributeList: SimpleListProperty<DtSpecIdentifierAttributeViewModel>
        get() = selectedIdentifier.value.attributesProperty

    val selectedIdentifierAttribute = SimpleObjectProperty<DtSpecIdentifierAttributeViewModel>()


    fun addIdentifier() {
        dtSpecViewModel.identifiers.add(
                DtSpecIdentifierViewModel(
                        identifier = "id_gen_${dtSpecViewModel.identifiers.size}",
                        attributes = mutableListOf<DtSpecIdentifierAttributeViewModel>().asObservable())
        )
    }

    fun removeIdentifier() {
        if (!selectedIdentifier.value.isIdentfierUsed())
            dtSpecViewModel.identifiers.remove(selectedIdentifier.value)
        else
            alert(
                    type = Alert.AlertType.WARNING,
                    header = "Identifier still in use...",
                    content = "The selected identifier is still used and cannot be removed!",
                    ButtonType.OK
            )
    }

    fun addAttribute() {
        selectedIdentifierAttributeList.add(
                DtSpecIdentifierAttributeViewModel(
                        field = "field_${selectedIdentifierAttributeList.size}",
                        generator = DtSpecYamlIdentifierGenerator.unique_integer.name
                )
        )
    }

    fun removeAttribute() {
        selectedIdentifierAttributeList.remove(selectedIdentifierAttribute.value)
    }

    fun DtSpecIdentifierViewModel.isIdentfierUsed(): Boolean {
        val isUsedInSources =
                dtSpecViewModel
                        .sources.any { source ->
                            source.identifierMap.any { identifierMap ->
                                identifierMap.identifier.identifier?.identifier == this.identifier
                            }
                        }
        val isUsedInTargets =
                dtSpecViewModel
                        .targets.any { target ->
                            target.identifierMap.any { identifierMap ->
                                identifierMap.identifier.identifier?.identifier == this.identifier
                            }
                        }
        return isUsedInSources || isUsedInTargets
    }
}