package at.willhaben.dt.snowpit.service.model.dbmeta

data class DbTable(
        val database: String,
        val schema: String,
        val name: String,
        val fields: List<DbTableField>
)

data class DbTableField(
        val name: String,
        val type: DbTableFieldType
)

enum class DbTableFieldType {
    string,
    number,
    date,
    datetime,
    variant
}