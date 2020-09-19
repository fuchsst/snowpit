package at.willhaben.dt.snowpit.view

import javafx.scene.image.ImageView
import tornadofx.*

class IdentifierView : View() {
    override val root = hbox {
        vbox {
            button(graphic = ImageView(Icons.IconAddIdentifier)) { tooltip("Add Identifier") }
            button(graphic = ImageView(Icons.IconRemoveIdentifier)) { tooltip("Remove Identifier") }
        }
        hbox {

        }

    }
}
