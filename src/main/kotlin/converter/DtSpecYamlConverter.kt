package at.willhaben.dt.snowpit.converter

import at.willhaben.dt.snowpit.service.model.dtspec.*
import at.willhaben.dt.snowpit.view.document.model.*
import javafx.collections.ObservableList
import tornadofx.asObservable

fun DtSpecYaml.convert(filename: String): DtSpecViewModel {
    val identifiers = this.identifiers.map { it.convert() }.toMutableList().asObservable()

    return DtSpecViewModel(
            filename = filename,
            version = this.version,
            description = this.description,
            identifiers = identifiers,
            sources = this.sources.map { it.convert(identifiers) }.toMutableList().asObservable(),
            targets = this.targets.map { it.convert(identifiers) }.toMutableList().asObservable(),
            factories = this.factories.map { it.convert() }.toMutableList().asObservable(),
            scenarios = this.scenarios.map { it.convert() }.toMutableList().asObservable()
    )
}

fun DtSpecYamlIdentifier.convert() = DtSpecIdentifierViewModel(
        identifier = this.identifier,
        attributes = this.attributes.map { it.convert() }.toMutableList().asObservable()
)

fun DtSpecYamlIdentifierAttribute.convert() = DtSpecIdentifierAttributeViewModel(
        field = this.field,
        generator = this.generator.name
)

fun DtSpecYamlSource.convert(dtSpecYamlIdentifierViewModelList: ObservableList<DtSpecIdentifierViewModel>) = DtSpecSourceViewModel(
        source = this.source,
        columnIdentifierMapping = this.identifier_map.map { it.convert(dtSpecYamlIdentifierViewModelList) }.toMutableList().asObservable()
)

fun DtSpecYamlTarget.convert(dtSpecYamlIdentifierViewModelList: ObservableList<DtSpecIdentifierViewModel>) = DtSpecTargetViewModel(
        target = this.target,
        columnIdentifierMapping = this.identifier_map.map { it.convert(dtSpecYamlIdentifierViewModelList) }.toMutableList().asObservable()
)

fun DtSpecItentifierMap.convert(dtSpecYamlIdentifierViewModelList: ObservableList<DtSpecIdentifierViewModel>) = DtSpecColumnIdentifierMappingViewModel(
        column = this.column,
        identifier = this.identifier.convert(dtSpecYamlIdentifierViewModelList)
)

fun DtSpecSourceIdentifierMapping.convert(dtSpecYamlIdentifierViewModelList: ObservableList<DtSpecIdentifierViewModel>) = DtSpecIdentifierAttributeMappingViewModel(
        identifier = dtSpecYamlIdentifierViewModelList.first { it.identifier == this.name },
        attribute = dtSpecYamlIdentifierViewModelList.first { it.identifier == this.name }.attributes.first { it.field == this.attribute }
)

fun DtSpecYamlFactory.convert() =
        DtSpecFactoryViewModel(
                factory = this.factory,
                dataFactories = this.data.map { it.convert() }.toMutableList().asObservable()
        )

fun DtSpecYamlScenario.convert() = DtSpecScenarioViewModel(
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