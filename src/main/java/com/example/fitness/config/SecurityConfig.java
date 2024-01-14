package com.example.fitness.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtFilter jwtFilter;
//BASIC AUTH
   /* @Bean
    public UserDetailsService user(){
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }*/
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    //Trainer, food, diet routingokat nem szabad mindenhol átengedni.
                    .authorizeHttpRequests(auth -> auth.requestMatchers("/user/**", "/trainer/**", "/food/**","/exercise/**", "/diet/**", "/workout/**", "/ws/**", "/message/**", "/guest/**", "/notification/**", "/dietRecommendation/**").permitAll().requestMatchers("/home/**").hasAuthority("GUEST").anyRequest().authenticated())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .httpBasic(Customizer.withDefaults())
                    .exceptionHandling(exception -> exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                        System.out.println("Unathorized: " + accessDeniedException.getMessage());
                            // Dobd meg a saját kivételt és adj vissza REST API-n keresztül
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write("Invalid authorities: " + accessDeniedException.getMessage());
                    }))
                    .build();
        }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
