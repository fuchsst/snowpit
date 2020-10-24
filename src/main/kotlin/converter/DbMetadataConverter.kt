package at.willhaben.dt.snowpit.converter

import at.willhaben.dt.snowpit.service.model.dbmeta.DbTable
import at.willhaben.dt.snowpit.service.model.dbmeta.DbTableField
import at.willhaben.dt.snowpit.view.document.model.DbTableFieldMetadataViewModel
import at.willhaben.dt.snowpit.view.document.model.DbTableMetadataViewModel
import tornadofx.asObservable

fun DbTable.convert() = DbTableMetadataViewModel(
        name = this.name,
        database = this.database,
        schema = this.schema,
        fields = this.fields.map { it.convert() }.toMutableList().asObservable()
)

fun DbTableField.convert() = DbTableFieldMetadataViewModel(
        name = this.name,
        type = this.type.name
)

