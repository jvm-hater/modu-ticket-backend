package com.jvmhater.moduticket.elasticsearch.index

open class IndexField<T>(val name: String, private val parent: IndexField<*>? = null) {

    val nestedName = nameWithParent(name, parent)

    private fun nameWithParent(childName: String, parent: IndexField<*>?): String {
        val nameDeque = ArrayDeque<String>()
        combineParentName(nameDeque, childName, parent)
        return nameDeque.joinToString(".")
    }

    private fun combineParentName(
        nameDeque: ArrayDeque<String>,
        childName: String,
        parent: IndexField<*>?
    ) {
        nameDeque.addFirst(childName)
        if (parent != null) {
            combineParentName(nameDeque, parent.name, parent.parent)
        }
    }
}
