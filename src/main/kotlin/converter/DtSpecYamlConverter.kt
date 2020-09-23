package at.willhaben.dt.snowpit.converter

import at.willhaben.dt.snowpit.service.model.*
import at.willhaben.dt.snowpit.view.document.model.*
import tornadofx.*

fun DtSpecYaml.convert(filename: String) = DtSpecViewModel(
        filename = filename,
        version = this.version,
        description = this.description,
        identifiers = this.identifiers.map { it.convert() }.toMutableList().asObservable(),
        sources = this.sources.map { it.convert() }.toMutableList().asObservable(),
        targets = this.targets.map { it.convert() }.toMutableList().asObservable(),
        scenarios = this.scenarios.map { it.convert() }.toMutableList().asObservable()
)

fun DtSpecYamlIdentifier.convert() = DtSpecIdentifierViewModel(
        identifier = this.identifier,
        attributes = this.attributes.map { it.convert() }.toMutableList().asObservable()
)

fun DtSpecYamlIdentifierAttribute.convert() = DtSpecIdentifierAttributeViewModel(
        field = this.field,
        generator = this.generator.name
)

fun DtSpecYamlSource.convert() = DtSpecSourceViewModel(
        source = this.source,
        columnIdentifierMapping = this.identifier_map.map { it.convert() }.toMutableList().asObservable()
)

fun DtSpecYamlTarget.convert() = DtSpecTargetViewModel(
        target = this.target,
        columnIdentifierMapping = this.identifier_map.map { it.convert() }.toMutableList().asObservable()
)

fun DtSpecItentifierMap.convert() = DtSpecColumnIdentifierMappingViewModel(
        column = this.column,
        identifier = this.identifier.convert()
)

fun DtSpecSourceIdentifierMapping.convert() = DtSpecIdentifierAttributeMappingViewModel(
        name = this.name,
        attribute = this.attribute
)

fun DtSpecScenario.convert() = DtSpecScenarioViewModel(
        scenario = this.scenario,
        cases = this.cases.map { it.convert() }.toMutableList().asObservable()
)

fun DtSpecScenarioCase.convert() = DtSpecScenarioCaseViewModel(
        case = this.case,
        description = this.description,
        factory = this.factory.data.map { it.convert() }.toMutableList().asObservable(),
        expected = this.expected.data.map { it.convert() }.toMutableList().asObservable()
)

fun DtSpecScenarioCaseFactorySource.convert() = DtSpecScenarioCaseFactoryViewModel(
        source = this.source,
        table = this.table
)

fun DtSpecScenarioCaseExpectedData.convert() = DtSpecScenarioCaseExpectedDataViewModel(
        target = this.target,
        table = this.table,
        byKeys = this.by.toMutableList().asObservable()
)