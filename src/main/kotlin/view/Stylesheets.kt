package at.willhaben.dt.snowpit.view

import tornadofx.CssBox
import tornadofx.Stylesheet
import tornadofx.px

class GlobalStylesheet : Stylesheet() {

    companion object {
        private val defaultFontSize = 24.px
    }

    init {
        s(form) {
            fontSize = defaultFontSize
        }
    }
}