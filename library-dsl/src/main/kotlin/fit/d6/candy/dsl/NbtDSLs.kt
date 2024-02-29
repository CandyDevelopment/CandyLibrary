@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package fit.d6.candy.dsl

import fit.d6.candy.api.nbt.*
import java.util.*

class DslCompound {

    private val compound = NbtCompound.of()

    operator fun String.plus(value: NbtBase) {
        compound.put(this, value)
    }

    operator fun String.plus(value: Byte) {
        compound.putByte(this, value)
    }

    operator fun String.plus(value: Short) {
        compound.putShort(this, value)
    }

    operator fun String.plus(value: Int) {
        compound.putInt(this, value)
    }

    operator fun String.plus(value: Long) {
        compound.putLong(this, value)
    }

    operator fun String.plus(value: UUID) {
        compound.putUUID(this, value)
    }

    operator fun String.plus(value: Float) {
        compound.putFloat(this, value)
    }

    operator fun String.plus(value: Double) {
        compound.putDouble(this, value)
    }

    operator fun String.plus(value: String) {
        compound.putString(this, value)
    }

    operator fun String.plus(value: ByteArray) {
        compound.putByteArray(this, value)
    }

    operator fun String.plus(value: Array<Byte>) {
        compound.putByteArray(this, listOf(*value))
    }

    operator fun String.plus(value: IntArray) {
        compound.putIntArray(this, value)
    }

    operator fun String.plus(value: Array<Int>) {
        compound.putIntArray(this, listOf(*value))
    }

    operator fun String.plus(value: LongArray) {
        compound.putLongArray(this, value)
    }

    operator fun String.plus(value: Array<Long>) {
        compound.putLongArray(this, listOf(*value))
    }

    operator fun String.plus(value: Boolean) {
        compound.putBoolean(this, value)
    }

    fun original(): NbtCompound {
        return this.compound
    }

}

class DslList {

    private val list = NbtList.of()

    operator fun NbtBase.unaryPlus() {
        list.add(this)
    }

    operator fun Byte.unaryPlus() {
        list.add(NbtByte.of(this))
    }

    operator fun Short.unaryPlus() {
        list.add(NbtShort.of(this))
    }

    operator fun Int.unaryPlus() {
        list.add(NbtInt.of(this))
    }

    operator fun Long.unaryPlus() {
        list.add(NbtLong.of(this))
    }

    operator fun Float.unaryPlus() {
        list.add(NbtFloat.of(this))
    }

    operator fun Double.unaryPlus() {
        list.add(NbtDouble.of(this))
    }

    operator fun String.unaryPlus() {
        list.add(NbtString.of(this))
    }

    operator fun ByteArray.unaryPlus() {
        list.add(NbtByteArray.of(this))
    }

    operator fun Array<Byte>.unaryPlus() {
        list.add(NbtByteArray.of(listOf(*this)))
    }

    operator fun List<Byte>.unaryPlus() {
        list.add(NbtByteArray.of(this))
    }

    operator fun Array<Int>.unaryPlus() {
        list.add(NbtIntArray.of(listOf(*this)))
    }

    operator fun LongArray.unaryPlus() {
        list.add(NbtLongArray.of(this))
    }

    operator fun Array<Long>.unaryPlus() {
        list.add(NbtLongArray.of(listOf(*this)))
    }

    fun original(): NbtList {
        return this.list
    }

}

fun Compound(content: DslCompound.() -> Unit): NbtCompound {
    return DslCompound().apply(content).original()
}

fun List(content: DslList.() -> Unit): NbtList {
    return DslList().apply(content).original()
}