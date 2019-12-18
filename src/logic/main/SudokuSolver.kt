package logic.main

import logic.cell.Cell
import logic.cell.CellUnion
import logic.consts.Properties.BOARD_WIDTH
import logic.consts.Properties.CELL_NUMBER
import logic.consts.Properties.SECTOR_WIDTH

import logic.consts.SudokuState.*
import java.util.*


internal class SudokuSolver internal constructor() : Sudoku() {
    constructor(other: Sudoku) : this() {
        for (i in 0 until CELL_NUMBER) {
            if (other[i].hasValue()) {
                this[i] = other[i].getValue()
            }
        }
    }

    fun solve() {
        defineState()
        if (getState() !== UNSOLVED) return
        val undefCellIndex = undefinedCellIndex
        var firstSolved: SudokuSolver? = null
        for (i in 1 until BOARD_WIDTH + 1) {
            if (super.get(undefCellIndex).canBe(i)) {
                val subSudoku = SudokuSolver(this)
                subSudoku[undefCellIndex] = i
                subSudoku.solve()
                if (subSudoku.getState() === SOLVED) {
                    if (firstSolved == null) {
                        firstSolved = subSudoku
                    } else firstSolved.setState(MANY_SOLVES)
                    if (firstSolved.getState() === MANY_SOLVES) {
                        moveFrom(firstSolved)
                        return
                    }
                } else if (subSudoku.getState() === MANY_SOLVES) {
                    moveFrom(subSudoku)
                    return
                }
            }
        }
        if (firstSolved != null) {
            moveFrom(firstSolved)
            return
        }
        if (getState() === UNSOLVED) setState(UNSOLVABLE)
    }

    private val undefinedCellIndex: Int
        get() {
            for (i in 0 until CELL_NUMBER) {
                if (!super.get(i).hasValue()) return i
            }
            return -1
        }

    private fun defineState() {
        if (getState() !== UNSOLVED) return
        var solvedUnions = 0
        for (union in unions) {
            if (union.isSolved) solvedUnions++
            if (union.isErr) {
                setState(UNSOLVABLE)
                break
            }
        }
        if (solvedUnions == 2 * BOARD_WIDTH) setState(SOLVED)
    }

    private val unions: MutableList<CellUnion>

    init {
        unions = ArrayList()
        for (i in 0 until BOARD_WIDTH) {
            val rowList = ArrayList<Cell>()
            val columnList = ArrayList<Cell>()
            val squaresList = ArrayList<Cell>()
            for (j in 0 until BOARD_WIDTH) rowList.add(super.get(BOARD_WIDTH * i + j))
            for (j in 0 until BOARD_WIDTH) columnList.add(super.get(BOARD_WIDTH * j + i))
            for (j in 0 until SECTOR_WIDTH) {
                val rowStartIndex = i / SECTOR_WIDTH * SECTOR_WIDTH * BOARD_WIDTH
                val columnStartIndex = i % SECTOR_WIDTH * SECTOR_WIDTH + BOARD_WIDTH * j
                val startIndex = rowStartIndex + columnStartIndex
                for (k in 0 until SECTOR_WIDTH) {
                    squaresList.add(super.get(startIndex + k))
                }
            }
            unions.add(CellUnion(rowList))
            unions.add(CellUnion(columnList))
            CellUnion(squaresList)
        }
    }
}