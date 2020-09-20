package at.willhaben.dt.snowpit.service.model

import kotlinx.serialization.Serializable

@Serializable
data class DtSpecYaml(
        var version: String = "0.1",
        val description: String? = null,
        val identifiers: List<DtSpecYamlIdentifier> = mutableListOf(),
        val sources: List<DtSpecYamlSource> = mutableListOf(),
        val targets: List<DtSpecYamlTarget> = mutableListOf(),
        val scenarios: List<DtSpecScenario> = mutableListOf()
)