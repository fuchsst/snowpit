package at.willhaben.dt.snowpit.view.document.fragments

import at.willhaben.dt.snowpit.view.Icons
import javafx.collections.ObservableList
import javafx.scene.image.ImageView
import tornadofx.*

class TargetFragment : Fragment() {
    class Region(val id: Int, val name: String, val country: String, val branches: ObservableList<Branch>)

    class Branch(val id: Int, val facilityCode: String, val city: String, val stateProvince: String)

    val regions = listOf(
            Region(1, "Pacific Northwest", "USA", listOf(
                    Branch(1, "D", "Seattle", "WA"),
                    Branch(2, "W", "Portland", "OR")
            ).asObservable()),
            Region(2, "Alberta", "Canada", listOf(
                    Branch(3, "W", "Calgary", "AB")
            ).asObservable()),
            Region(3, "Midwest", "USA", listOf(
                    Branch(4, "D", "Chicago", "IL"),
                    Branch(5, "D", "Frankfort", "KY"),
                    Branch(6, "W", "Indianapolis", "IN")
            ).asObservable())
    ).asObservable()

    override val root = hbox {
        vbox {
            button(graphic = ImageView(Icons.IconAddTable)) { tooltip("Add Source") }
            button(graphic = ImageView(Icons.IconRemoveTable)) { tooltip("Remove Source") }
        }

        tableview(regions) {
            readonlyColumn("ID", Region::id)
            readonlyColumn("Name", Region::name)
            readonlyColumn("Country", Region::country)
            rowExpander(expandOnDoubleClick = true) {
                paddingLeft = expanderColumn.width
                tableview(it.branches) {
                    readonlyColumn("ID", Branch::id)
                    readonlyColumn("Facility Code", Branch::facilityCode)
                    readonlyColumn("City", Branch::city)
                    readonlyColumn("State/Province", Branch::stateProvince)
                }
            }
        }
    }
}
