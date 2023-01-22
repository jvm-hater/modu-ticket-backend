package com.jvmhater.moduticket.model

import org.springframework.data.domain.Persistable

/*
R2DBC Repository save() 함수는 id 파라미터가 들어올 경우 Update, 그렇지 않을 경우 Insert로 간주합니다.
따라서 UUID와 같이 Insert할 때 id 파라미터를 넣어야 한다면 Persistable 인터페이스를 상속 받아서
getId()와 isNew()를 재정의해야 합니다.
(https://github.com/spring-projects/spring-data-r2dbc/issues/49)

isNew()를 재정의하기 위해 사용된 isNewRow는 Row 인터페이스의 하위 클래스에서 @Transient 형태로 정의가 될텐데
isNewRow를 non-nullable로 유지하려면 아래와 같은 규칙을 따라야 합니다.
- @Transient와 함께 @Value("null")을 사용할 것.
- default value를 사용할 것.
(https://github.com/spring-projects/spring-data-r2dbc/issues/320)
 */
interface Row<ID> : Persistable<ID> {
    val rowId: ID
    val isNewRow: Boolean

    override fun getId(): ID = this.rowId

    override fun isNew(): Boolean = isNewRow
}
