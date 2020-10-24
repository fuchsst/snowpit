package at.willhaben.dt.snowpit.controller

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller
import tornadofx.getValue
import tornadofx.setValue

class PreferencesController : Controller() {

    companion object {
        private val ROOT_NODE = "snowpit"
        private val KEY_DBT_PROFILES_YML = "dbt_profiles_yaml_file"
        private val KEY_DBT_PROFILE = "dbt_profile"
        private val KEY_DBT_TARGET = "dbt_profile_target"
        private val KEY_QUALIFY_TABLE_NAMES = "qualify_table_names"
        private val KEY_FONT_SIZE = "font_size"
    }

    val dbtProfilesYamlPathProperty = SimpleStringProperty()
    val dbtProfileProperty = SimpleStringProperty()
    val dbtProfileTargetProperty = SimpleStringProperty()
    val qualifyTableNamesProperty = SimpleBooleanProperty()
    val fontSizeProperty = SimpleIntegerProperty()

    var dbtProfilesYamlPath by dbtProfilesYamlPathProperty
    var dbtProfile by dbtProfileProperty
    var dbtProfileTarget by dbtProfileTargetProperty
    var qualifyTableNames by qualifyTableNamesProperty
    var fontSize by fontSizeProperty


    init {
        load()
    }

    fun load() {
        preferences(ROOT_NODE) {
            dbtProfilesYamlPath = get(KEY_DBT_PROFILES_YML, "~/.dbt/profiles.yml")
            dbtProfile = get(KEY_DBT_PROFILE, "default")
            dbtProfileTarget = get(KEY_DBT_TARGET, "default")
            qualifyTableNames = getBoolean(KEY_QUALIFY_TABLE_NAMES, true)
            fontSize = getInt(KEY_FONT_SIZE, 24)
        }
    }

    fun save() {
        preferences(ROOT_NODE) {
            put(KEY_DBT_PROFILES_YML, dbtProfilesYamlPath)
            put(KEY_DBT_PROFILE, dbtProfile)
            put(KEY_DBT_TARGET, dbtProfileTarget)
            putBoolean(KEY_QUALIFY_TABLE_NAMES, qualifyTableNames)
            putInt(KEY_FONT_SIZE, fontSize)
            flush()
        }
    }

}