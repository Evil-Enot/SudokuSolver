package gui

import java.awt.Component
import java.awt.Container
import java.awt.FocusTraversalPolicy


class FocusPolicy internal constructor(private val order: List<Component?>) : FocusTraversalPolicy() {
    override fun getComponentAfter(focusCycleRoot: Container?,
                                   aComponent: Component?): Component? {
        val index = (order.indexOf(aComponent) + 1) % order.size
        return order[index]
    }

    override fun getComponentBefore(focusCycleRoot: Container?,
                                    aComponent: Component?): Component? {
        var index = order.indexOf(aComponent) - 1
        if (index < 0) {
            index = order.size - 1
        }
        return order[index]
    }

    override fun getDefaultComponent(focusCycleRoot: Container?): Component? {
        return order[0]
    }

    override fun getLastComponent(focusCycleRoot: Container?): Component? {
        return order[order.size - 1]
    }

    override fun getFirstComponent(focusCycleRoot: Container?): Component? {
        return order[0]
    }

}