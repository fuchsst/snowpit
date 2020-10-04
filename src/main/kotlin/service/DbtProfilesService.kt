package at.willhaben.dt.snowpit.service

import at.willhaben.dt.snowpit.service.model.dbt.DbtProfile
import at.willhaben.dt.snowpit.service.model.dbt.DbtTarget
import at.willhaben.dt.snowpit.service.model.dbt.DbtTargetType
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.InputStream

class DbtProfilesService {

    fun loadTargetProfiles(profilesYamlFilename: String): List<DbtProfile> {
        val file = File(profilesYamlFilename)
        return loadTargetProfiles(file.inputStream())
    }

    fun loadTargetProfiles(profilesYamlFileStream: InputStream): List<DbtProfile> {
        val yaml = Yaml().loadAll(profilesYamlFileStream.reader())
        val rootNodes = yaml.first() as LinkedHashMap<String, Any>

        return rootNodes.map { rootNodes ->
            val childNodes = rootNodes.value as LinkedHashMap<String, Any>
            if (childNodes.containsKey("target") && childNodes.containsKey("outputs")) {
                val outputs = (childNodes["outputs"] as LinkedHashMap<String, Any>).mapValues {
                    convertHashMapToOutputConfig(it.value as LinkedHashMap<String, Any>)
                }
                DbtProfile(
                        name = rootNodes.key,
                        default = childNodes["target"] as String,
                        outputs = outputs
                )
            } else {
                null
            }
        }.filterNotNull()
    }

    private fun convertHashMapToOutputConfig(outputConfigHashMap: LinkedHashMap<String, Any>) = DbtTarget(
            type = DbtTargetType.valueOf(outputConfigHashMap["type"].toString()),
            account = substituteEnvVar(outputConfigHashMap["account"].toString()),
            user = substituteEnvVar(outputConfigHashMap["user"].toString()),
            password = substituteEnvVar(outputConfigHashMap["password"].toString()),
            role = substituteEnvVar(outputConfigHashMap["role"].toString()),
            threads = substituteEnvVar(outputConfigHashMap["threads"].toString()),
            database = substituteEnvVar(outputConfigHashMap["database"].toString()),
            warehouse = substituteEnvVar(outputConfigHashMap["warehouse"].toString()),
            schema = substituteEnvVar(outputConfigHashMap["schema"].toString())
    )

    private fun substituteEnvVar(setting: String): String {
        val cleanedSetting = setting.replace(" ", "")
        return if (cleanedSetting.startsWith("{{env_var('") && cleanedSetting.endsWith("')}}")) {
            val envVarName = cleanedSetting.substringAfter("'").substringBefore("'")
            System.getenv(envVarName) ?: setting
        } else {
            setting
        }
    }

}