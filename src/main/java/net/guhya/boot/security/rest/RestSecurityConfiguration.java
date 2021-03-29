package net.guhya.boot.security.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import net.guhya.boot.security.rest.filter.RestAuthenticationFilter;
import net.guhya.boot.security.rest.filter.RestAuthorizationFilter;
import net.guhya.boot.security.rest.handler.RestLoginFailureHandler;
import net.guhya.boot.security.rest.handler.RestLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private RestLoginFailureHandler failureHandler;
	
	@Autowired
	private RestLoginSuccessHandler successHandler;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	private static final String[] AUTH_WHITELIST = {
			"/",
			"/favicon.ico",
			"/swagger-ui.html",
			"/swagger-resources",
			"/swagger-resources/**",
			"/resources/**"
	};
		
	/**
	 * Configuring authentication manager
	 */
	@Override
	protected void configure(HttpSecurity http) 
			throws Exception {
		
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/v1/users/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new RestAuthenticationFilter(authenticationManager()
                		, successHandler, failureHandler))
                .addFilter(new RestAuthorizationFilter(authenticationManager()))
                .exceptionHandling()
                	.authenticationEntryPoint(authenticationEntryPoint)
                	.accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

	/**
	 * Configuring web security
	 */
	@Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) 
    		throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailService)
			.passwordEncoder(new BCryptPasswordEncoder());
    }

	
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        
        return source;
    }	
    
}
