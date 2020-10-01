package at.willhaben.dt.snowpit.service.model.dtspec

import kotlinx.serialization.Serializable


@Serializable
/**
 * @param scenario Name of the test scenario
 * @param cases List of test cases
 */
data class DtSpecYamlScenario(
        val scenario: String,
        val cases: List<DtSpecScenarioCase>
)


@Serializable
/**
 * @param case Name of test case
 * @param description Description of the test case
 * @param factory Source table data definitions
 * @param expected Expected table data definitions
 */
data class DtSpecScenarioCase(
        val case: String,
        val description: String?,
        val factory: DtSpecScenarioCaseFactory,
        val expected: DtSpecScenarioCaseExpected
)

@Serializable
/**
 * @param data List of source factories (source table data)
 */
data class DtSpecScenarioCaseFactory(
        val data: List<DtSpecScenarioCaseFactorySource>
)

@Serializable
/**
 * @param source Name of the source table
 * @param table Markdown formatted input table data
 */
data class DtSpecScenarioCaseFactorySource(
        val source: String,
        val table: String
)

@Serializable
/**
 * @param data List of expected data definitions
 */
data class DtSpecScenarioCaseExpected(
        val data: List<DtSpecScenarioCaseExpectedData>
)

@Serializable
/**
 * @param target Name of the target table
 * @param table Markdown formatted expected table data
 * @param by List of key field names
 */
data class DtSpecScenarioCaseExpectedData(
        val target: String,
        val table: String,
        val by: List<String> = emptyList()
)