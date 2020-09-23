package at.willhaben.dt.snowpit.converter

import at.willhaben.dt.snowpit.service.model.*
import at.willhaben.dt.snowpit.view.document.model.*

fun DtSpecViewModel.convert() = DtSpecYaml(
        version = this.version,
        description = this.description,
        identifiers = this.identifiers.map { it.convert() }.toList(),
        sources = this.sources.map { it.convert() }.toList()
)

fun DtSpecIdentifierViewModel.convert() = DtSpecYamlIdentifier(
        identifier = this.identifier,
        attributes = this.attributes.map { it.convert() }.toList()
)

fun DtSpecIdentifierAttributeViewModel.convert() = DtSpecYamlIdentifierAttribute(
        field = this.field,
        generator = DtSpecYamlIdentifierGenerator.valueOf(this.generator)
)

fun DtSpecSourceViewModel.convert() = DtSpecYamlSource(
        source = this.source,
        identifier_map = this.identifierMap.map { it.convert() }.toList()
)

fun DtSpecColumnIdentifierMappingViewModel.convert() = DtSpecItentifierMap(
        column = this.column,
        identifier = this.identifier.convert()
)

fun DtSpecIdentifierAttributeMappingViewModel.convert() = DtSpecSourceIdentifierMapping(
        name = this.name,
        attribute = this.attribute
)