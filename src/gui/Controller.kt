package gui

import logic.consts.Properties.CELL_NUMBER
import logic.main.Sudoku
import logic.consts.SudokuState.*

internal class Controller(application: SudokuApp) {
    private val app: SudokuApp = application
    fun actionSolve() {
        val sudoku = Sudoku()
        for (i in 0 until CELL_NUMBER) {
            sudoku[i] = app.getCell(i)
        }
        val solve = sudoku.solve
        for (i in 0 until CELL_NUMBER) {
            if (solve[i].hasValue()) app.setCell(i, solve[i].getValue())
        }
        when (solve.getState()) {
            SOLVED -> app.setState("Solved successfully")
            UNSOLVABLE -> app.setState("There is no solutions")
            MANY_SOLVES -> app.setState("There are several solutions")
            else -> app.setState("...")
        }
    }

    fun actionClear() {
        app.clearBoard()
        app.setState("Enter your Sudoku")
    }

    init {
        actionClear()
    }
}