package com.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity /* es extra para utilizar metodos en vez de tantas configuraciones */
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securiryFilterChain(HttpSecurity httpSecurity) throws Exception { /*configuracion de seguridad ejemplo que direccion esta libre o cual no*/
        return httpSecurity
                .httpBasic(Customizer.withDefaults())  /*solo para usuario y cotraseña*/
                .csrf(csrf -> csrf.disable())/*.crsf() defensa para vulnerabilidad para cuando se trabaja con formularios */
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(HttpMethod.GET, "/auth/hello").permitAll();
                    http.requestMatchers(HttpMethod.GET,"/auth/hello-secured").hasAuthority("CREATE");

                    http.anyRequest().denyAll(); /* se hace para denegar cualquier otra peticin que no contemple*/

                })
                .build();
    }

    @Bean /* este autentication manager se genrera a travez de otro objeto de springsecurity "AuthenticationConfiguration"*/
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); /* es el proveedor de authenticacion que nescesita un passwordEnconder y un userDetailService*/
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); /*utilizar el Bcryp para encyptar la contraseña*/
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetails = new ArrayList<>();
        userDetails.add
                (User.withUsername("Santiago") /*creo un usuario de userDetails simulando que lo traigo de la base de datos*/
                        .password("1234")
                        .roles("ADMIN")
                        .authorities("READ", "CREATE")
                        .build());
        userDetails.add
                (User.withUsername("daniel") /*creo un usuario de userDetails simulando que lo traigo de la base de datos*/
                        .password("1234")
                        .roles("USER")
                        .authorities("READ")
                        .build());

        return new InMemoryUserDetailsManager(userDetails);
    }

}