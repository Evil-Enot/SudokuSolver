import logic.consts.SudokuState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import logic.main.Sudoku

class SudokuTest {

    @Test
    fun creation() {
        val sud1 = Sudoku()
        val sud2 = Sudoku(strSud1)
        val sud3 = Sudoku(sud2)
        assertEquals(sud1.toString(), strEmpty)
        assertEquals(sud2.toString(), sud3.toString())
        assertThrows(IllegalArgumentException::class.java) {
            Sudoku("")
        }
        assertThrows(IllegalArgumentException::class.java) {
            Sudoku(strSud1.replace("1".toRegex(), "a"))
        }
        assertThrows(IllegalArgumentException::class.java) {
            Sudoku(strSud1 + "1")
        }
    }

    @Test
    fun solving() {
        val s1 = Sudoku(strSud1).solve
        val s2 = Sudoku(strSud2).solve
        val s3 = Sudoku(strSud3).solve
        assertEquals(s1.getState(), SudokuState.SOLVED)
        assertEquals(s2.getState(), SudokuState.SOLVED)
        assertEquals(s3.getState(), SudokuState.SOLVED)
        assertEquals(s1.toString(), strSud1Answer)
        assertEquals(s2.toString(), strSud2Answer)
        assertEquals(s3.toString(), strSud3Answer)
    }

    @Test
    fun manySolves() {
        val sud1 = Sudoku(strEmpty).solve
        val sud2 = Sudoku(strSud4).solve
        assertEquals(sud1.getState(), SudokuState.MANY_SOLVES)
        assertEquals(sud2.getState(), SudokuState.MANY_SOLVES)
    }

    @Test
    fun noSolves() {
        val errSud1 = strSud1.replace("530070000".toRegex(), "536070000")
        val errSud2 = strSud2.replace("000380200".toRegex(), "000387200")
        val errSud3 = strSud3.replace("090000400".toRegex(), "090020400")
        val sud1 = Sudoku(errSud1).solve
        val sud2 = Sudoku(errSud2).solve
        val sud3 = Sudoku(errSud3).solve
        assertEquals(sud1.getState(), SudokuState.UNSOLVABLE)
        assertEquals(sud2.getState(), SudokuState.UNSOLVABLE)
        assertEquals(sud3.getState(), SudokuState.UNSOLVABLE)
    }

    private val strSud2 = ("000003000"
            + "008190367"
            + "069002001"
            + "670000480"
            + "035000000"
            + "000380200"
            + "040051000"
            + "910000520"
            + "000040096")

    private val strSud2Answer = ("751863942\n"
            + "428195367\n"
            + "369472851\n"
            + "672519483\n"
            + "835724619\n"
            + "194386275\n"
            + "246951738\n"
            + "917638524\n"
            + "583247196")

    private val strSud3 = ("800000000"
            + "003600000"
            + "070090200"
            + "050007000"
            + "000045700"
            + "000100030"
            + "001000068"
            + "008500010"
            + "090000400")

    private val strSud3Answer = ("812753649\n"
            + "943682175\n"
            + "675491283\n"
            + "154237896\n"
            + "369845721\n"
            + "287169534\n"
            + "521974368\n"
            + "438526917\n"
            + "796318452")

    private val strSud4 = ("100080009"
            + "050601020"
            + "000503000"
            + "096104830"
            + "300060005"
            + "015908460"
            + "000705000"
            + "080309070"
            + "500010003")

    companion object {
        private const val strEmpty = ("000000000\n"
                + "000000000\n"
                + "000000000\n"
                + "000000000\n"
                + "000000000\n"
                + "000000000\n"
                + "000000000\n"
                + "000000000\n"
                + "000000000")
        private const val strSud1 = ("530070000"
                + "600195000"
                + "098000060"
                + "800060003"
                + "400803001"
                + "700020006"
                + "060000280"
                + "000419005"
                + "000080079")
        private const val strSud1Answer = ("534678912\n"
                + "672195348\n"
                + "198342567\n"
                + "859761423\n"
                + "426853791\n"
                + "713924856\n"
                + "961537284\n"
                + "287419635\n"
                + "345286179")
    }
}