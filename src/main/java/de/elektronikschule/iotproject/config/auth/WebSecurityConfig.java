package de.elektronikschule.iotproject.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
   @EnableWebSecurity: Enable web security support and MVC integration
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

   // Define URL paths to secure in the application. Only / and home paths can be accessed without authentication.
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests((requests) ->
            requests.requestMatchers("/", "/home")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
          .formLogin((form) ->
                form.loginPage("/login")
                    .permitAll())
          .logout(LogoutConfigurer::permitAll);

      return http.build();
   }

   // Set up in-memory user
   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails user = User.withDefaultPasswordEncoder()
                             .username("user")
                             .password("password")
                             .roles("USER")
                             .build();

      return new InMemoryUserDetailsManager(user);
   }
}
