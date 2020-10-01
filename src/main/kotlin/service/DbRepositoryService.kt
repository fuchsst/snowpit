package at.willhaben.dt.snowpit.service

import at.willhaben.dt.snowpit.service.model.dbmeta.DbTable
import kotliquery.queryOf
import kotliquery.sessionOf
import kotliquery.using

class DbRepositoryService(
        private val host: String,
        private val user: String,
        private val password: String,
        private val db: String,
        private val schema: String? = null,
        private val warehouse: String? = null,
        private val role: String? = null
) {

    private fun buildJsbcConnectionString(account: String, db: String, schema: String?, warehouse: String?, role: String?) =
            "jdbc:snowflake://$account.snowflakecomputing.com/" +
                    "?db=$db" +
                    (if (schema != null) "&schema=$schema" else "") +
                    (if (warehouse != null) "&warehouse=$warehouse" else "") +
                    (if (schema != null) "&schema=$schema" else "") +
                    (if (role != null) "&role=$role" else "")

    private data class TableFieldsMeta(
            val database: String,
            val schema: String,
            val table: String,
            val field: String,
            val fieldType: String)

    fun loadTableMetaData() {
        val jdbcConnectionString = buildJsbcConnectionString(host, db, schema, warehouse, role)
        val statement =
                """
                select table_catalog as database_name, 
                       table_schema as schema_name, 
                       table_name, 
                       column_name as field_name, 
                       data_type as field_type
                from information_schema.columns
                where table_schema != 'INFORMATION_SCHEMA'
                order by database_name, schema_name, table_name, ordinal_position
                """.trimIndent()
        using(sessionOf(jdbcConnectionString, user, password)) { session ->
            val tableFieldList = session.list(queryOf(statement)) { row ->
                TableFieldsMeta(
                        database = row.string("database_name"),
                        schema = row.string("schema_name"),
                        table = row.string("table_name"),
                        field = row.string("field_name"),
                        fieldType = row.string("field_type")
                )
            }
        }

    }

    private fun convert(tableFieldsMeta: List<TableFieldsMeta>): List<DbTable> {
        TODO("Convert Table/Field List to DbTable objects")
    }

}