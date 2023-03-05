package com.jvmhater.moduticket.model

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

// clustering index 성능 개선 필요
@Table("user")
data class UserRow(
    @Id @Column("id") override val rowId: String,
    @Transient @Value("null") override val isNewRow: Boolean = false,
    val password: String,
    val point: Int = 0,
    @Column("user_rank") val rank: Rank = Rank.BRONZE,
) : Row<String> {

    fun toDomain(): User = User(id = rowId, rank = rank, point = point, password = password)
}
