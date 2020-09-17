package at.willhaben.dt.snowpit.service.model

import kotlinx.serialization.Serializable

@Serializable
data class DtSpecYaml(
        var version: String = "0.1",
        val description: String?,
        val identifiers: List<DtSpecYamlIdentifier>?,
        val sources: List<DtSpecSource>?,
        val targets: List<DtSpecTarget>?,
        val scenarios: List<DtSpecScenario>?
)