package com.jvmhater.moduticket

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table
class ModuEntity(
    @Id val id: String
)