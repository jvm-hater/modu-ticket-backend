//package com.jvmhater.moduticket.configuration
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
//import org.springframework.security.config.web.server.ServerHttpSecurity
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.server.SecurityWebFilterChain
//
//@EnableWebFluxSecurity
//class SecurityConfig {
//    @Bean
//    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain  {
//        return http
//            .authorizeExchange{ auth ->
//                auth.pathMatchers("/api/signup").permitAll()
//                    .pathMatchers("/api/login").permitAll()
//            }
//            .httpBasic().disable()
//            .formLogin().disable()
//            .csrf().disable()
//            .build()
//
//    }
//}