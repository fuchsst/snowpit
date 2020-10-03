package at.willhaben.dt.snowpit.service

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class PreferencesService : Component() {

    companion object {
        private val ROOT_NODE = "snowpit"
        private val KEY_DBT_PROFILES_YML = "dbt_profiles_yaml_file"
        private val KEY_DBT_PROFILE = "dbt_profile"
        private val KEY_QUALIFY_TABLE_NAMES = "dbt_profiles_yaml_file"
    }

    private val dbtProfilesYamlPathProperty = SimpleStringProperty()
    private val dbtProfileProperty = SimpleStringProperty()
    private val qualifyTableNamesProperty = SimpleBooleanProperty()

    var dbtProfilesYamlPath by dbtProfilesYamlPathProperty
    var dbtProfile by dbtProfileProperty
    var qualifyTableNames by qualifyTableNamesProperty

    init {
        preferences(ROOT_NODE) {
            dbtProfilesYamlPath = get(KEY_DBT_PROFILES_YML, "~/.dbt/profiles.yml")
            dbtProfile = get(KEY_DBT_PROFILE, "default")
            qualifyTableNames = getBoolean(KEY_QUALIFY_TABLE_NAMES, true)
        }
    }

    fun save() {
        preferences(ROOT_NODE) {
            put(KEY_DBT_PROFILES_YML, dbtProfilesYamlPath)
            put(KEY_DBT_PROFILE, dbtProfile)
            putBoolean(KEY_QUALIFY_TABLE_NAMES, qualifyTableNames)
            flush()
        }
    }

}