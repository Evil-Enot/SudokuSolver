package logic.main

import logic.cell.Cell
import logic.consts.SudokuState
import java.util.*
import logic.consts.Properties.BOARD_WIDTH
import logic.consts.Properties.CELL_NUMBER


internal open class Sudoku() {
    constructor(str: String) : this() {
        var string = str
        string = string.replace("\n+".toRegex(), "")
        require(string.length == CELL_NUMBER) { "Wrong size of a string" }
        for (i in 0 until BOARD_WIDTH) {
            for (j in 0 until BOARD_WIDTH) {
                val ch = string[BOARD_WIDTH * i + j]
                if (ch != '0') set(i, j, Character.getNumericValue(ch))
            }
        }

    }

    private var cells: MutableList<Cell>
    private var state: SudokuState

    init {
        cells = ArrayList()
        state = SudokuState.UNSOLVED
        for (i in 0 until CELL_NUMBER) {
            cells.add(Cell())
        }
    }

    constructor(other: Sudoku) : this() {
        for (i in 0 until CELL_NUMBER) {
            if (other[i].hasValue()) {
                this[i] = other[i].getValue()
            }
        }
    }

    operator fun set(cellIndex: Int, value: Int) {
        if (value == 0) return
        require(!(cellIndex >= CELL_NUMBER || cellIndex < 0)) { "Index must be from 0 to 80" }
        cells[cellIndex].setValue(value)
    }

    operator fun set(rowIndex: Int, columnIndex: Int, value: Int) {
        require(!(rowIndex < 0 || rowIndex >= BOARD_WIDTH || columnIndex < 0 || columnIndex > BOARD_WIDTH)) { "There are only 9 rows and 9 columns. Max index is 8" }
        set(rowIndex * BOARD_WIDTH + columnIndex, value)
    }

    operator fun get(cellIndex: Int): Cell = cells[cellIndex]

    private operator fun get(row: Int, column: Int): Cell {
        require(!(row < 0 || row >= BOARD_WIDTH || column < 0 || column >= BOARD_WIDTH)) { "There are only 9 rows and 9 columns. Max index is 8" }
        return cells[row * BOARD_WIDTH + column]
    }

    val solve: Sudoku
        get() {
            val solver = SudokuSolver(this)
            solver.solve()
            return solver
        }


    fun getState(): SudokuState = state

    protected fun setState(newState: SudokuState) {
        state = newState
    }

    protected fun moveFrom(other: Sudoku) {
        if (this !== other) {
            cells = other.cells
            state = other.state
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (i in 0 until BOARD_WIDTH - 1) {
            for (j in 0 until BOARD_WIDTH) {
                builder.append(cells[i * BOARD_WIDTH + j].getValue())
            }
            builder.append('\n')
        }
        for (j in 0 until BOARD_WIDTH) {
            builder.append(cells[CELL_NUMBER - BOARD_WIDTH + j].getValue())
        }
        return builder.toString()
    }

}