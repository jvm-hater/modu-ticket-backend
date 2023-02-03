package com.jvmhater.moduticket.model.vo

@JvmInline
value class Quantity(val value: Int) {
    operator fun plus(b: Quantity) = Quantity(value + b.value)

    operator fun minus(b: Quantity) = Quantity(value - b.value)

    fun isZero(): Boolean = this.value == 0
}

fun Int.toQuantity(): Quantity = Quantity(value = this)
