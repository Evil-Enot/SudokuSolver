package gui

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.KeyboardFocusManager
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFormattedTextField
import javax.swing.SwingConstants
import javax.swing.text.DefaultEditorKit
import javax.swing.text.DefaultFormatterFactory
import javax.swing.text.MaskFormatter


class SudokuCell internal constructor() : JFormattedTextField(), KeyListener, FocusListener {
    override fun keyTyped(e: KeyEvent) {
        if ("123456789".indexOf(e.keyChar) != -1) {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent()
        }
    }

    override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_LEFT) {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent()
        } else if (e.keyCode == KeyEvent.VK_RIGHT) {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent()
        }
        if (e.keyCode == KeyEvent.VK_BACK_SPACE) {
            value = null
        }
    }

    override fun keyReleased(e: KeyEvent?) {}
    override fun focusGained(e: FocusEvent) {
        background = Color(192, 255, 184)
    }

    override fun focusLost(e: FocusEvent) {
        background = Color.white
    }

    companion object {
        private val dim = Dimension(30, 30)
        private val font = Font("Verdana", Font.BOLD, 14)
    }

    init {
        preferredSize = dim
        font = Companion.font
        horizontalAlignment = SwingConstants.CENTER
        val transparent = Color(0, 0, 0, 0)
        caretColor = transparent
        selectionColor = transparent
        val formatter = MaskFormatter("#")
        formatter.validCharacters = "123456789"
        val factory = DefaultFormatterFactory(formatter)
        formatterFactory = factory
        addKeyListener(this)
        addFocusListener(this)
        actionMap[DefaultEditorKit.deletePrevCharAction].isEnabled = false
    }
}