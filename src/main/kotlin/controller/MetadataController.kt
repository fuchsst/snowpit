package at.willhaben.dt.snowpit.controller

import at.willhaben.dt.snowpit.DbtProfileException
import at.willhaben.dt.snowpit.converter.convert
import at.willhaben.dt.snowpit.service.DbRepositoryService
import at.willhaben.dt.snowpit.service.DbtProfilesService
import at.willhaben.dt.snowpit.view.document.model.DbTableMetadataViewModel
import javafx.beans.property.SimpleListProperty
import tornadofx.*
import java.io.File

class MetadataController : Controller() {

    private val preferencesController: PreferencesController by inject()
    private val dbtProfilesService = DbtProfilesService()

    val dbtProfileListProperty = SimpleListProperty(mutableListOf<String>().asObservable())
    var dbtProfileList by dbtProfileListProperty

    val dbtProfileTargetListProperty = SimpleListProperty(mutableListOf<String>().asObservable())
    var dbtProfileTargetList by dbtProfileTargetListProperty

    val dbTableMetadataListProperty = SimpleListProperty(mutableListOf<DbTableMetadataViewModel>().asObservable())
    var dbTableMetadataList by dbTableMetadataListProperty


    fun reloadProfiles() {
        val dbtProfilesYamlFile = File(preferencesController.dbtProfilesYamlPath)
        if (dbtProfilesYamlFile.exists()) {
            val profiles = dbtProfilesService.loadTargetProfiles(dbtProfilesYamlFile.inputStream())
            val profile = profiles.find { it.name == preferencesController.dbtProfile }?.convert()
            if (profile != null) {
                dbtProfileList.removeIf { !profiles.contains(it) }
                dbtProfileList.addAll(profiles.map { it.name }.filterNot { dbtProfileList.contains(it) })
                dbtProfileList.sort()
            } else {
                throw DbtProfileException("The configured Profile '${preferencesController.dbtProfile}' not found in '${preferencesController.dbtProfilesYamlPath}'!")
            }
        } else {
            throw DbtProfileException("The dbt profiles.yml configuration file '${preferencesController.dbtProfilesYamlPath}' does not exist!")
        }
    }

    fun reloadTargets() {
        val profiles = dbtProfilesService.loadTargetProfiles(preferencesController.dbtProfilesYamlPath)
        val activeProfile = profiles.firstOrNull { it.name == preferencesController.dbtProfile }

        if (activeProfile != null) {
            val dbtTargets = activeProfile.outputs.map { it.key }
            dbtProfileTargetList.removeIf { !dbtTargets.contains(it) }
            dbtProfileTargetList.addAll(dbtTargets.filterNot { dbtProfileTargetList.contains(it) })
            dbtProfileTargetList.sort()
        }
    }

    fun reloadDbTableMetadata() {
        val profiles = dbtProfilesService.loadTargetProfiles(preferencesController.dbtProfilesYamlPath)
        val activeProfile = profiles.firstOrNull { it.name == preferencesController.dbtProfile }
        val activeTarget =
                if (activeProfile != null && activeProfile.outputs.containsKey(preferencesController.dbtProfileTarget))
                    activeProfile.outputs[preferencesController.dbtProfileTarget]
                else
                    null

        dbTableMetadataList.clear()
        if (activeTarget != null) {
            val dbRepositoryService = DbRepositoryService(
                    account = activeTarget.account,
                    user = activeTarget.user,
                    password = activeTarget.password,
                    db = activeTarget.database,
                    schema = activeTarget.schema,
                    warehouse = activeTarget.warehouse,
                    role = activeTarget.role
            )
            dbTableMetadataList.addAll(dbRepositoryService.loadTableMetaData().map { it.convert() })
        }
    }
}