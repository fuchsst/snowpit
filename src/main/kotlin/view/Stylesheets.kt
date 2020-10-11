package at.willhaben.dt.snowpit.view

import at.willhaben.dt.snowpit.controller.PreferencesController
import tornadofx.*

class GlobalStylesheet : Stylesheet() {
    val preferencesController = find<PreferencesController>()

    init {
        preferencesController.fontSizeProperty.addListener { _ -> fontSizeChangeListener() }
        fontSizeChangeListener()
    }

    fun fontSizeChangeListener() {
        s(form) {
            fontSize = preferencesController.fontSize.px
        }
    }
}