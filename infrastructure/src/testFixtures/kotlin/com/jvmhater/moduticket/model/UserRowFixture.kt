package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId

object UserRowFixture {
    fun generate(
        rowId: String = generateId(),
        userId: String = "userId",
        password: String = "password",
        point: Int = 1000
    ): UserRow {
        return UserRow(
            rowId = rowId,
            password = password,
            point = point,
            userId = userId
        )
    }
}
