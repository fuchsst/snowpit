package at.willhaben.dt.snowpit


import at.willhaben.dt.snowpit.view.GlobalStylesheet
import at.willhaben.dt.snowpit.view.MainFormView
import javafx.scene.image.Image
import tornadofx.*


class Snowpit : App(Image("icon/app_icon.png"), MainFormView::class, GlobalStylesheet::class) {
    init {
        reloadStylesheetsOnFocus()
    }

    override fun stop() {
        super.stop()
        /* Do your shutdown routine here  */
    }
}

fun main() {
    launch<Snowpit>()
}