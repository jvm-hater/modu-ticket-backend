 package com.jvmhater.moduticket.configuration

 import org.springframework.beans.factory.annotation.Value
 import org.springframework.context.annotation.Bean
 import org.springframework.context.annotation.Configuration

 @Configuration
 class SecurityConfig {
    @Bean
    fun jwtSecret(@Value("\${jwt.secret}") secret: String): String  {
        return secret
    }
 }
