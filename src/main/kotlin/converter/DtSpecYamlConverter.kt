package at.willhaben.dt.snowpit.converter

import at.willhaben.dt.snowpit.service.model.*
import at.willhaben.dt.snowpit.view.document.model.*
import tornadofx.*

fun DtSpecYaml.convert(filename: String) = DtSpecYamlViewModel(
        filename = filename,
        version = this.version,
        description = this.description,
        identifiers = this.identifiers.map { it.convert() }.asObservable(),
        sources = this.sources.map { it.convert() }.asObservable(),
        targets = this.targets.map { it.convert() }.asObservable(),
)

fun DtSpecYamlIdentifier.convert() = DtSpecIdentifierViewModel(
        identifier = this.identifier,
        attributes = this.attributes.map { it.convert() }.asObservable()
)

fun DtSpecYamlIdentifierAttribute.convert() = DtSpecIdentifierAttributeViewModel(
        field = this.field,
        generator = this.generator.name
)

fun DtSpecYamlSource.convert() = DtSpecSourceViewModel(
        source = this.source,
        identifierMap = this.identifier_map.map { it.convert() }.asObservable()
)

fun DtSpecYamlTarget.convert() = DtSpecTargetViewModel(
        target = this.target,
        identifierMap = this.identifier_map.map { it.convert() }.asObservable()
)

fun DtSpecItentifierMap.convert() = DtSpecSourceIdentifierMapViewModel(
        column = this.column,
        identifier = this.identifier.convert()
)

fun DtSpecSourceIdentifierMapping.convert() = DtSpecSourceIdentifierMappingViewModel(
        name = this.name,
        attribute = this.attribute
)