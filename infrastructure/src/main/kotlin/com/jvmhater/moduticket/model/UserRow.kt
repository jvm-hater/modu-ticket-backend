package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.exception.RepositoryException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class UserRow(
    @Id @Column("id") override val rowId: String,
    @Transient @Value("null") override val isNewRow: Boolean = false,
    val password: String,
    val point: Int = 0,
    @Column("user_rank") val rank: String = Rank.BRONZE.name,
) : Row<String> {

    fun toDomain(): User =
        User(
            id = rowId,
            rank =
                when (rank) {
                    Rank.BRONZE.name -> Rank.BRONZE
                    Rank.SILVER.name -> Rank.SILVER
                    Rank.GOLD.name -> Rank.GOLD
                    Rank.PLATINUM.name -> Rank.PLATINUM
                    Rank.DIAMOND.name -> Rank.DIAMOND
                    else ->
                        throw RepositoryException.DataIntegrityException(
                            message = "올바르지 않은 등급의 유저입니다. 해당 유저 id : $rowId, rank: $rank"
                        )
                },
            point = point
        )
}
