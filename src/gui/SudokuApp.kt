package gui

import logic.consts.Properties.BOARD_WIDTH
import logic.consts.Properties.SECTOR_WIDTH
import java.awt.*
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.LineBorder


class SudokuApp private constructor() {
    private val frmSudoku = JFrame()
    private var lblState = JLabel("")
    private var cellList = mutableListOf<SudokuCell>()
    private var controller = Controller(this)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            EventQueue.invokeLater {
                try {
                    val window = SudokuApp()
                    window.frmSudoku.isVisible = true
                    window.frmSudoku.isFocusable = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    init {
        frmSudoku.isResizable = false
        frmSudoku.title = "Sudoku"
        frmSudoku.setBounds(100, 100, 325, 380)
        frmSudoku.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frmSudoku.contentPane.layout = BorderLayout(0, 3)
        val sudoku_panel = JPanel()
        sudoku_panel.layout = GridLayout(SECTOR_WIDTH, SECTOR_WIDTH, 0, 0)
        frmSudoku.contentPane.add(sudoku_panel, BorderLayout.NORTH)
        for (i in 0 until BOARD_WIDTH) {
            val panel = JPanel()
            panel.border = LineBorder(Color.black, 2)
            panel.layout = GridLayout(SECTOR_WIDTH, SECTOR_WIDTH, 0, 0)
            sudoku_panel.add(panel)
        }
        cellList = mutableListOf()

        for (rowBlock in 0 until SECTOR_WIDTH) for (i in 0 until SECTOR_WIDTH) for (columnBlock in 0 until SECTOR_WIDTH) {
            val currentPanel = sudoku_panel
                    .getComponent(rowBlock * SECTOR_WIDTH + columnBlock) as JPanel
            for (j in 0 until SECTOR_WIDTH) {
                val currentCell = SudokuCell()
                cellList.add(currentCell)
                currentPanel.add(currentCell)
            }
        }
        frmSudoku.focusTraversalPolicy = FocusPolicy(cellList)
        val info_panel = JPanel()
        info_panel.layout = BorderLayout(0, 0)
        frmSudoku.contentPane.add(info_panel, BorderLayout.CENTER)
        val button_panel = JPanel()
        val flowLayout = button_panel.layout as FlowLayout
        flowLayout.hgap = 10
        val btnSolve = JButton("Solve")
        btnSolve.addActionListener { controller.actionSolve() }
        val btnClear = JButton("Clear")
        btnClear.addActionListener { controller.actionClear() }
        button_panel.add(btnSolve)
        button_panel.add(btnClear)
        info_panel.add(button_panel, BorderLayout.NORTH)
        val state_panel = JPanel()
        state_panel.layout = FlowLayout(FlowLayout.CENTER, 5, 5)
        lblState = JLabel("Enter your Sudoku")
        state_panel.add(lblState)
        info_panel.add(state_panel, BorderLayout.SOUTH)
    }

    fun getCell(index: Int): Int {
        val text = cellList[index].text
        return if (text.trim { it <= ' ' }.isEmpty()) 0 else text.toInt()
    }

    fun setCell(index: Int, value: Int) {
        cellList[index].text = value.toString()
    }

    fun clearBoard() {
        if (!cellList.isNullOrEmpty()) {
            for (cell in cellList) {
                cell.value = null
            }
        }
    }

    fun setState(state: String?) {
        println(state)
        lblState.text = state
    }
}