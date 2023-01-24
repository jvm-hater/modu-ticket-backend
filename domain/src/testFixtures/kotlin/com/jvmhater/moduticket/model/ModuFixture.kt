package com.jvmhater.moduticket.model

object ModuFixture {
    fun generate(id: String = "id", name: String = "name"): Modu = Modu(id = id, name = name)
}
