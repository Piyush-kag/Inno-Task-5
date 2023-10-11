package com.task.BookStore.Config;

import com.task.BookStore.Entity.Role;
import com.task.BookStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//
//    @Bean
//
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public UserDetailsService userDetailsService()
//    {
////        UserDetails normalUser = User.withUsername("piyush")
////                .password(passwordEncoder().encode("123"))
////                .roles("NORMAL")
////                .build();
////
////        UserDetails adminUser = User.withUsername("piyush1")
////                .password(passwordEncoder().encode("321"))
////                .roles("ADMIN")
////                .build();
////
////        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(normalUser, adminUser);
////
////        return inMemoryUserDetailsManager;
//        return new CustomUserDetailService();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers("/home/admin")
////                        .hasRole("ADMIN")
////                        .requestMatchers("/home/normal")
////                        .hasRole("NORMAL")
//                        .requestMatchers("/home/public").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(Customizer.withDefaults()
//
//                )
//                .rememberMe(Customizer.withDefaults());
//
//        return http.build();
//    }
private final JwtAuthenticationFilter jwtAuthenticationFilter;
private final UserService userService;

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request-> request.requestMatchers("/author/**")
                    .permitAll()
                    .requestMatchers("/author/admin").hasAnyAuthority(Role.ADMIN.name())
                    .requestMatchers("/author/user").hasAnyAuthority(Role.USER.name())
                    .anyRequest().authenticated())

            .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider()).addFilterBefore(
                    jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
}

@Bean
    public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userService.userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
}

@Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
}

}
