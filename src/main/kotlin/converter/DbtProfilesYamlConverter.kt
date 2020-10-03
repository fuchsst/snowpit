package at.willhaben.dt.snowpit.converter

import at.willhaben.dt.snowpit.service.model.dbt.DbtProfile
import at.willhaben.dt.snowpit.service.model.dbt.DbtTarget
import at.willhaben.dt.snowpit.view.document.model.DbtProfileViewModel
import at.willhaben.dt.snowpit.view.document.model.DbtTargetViewModel
import tornadofx.*

fun DbtProfile.convert() = DbtProfileViewModel(
        name = this.name,
        default = this.default,
        outputs = this.outputs.map { it.convert() }.toMutableList().asObservable()
)

fun Map.Entry<String, DbtTarget>.convert() = DbtTargetViewModel(
        name = this.key,
        type = this.value.type,
        account = this.value.account,
        user = this.value.user,
        password = this.value.password,
        role = this.value.role,
        threads = this.value.threads,
        database = this.value.database,
        warehouse = this.value.warehouse,
        schema = this.value.schema
)

