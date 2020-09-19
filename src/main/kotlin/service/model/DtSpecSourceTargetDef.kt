package at.willhaben.dt.snowpit.service.model

import kotlinx.serialization.Serializable

@Serializable
data class DtSpecYamlSource(
        val source: String,
        val identifier_map: List<DtSpecItentifierMap>
)

@Serializable
data class DtSpecYamlTarget(
        val target: String,
        val identifier_map: List<DtSpecItentifierMap>
)

@Serializable
data class DtSpecItentifierMap(
        val column: String,
        val identifier: DtSpecSourceIdentifierMapping
)

@Serializable
data class DtSpecSourceIdentifierMapping(
        val name: String,
        val attribute: String
)