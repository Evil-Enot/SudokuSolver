package logic.cell

import logic.consts.Properties.BOARD_WIDTH
import java.util.*


internal class CellUnion(cells: List<Cell>) {
    fun exclude(excludeValue: Int) {
        for (it in cells) {
            it.exclude(excludeValue)
        }
    }


    val isSolved: Boolean
        get() {
            var def = 3628800 //10!
            for (cell in cells) {
                if (cell.hasValue())
                    def /= cell.getValue() + 1
            }
            return def == 1
        }

    val isErr: Boolean
        get() {
            var allNumbers = 0
            val numSet: MutableSet<Int> = TreeSet()
            for (cell in cells) {
                if (cell.hasValue()) {
                    numSet.add(cell.getValue())
                    allNumbers++
                }
            }
            return numSet.size != allNumbers
        }

    override fun toString(): String {
        val str = StringBuilder()
        for (cell in cells) {
            str.append(cell.getValue())
        }
        return str.toString()
    }

    private val cells: List<Cell>

    init {
        require(cells.size == 9) { "A list must have 9 elements" }
        this.cells = cells
        for (i in 0 until BOARD_WIDTH) {
            this.cells[i].connect(this)
        }
    }
}