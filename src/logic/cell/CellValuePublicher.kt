package logic.cell
import java.util.*


internal open class CellValuePublisher {
    fun connect(union: CellUnion) {
        unions.add(union)
    }

    fun event(setValue: Int) {
        for (it in unions) {
            it.exclude(setValue)
        }
    }

    private val unions: MutableList<CellUnion>

    init {
        unions = ArrayList()
    }
}