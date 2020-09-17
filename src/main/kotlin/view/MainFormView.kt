package at.willhaben.dt.snowpit.view


import at.willhaben.dt.snowpit.controller.MainFormController
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.collections.FXCollections
import javafx.geometry.Side
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.controlsfx.glyphfont.FontAwesome
import tornadofx.*
import tornadofx.FX.Companion.icon

class MainFormView : View("Snowpit - DtSpec Yaml Editor", ImageView(Image("icon/app_icon.png"))) {

    private val controller: MainFormController by inject()

    override val root = form {

        menubar {
            menu("File") {
                item(name = "New", keyCombination = "Shortcut+N", graphic = FontAwesome().create(FontAwesomeIcon.FILE))
                item(name = "Open", keyCombination = "Shortcut+O", graphic = FontAwesome().create(FontAwesomeIcon.FOLDER_OPEN))
                item(name = "Save", keyCombination = "Shortcut+S", graphic = FontAwesome().create(FontAwesomeIcon.SAVE))
                item(name = "Save as ...", keyCombination = "Shift+Shortcut+N", graphic = FontAwesome().create(FontAwesomeIcon.DOWNLOAD))
                separator()
                item(name = "Preferences", keyCombination = "Shortcut+P", graphic = FontAwesome().create(FontAwesomeIcon.LIST_ALT))
                separator()
                item(name = "Quit", keyCombination = "Shortcut+Q", graphic = FontAwesome().create(FontAwesomeIcon.CLOSE))
            }
        }

        tabpane {
            tab("<New File> *") {
                vbox(spacing = 16) {
                    hbox(spacing = 8) {
                        label { text = "Profile: " }
                        combobox<String> {
                            items = FXCollections.observableArrayList("Dev", "UAT", "Prod", "Test")
                        }
                        button { text = "Connect" }
                    }
                    hbox(spacing = 8) {
                        label { text = "Description: " }
                        textfield()
                    }

                    drawer(side = Side.LEFT, multiselect = true) {
                        item("Identifier", expanded = true) {

                        }
                        item("Sources", expanded = true) {

                        }
                        item("Targets", expanded = true) {

                        }
                        item("Scenarios", expanded = true) {

                        }
                    }
                }
            }
            tab("Another Tab") {

            }
        }


        /*tableview(controller.members) {
            column("MemberType", MemberViewModel::memberType)
            column("Id", MemberViewModel::id)
            column("Name", MemberViewModel::name)
            column("Club", MemberViewModel::club)
            column("Bonus", MemberViewModel::bonus)
            column("Fee", MemberViewModel::fee)

            smartResize()
        }*/
    }
}