package at.willhaben.dt.snowpit.service

import at.willhaben.dt.snowpit.service.model.DtSpecYaml
import com.charleskorn.kaml.Yaml
import java.io.File

class DtSpecYamlService {

    fun loadDtSpecYaml(file: File): DtSpecYaml {
        val ymlText = file.bufferedReader().readText()
        return Yaml.default.parse(DtSpecYaml.serializer(), ymlText)
    }


    fun saveDtSpecYaml(file: File, dtSpecYaml: DtSpecYaml) {
        file.bufferedWriter().use {
            val ymlText = Yaml.default.stringify(DtSpecYaml.serializer(), dtSpecYaml)
            it.write(ymlText)
        }

    }
}

