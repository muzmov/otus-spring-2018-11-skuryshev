package ru.otus.crudrest.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
class SecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Autowired
    @Qualifier("mongoUserDetailsService")
    lateinit var userDetailsService: UserDetailsService

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests().antMatchers("/**").authenticated()
            .and()
            .formLogin().defaultSuccessUrl("/index.html")
            .and()
            .rememberMe().key("SecretKey")
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun userDetailsService() = userDetailsService
}