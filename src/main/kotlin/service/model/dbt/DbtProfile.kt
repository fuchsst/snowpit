package at.willhaben.dt.snowpit.service.model.dbt

data class DbtProfile(
        val name: String,
        val default: String,
        val outputs: Map<String, DbtTarget>
)

data class DbtTarget(
        val type: DbtTargetType,
        val account: String,
        val user: String,
        val password: String,
        val role: String?,
        val threads: String?,
        val database: String?,
        val warehouse: String?,
        val schema: String?
)

enum class DbtTargetType {
    snowflake
}