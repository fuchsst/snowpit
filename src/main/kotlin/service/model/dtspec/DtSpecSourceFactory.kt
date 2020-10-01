package at.willhaben.dt.snowpit.service.model.dtspec

import kotlinx.serialization.Serializable

@Serializable
data class DtSpecYamlFactory(
        val factory: String,
        val data: List<DtSpecScenarioCaseFactorySource>
)
