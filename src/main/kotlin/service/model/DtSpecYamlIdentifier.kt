package at.willhaben.dt.snowpit.service.model

import kotlinx.serialization.*

@Serializable
data class DtSpecYamlIdentifier(
        val identifier: String,
        val attributes: List<DtSpecYamlIdentifierAttribute>
)

@Serializable
data class DtSpecYamlIdentifierAttribute(
        val field: String,
        val generator:DtSpecYamlIdentifierGenerator
)

@Serializable
enum class DtSpecYamlIdentifierGenerator{
    unique_string,
    unique_integer
}