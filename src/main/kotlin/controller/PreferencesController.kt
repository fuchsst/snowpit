package at.willhaben.dt.snowpit.controller

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class PreferencesController : Controller() {

    companion object {
        private val ROOT_NODE = "snowpit"
        private val KEY_DBT_PROFILES_YML = "dbt_profiles_yaml_file"
        private val KEY_DBT_PROFILE = "dbt_profile"
        private val KEY_DBT_TARGET = "dbt_profile_target"
        private val KEY_QUALIFY_TABLE_NAMES = "qualify_table_names"
    }

    val dbtProfilesYamlPathProperty = SimpleStringProperty()
    val dbtProfileProperty = SimpleStringProperty()
    val dbtProfileTargetProperty = SimpleStringProperty()
    val qualifyTableNamesProperty = SimpleBooleanProperty()

    var dbtProfilesYamlPath by dbtProfilesYamlPathProperty
    var dbtProfile by dbtProfileProperty
    var dbtProfileTarget by dbtProfileTargetProperty
    var qualifyTableNames by qualifyTableNamesProperty


    init {
        load()
    }

    fun load() {
        preferences(ROOT_NODE) {
            dbtProfilesYamlPath = get(KEY_DBT_PROFILES_YML, "~/.dbt/profiles.yml")
            dbtProfile = get(KEY_DBT_PROFILE, "default")
            dbtProfileTarget = get(KEY_DBT_TARGET, "default")
            qualifyTableNames = getBoolean(KEY_QUALIFY_TABLE_NAMES, true)
        }
    }

    fun save() {
        preferences(ROOT_NODE) {
            put(KEY_DBT_PROFILES_YML, dbtProfilesYamlPath)
            put(KEY_DBT_PROFILE, dbtProfile)
            put(KEY_DBT_TARGET, dbtProfileTarget)
            putBoolean(KEY_QUALIFY_TABLE_NAMES, qualifyTableNames)
            flush()
        }
    }

}