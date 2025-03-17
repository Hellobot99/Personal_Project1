package com.ktj4060.personal_project1.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;


import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity //SpringSecurity 사용을 위해 어노테이션
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		//스프링 시큐리티 기능 사용하고자 할 때 작성
		
		http
		.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
		.authorizeHttpRequests(authz -> authz.requestMatchers("/","/loginPage","logout","/noticeCheckPage","/registerPage","/menu/all")
		.permitAll()
		
		.requestMatchers(HttpMethod.POST,"/login","/register").permitAll()
		.requestMatchers("/resources/**","/WEB-INF/**").permitAll()
		.requestMatchers("/noticeAdd","/noticeModifyPage").hasAnyAuthority("ADMIN","MANAGER")
		.requestMatchers(HttpMethod.POST,"/menu/add").hasAnyAuthority("ADMIN","MANAGER")
		.requestMatchers(HttpMethod.POST,"/menu/update").hasAnyAuthority("ADMIN","MANAGER")
		.requestMatchers(HttpMethod.POST,"/menu/delete").hasAnyAuthority("ADMIN","MANAGER")
		.anyRequest().authenticated()
		)
		
		
		.formLogin(
				login->login.loginPage("/loginPage")
				.loginProcessingUrl("/login")
				.failureUrl("/loginPage?error=true")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(authenticationSuccessHandler())
				.permitAll()
				)
		
		//로그아웃 url를 통해 로그아웃 진행

		.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true)//세션 무효화
		.deleteCookies("JSESSIONID")
		.permitAll()
		);

		return http.build();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SimpleUrlAuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws java.io.IOException ,jakarta.servlet.ServletException {
				//로그인 성공 시 작동
				HttpSession session = request.getSession();
				boolean isManager = authentication.getAuthorities().stream()
						.anyMatch(grantedAuthority->
						grantedAuthority.getAuthority().equals("ADMIN") ||
						grantedAuthority.getAuthority().equals("MANAGER"));
				if(isManager) {
					session.setAttribute("MANAGER", true);
				}
				session.setAttribute("username",authentication.getName());
				session.setAttribute("isAuthenticated", true);
				response.sendRedirect(request.getContextPath()+"/");
				super.onAuthenticationSuccess(request, response, authentication);
			};
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		//내 서버에서 프론트 - 백엔드 간 데이터 교환 가능하게
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080","https://localhost:8080"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
		org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	

	
}
