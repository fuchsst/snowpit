package at.willhaben.dt.snowpit.service.model.dtspec

import kotlinx.serialization.Serializable

@Serializable
data class DtSpecYaml(
        var version: String = "0.1",
        val description: String? = null,
        val identifiers: List<DtSpecYamlIdentifier> = emptyList(),
        val sources: List<DtSpecYamlSource> = emptyList(),
        val targets: List<DtSpecYamlTarget> = emptyList(),
        val factories: List<DtSpecYamlFactory> = emptyList(),
        val scenarios: List<DtSpecYamlScenario> = emptyList()
)