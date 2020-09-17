package at.willhaben.dt.snowpit.service.model

import kotlinx.serialization.*

@Serializable
data class DtSpecYaml(
        val version: String,
        val identifiers: List<DtSpecYamlIdentifier>,
        val sources: List<DtSpecSource>,
        val targets:List<DtSpecTarget>,
        val scenarios: List<DtSpecScenario>
)