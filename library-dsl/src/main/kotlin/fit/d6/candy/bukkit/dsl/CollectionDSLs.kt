package fit.d6.candy.bukkit.dsl

import fit.d6.candy.api.collection.CollectionService
import fit.d6.candy.api.collection.FixedList
import fit.d6.candy.api.collection.FixedMap

fun <T> fixedArrayListOf(maxSize: Int, vararg initialization: T): FixedList<T> {
    val list = CollectionService.getService().createFixedArrayList<T>(maxSize)
    list.addAll(listOf(*initialization))
    return list
}

fun <T> fixedLinkedListOf(maxSize: Int, vararg initialization: T): FixedList<T> {
    val list = CollectionService.getService().createFixedLinkedList<T>(maxSize)
    list.addAll(listOf(*initialization))
    return list
}

fun <K, V> fixedMapOf(maxSize: Int, accessOrder: Boolean, vararg initialization: Pair<K, V>): FixedMap<K, V> {
    val map = CollectionService.getService().createFixedMap<K, V>(maxSize, accessOrder)
    map.putAll(mapOf(*initialization))
    return map
}