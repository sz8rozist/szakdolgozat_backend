package com.example.fitness.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                    .cors(Customizer.withDefaults())
                   // .authorizeHttpRequests(auth -> auth.requestMatchers("/user/**", "/trainer/**", "/food/**","/exercise/**", "/notification/**", "/diet/**", "/workout/**", "/ws/**", "/message/**", "/guest/**",  "/dietRecommendation/**").permitAll().requestMatchers("/home/**").hasAuthority("GUEST").anyRequest().authenticated())
                    .authorizeHttpRequests(auth ->
                            auth.requestMatchers("/user/login", "/user/register", "/ws/**", "/admin/**")
                            .permitAll()
                                    .requestMatchers("/user/**").hasAnyAuthority("GUEST", "TRAINER")
                                    //.requestMatchers("/home/**").hasAuthority("GUEST")
                                    .requestMatchers(HttpMethod.POST, "/workout/{userId}/{date}").hasAuthority("GUEST")
                                    .requestMatchers(HttpMethod.POST, "/workout/saveTrainer/{userId}/{guestId}/{date}").hasAuthority("TRAINER")
                                    .requestMatchers(HttpMethod.GET,"/workout/{id}").hasAuthority("GUEST")
                                    .requestMatchers(HttpMethod.PUT,"/workout/{workoutId}").hasAuthority("GUEST")
                                    .requestMatchers(HttpMethod.DELETE, "/workout/{guestId}/{date}").hasAuthority("GUEST")
                                    .requestMatchers(HttpMethod.GET,"/workout/recentlyUsedExercise/{userId}").hasAuthority("GUEST")
                                    .requestMatchers(HttpMethod.GET, "/workout/exerciseRegularity/{userId}").hasAuthority("GUEST")
                                    .requestMatchers(HttpMethod.GET, "/workout/trainerWorkoutPlanCount/{trainerId}").hasAuthority("TRAINER")
                                    .requestMatchers(HttpMethod.GET, "/workout/getAllWorkoutByGuestId/{guestId}").hasAuthority("GUEST")
                                    .requestMatchers(HttpMethod.GET,"/workout/getAllTrainerGuestWorkout/{trainerId}").hasAuthority("TRAINER")
                                    .requestMatchers(HttpMethod.GET, "/workout/setDone/{workoutId}").hasAuthority("GUEST")
                                    .anyRequest().authenticated())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .httpBasic(Customizer.withDefaults())
                    .exceptionHandling(exception -> exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                            // Dobd meg a saját kivételt és adj vissza REST API-n keresztül
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write("Unathorized: " + accessDeniedException.getMessage());
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
