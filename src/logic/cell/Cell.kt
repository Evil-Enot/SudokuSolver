package logic.cell

import logic.consts.Properties.BOARD_WIDTH

internal class Cell internal constructor() : CellValuePublisher() {
    enum class CellValueState {
        DEFINE, UNDEFINE
    }

    fun hasValue(): Boolean = value != 0

    fun setValue(someValue: Int) {
        if (value != 0 && someValue != value) {
            return
        }
        require(!(someValue < 1 || someValue > BOARD_WIDTH)) { "Value must be from 1 to 9" }
        value = someValue
        defCount = BOARD_WIDTH
        for (i in 0 until BOARD_WIDTH) {
            states[i] = CellValueState.DEFINE
        }
        super.event(someValue)
    }

    fun getValue(): Int = value

    fun canBe(someValue: Int): Boolean = if (hasValue() && someValue == value){
            true
        } else states[someValue - 1] == CellValueState.UNDEFINE

    fun exclude(excludeValue: Int) {
        require(!(excludeValue < 1 || excludeValue > BOARD_WIDTH)) { "Value must be from 1 to 9" }
        if (states[excludeValue - 1] != CellValueState.DEFINE) {
            states[excludeValue - 1] = CellValueState.DEFINE
            ++defCount
        }
        if (defCount == BOARD_WIDTH - 1) attemptToDef()
    }

    private fun attemptToDef() {
        if (hasValue() || defCount != BOARD_WIDTH - 1) return
        for (i in 0 until BOARD_WIDTH) {
            if (states[i] != CellValueState.DEFINE) {
                setValue(i + 1)
                break
            }
        }
    }

    override fun toString(): String = value.toString()

    private var value = 0
    private var defCount = 0
    private val states: Array<CellValueState?> = arrayOfNulls(BOARD_WIDTH)

    init {
        for (i in 0 until BOARD_WIDTH) {
            states[i] = CellValueState.UNDEFINE
        }
    }
}