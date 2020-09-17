package at.willhaben.dt.snowpit


import at.willhaben.dt.snowpit.view.GlobalStylesheet
import at.willhaben.dt.snowpit.view.MainFormView
import javafx.scene.image.Image
import tornadofx.App
import tornadofx.launch
import tornadofx.reloadStylesheetsOnFocus


class Snowpit : App(Image("icon/app_icon.png"), MainFormView::class, GlobalStylesheet::class) {
    init {
        reloadStylesheetsOnFocus()
    }

}

fun main() {
    launch<Snowpit>()
}