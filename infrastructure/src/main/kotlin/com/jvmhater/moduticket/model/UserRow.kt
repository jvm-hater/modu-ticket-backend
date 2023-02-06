package com.jvmhater.moduticket.model

<<<<<<< HEAD
=======
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.util.generateId
>>>>>>> 3665b0c (feat: apply auth pr review)
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

// clustering index 성능 개선 필요
@Table("user")
data class UserRow(
    @Id @Column("id") override val rowId: String = generateId(ID_PREFIX),
    @Transient @Value("null") override val isNewRow: Boolean = false,
    @Column("user_id") val userId: String,
    val password: String,
    val point: Int = 0,
) : Row<String> {

    companion object {
        const val ID_PREFIX = "user-id-"
    }

    fun toDomain(): User =
        User(
            id = userId,
            rank = rank,
            point = point,
            password = password
        )
}
