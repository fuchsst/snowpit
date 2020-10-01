package service

import at.willhaben.dt.snowpit.service.DbtProfilesService
import org.junit.jupiter.api.Test

internal class DbtProfilesServiceTest {

    val profilesYamlFilename = "dbt_profiles.yml"
    val profilesYamlFile = DbtProfilesServiceTest::class.java.classLoader.getResourceAsStream(profilesYamlFilename)

    @Test
    fun testParseDbtProfilesYaml() {

        val dbtProfilesService = DbtProfilesService()
        dbtProfilesService.loadTargetProfiles(profilesYamlFile)
    }
}