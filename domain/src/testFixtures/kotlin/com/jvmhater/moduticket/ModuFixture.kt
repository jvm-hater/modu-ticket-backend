package com.jvmhater.moduticket

import com.jvmhater.moduticket.model.Modu

object ModuFixture {
    fun generate(id: String = "id", name: String = "name"): Modu = Modu(id = id, name = name)
}
