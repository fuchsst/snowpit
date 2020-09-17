package at.willhaben.dt.snowpit.controller


import  at.willhaben.dt.snowpit.service.DtSpecYamlService
import at.willhaben.dt.snowpit.view.model.DtSpecYamlViewModel
import tornadofx.*

class MainFormController : Controller() {

    private val dtSpecYamlService = DtSpecYamlService()
    //val yaml = DtSpecYamlViewModel(dtSpecYamlService.memberList).members


}