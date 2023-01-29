package com.jvmhater.moduticket.model.vo

@JvmInline
value class Quantity(val value: Int) {
    // example method
    operator fun plus(b: Quantity) = Quantity(value + b.value)

    operator fun minus(b: Quantity) = Quantity(value - b.value)
}
