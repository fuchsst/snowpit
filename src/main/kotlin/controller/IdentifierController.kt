package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.service.model.DtSpecYamlIdentifierGenerator
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierAttributeViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecIdentifierViewModel
import at.willhaben.dt.snowpit.view.document.model.DtSpecYamlViewModel
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*

class IdentifierController : Controller() {
    val dtSpecYamlViewModel: DtSpecYamlViewModel by inject()

    val selectedIdentifier = SimpleObjectProperty<DtSpecIdentifierViewModel>()

    val selectedIdentifierAttributeList: SimpleListProperty<DtSpecIdentifierAttributeViewModel>
        get() = selectedIdentifier.value.attributesProperty

    val selectedIdentifierAttribute = SimpleObjectProperty<DtSpecIdentifierAttributeViewModel>()


    fun addIdentifier() {
        dtSpecYamlViewModel.identifiers.add(
                DtSpecIdentifierViewModel(
                        identifier = "id_gen_${dtSpecYamlViewModel.identifiers.size}",
                        attributes = mutableListOf<DtSpecIdentifierAttributeViewModel>().asObservable())
        )
    }

    fun removeIdentifier() {
        if (!selectedIdentifier.value.isIdentfierUsed())
            dtSpecYamlViewModel.identifiers.remove(selectedIdentifier.value)
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
                dtSpecYamlViewModel
                        .sources.any { source ->
                            source.identifierMap.any { identifierMap ->
                                identifierMap.identifier.name == this.identifier
                            }
                        }
        val isUsedInTargets =
                dtSpecYamlViewModel
                        .targets.any { target ->
                            target.identifierMap.any { identifierMap ->
                                identifierMap.identifier.name == this.identifier
                            }
                        }
        return isUsedInSources || isUsedInTargets
    }
}