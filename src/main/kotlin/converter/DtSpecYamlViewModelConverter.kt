package at.willhaben.dt.snowpit.converter

import at.willhaben.dt.snowpit.service.model.DtSpecYaml
import at.willhaben.dt.snowpit.service.model.DtSpecYamlIdentifier
import at.willhaben.dt.snowpit.service.model.DtSpecYamlIdentifierAttribute
import at.willhaben.dt.snowpit.service.model.DtSpecYamlIdentifierGenerator
import at.willhaben.dt.snowpit.view.model.DtSpecIdentifierAttributeViewModel
import at.willhaben.dt.snowpit.view.model.DtSpecIdentifierViewModel
import at.willhaben.dt.snowpit.view.model.DtSpecYamlViewModel

fun DtSpecYamlViewModel.convert() = DtSpecYaml(
        version = this.version,
        description = this.description,
        identifiers = this.identifiers.map { it.convert() }.toList()
)

fun DtSpecIdentifierViewModel.convert() = DtSpecYamlIdentifier(
        identifier = this.identifier,
        attributes = this.attributes.map { it.convert() }.toList()
)

fun DtSpecIdentifierAttributeViewModel.convert() = DtSpecYamlIdentifierAttribute(
        field = this.field,
        generator = DtSpecYamlIdentifierGenerator.valueOf(this.generator)
)