package at.willhaben.dt.snowpit.service

import at.willhaben.dt.snowpit.service.model.dbmeta.DbTable
import at.willhaben.dt.snowpit.service.model.dbmeta.DbTableField
import at.willhaben.dt.snowpit.service.model.dbmeta.DbTableFieldType
import kotliquery.queryOf
import kotliquery.sessionOf
import kotliquery.using

class DbRepositoryService(
        private val account: String,
        private val user: String,
        private val password: String,
        private val db: String,
        private val schema: String? = null,
        private val warehouse: String? = null,
        private val role: String? = null
) {

    private fun buildJdbcConnectionString(account: String, db: String, schema: String?, warehouse: String?, role: String?) =
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

    fun loadTableMetaData(): List<DbTable> {
        val jdbcConnectionString = buildJdbcConnectionString(account, db, schema, warehouse, role)
        val statement =
                """
                select table_catalog as database_name, 
                       table_schema as schema_name, 
                       table_name, 
                       column_name as field_name, 
                       data_type as field_type
                from information_schema.columns
                where table_schema != 'INFORMATION_SCHEMA'
                order by table_catalog, table_schema, table_name, ordinal_position
                """.trimIndent()
        val result = mutableListOf<DbTable>()
        using(sessionOf(jdbcConnectionString, user, password)) { session ->
            val tableFieldList = session.list(queryOf(statement)) { row ->
                TableFieldsMeta(
                        database = row.string("DATABASE_NAME"),
                        schema = row.string("SCHEMA_NAME"),
                        table = row.string("TABLE_NAME"),
                        field = row.string("FIELD_NAME"),
                        fieldType = row.string("FIELD_TYPE")
                )
            }
            result.addAll(tableFieldList.convert())
        }
        return result.toList()
    }

    private fun List<TableFieldsMeta>.convert(): List<DbTable> {
        var previousQualifiedTable = "."
        val resultList = mutableListOf<DbTable>()
        var currentDbTable: DbTable? = null
        this.forEach {
            if ("${it.schema}.${it.table}" != previousQualifiedTable) {
                previousQualifiedTable = "${it.schema}.${it.table}"
                if (currentDbTable != null) {
                    resultList.add(currentDbTable as DbTable)
                }
                currentDbTable = DbTable(
                        database = it.database,
                        schema = it.schema,
                        name = it.table,
                        fields = mutableListOf(DbTableField(name = it.field, type = convertDbFieldType(it.fieldType))))
            } else {
                currentDbTable?.fields?.add(DbTableField(name = it.field, type = convertDbFieldType(it.fieldType)))
            }
        }
        if (currentDbTable != null) {
            resultList.add(currentDbTable as DbTable)
        }
        return resultList.toList()
    }

    private fun convertDbFieldType(fieldType: String): DbTableFieldType =
            when (fieldType.toUpperCase()) {
                in listOf("NUMBER", "DECIMAL", "NUMERIC",
                        "INT", "INTEGER", "BIGINT", "SMALLINT", "TINYINT", "BYTEINT",
                        "FLOAT", "FLOAT4", "FLOAT8",
                        "DOUBLE", "DOUBLE PRECISION", "REAL") -> DbTableFieldType.number
                in listOf("DATE") -> DbTableFieldType.date
                in listOf("DATETIME", "TIME", "TIMESTAMP",
                        "TIMESTAMP_LTZ", "TIMESTAMP_NTZ", "TIMESTAMP_TZ") -> DbTableFieldType.datetime
                in listOf("VARIANT", "OBJECT", "ARRAY") -> DbTableFieldType.variant
                else -> DbTableFieldType.string
            }


}