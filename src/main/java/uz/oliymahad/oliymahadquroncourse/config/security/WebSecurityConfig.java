package uz.oliymahad.oliymahadquroncourse.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.oliymahad.oliymahadquroncourse.controller.core.ControllerUtils;
import uz.oliymahad.oliymahadquroncourse.security.jwt.JWTokenFilter;
import uz.oliymahad.oliymahadquroncourse.security.oauth2.CustomOAuth2UserService;
import uz.oliymahad.oliymahadquroncourse.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import uz.oliymahad.oliymahadquroncourse.security.oauth2.OAuth2AuthenticationFailureHandler;
import uz.oliymahad.oliymahadquroncourse.security.oauth2.OAuth2AuthenticationSuccessHandler;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class WebSecurityConfig {

    private final JWTokenFilter jwTokenFilter;

    private final OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;

    private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().formLogin().disable().httpBasic().disable()
                .addFilterBefore(jwTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers(ControllerUtils.OPEN_PATH).permitAll()
//                .antMatchers(HttpMethod.GET, ControllerUtils.OPEN_FILES)
//                .permitAll()
//                .antMatchers("/api/v1/auth/**", "/oauth2/**")
                .antMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().authorizationEndpoint().baseUri("/oauth2/authorize").authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository)
                .and()
                .redirectionEndpoint().baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(oauth2AuthenticationSuccessHandler)
                .failureHandler(oauth2AuthenticationFailureHandler);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:3000", "*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(Arrays.asList("X-Auth-Token", "Authorization", "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
