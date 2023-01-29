package com.jvmhater.moduticket.model.vo

@JvmInline
value class Amount(val value: Int) {
    // example method
    operator fun plus(b: Amount) = Amount(value + b.value)

    operator fun minus(b: Amount) = Amount(value - b.value)

    operator fun compareTo(b: Amount): Int = (value - b.value)
}
