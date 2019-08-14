import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.fxml.FXML
import javafx.scene.control.Button
import tornadofx.*
import Operator.*
import javafx.scene.input.KeyEvent

class Calculator : View () {
    override val root: VBox by fxml()

    @FXML lateinit var display: Label

    init {
        title = "kt Calculator"

        root.lookupAll(".button").forEach { b ->
            b.setOnMouseClicked {
                operator((b as Button).text)
            }
        }

        root.addEventFilter(KeyEvent.KEY_TYPED) {
            operator(it.character.toUpperCase().replace("\r","="))
        }

    }

    var state: Operator = add(0)

    fun onAction(fn: Operator) {
        state = fn
        display.text = ""
    }

    val displayValue: Long
        get() = when(display.text ) {
            "" -> 0
            else -> display.text.toLong()
        }

    private fun operator(x: String) {
        if ( Regex("[0-9]").matches(x) ) {
            display.text += x
        } else {
            when(x) {
                "+" -> onAction(add(displayValue))
                "-" -> onAction(sub(displayValue))
                "x" -> onAction(mult(displayValue))
                "/" -> onAction(div(displayValue))
                "%" -> { onAction(add(displayValue / 100)); operator("=") }
                "C" -> onAction(add(0))
                "+/-" -> { onAction(mult(-1 * displayValue)); operator("=") }
                "=" -> display.text = state.calculate(displayValue).toString()
            }
        }
    }
}