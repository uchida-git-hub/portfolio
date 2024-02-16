package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	@Qualifier("SuccessHandler")
	private AuthenticationSuccessHandler successHandler;
	
	@Autowired
	@Qualifier("UserDetailsServiceImpl")
	private UserDetailsService userDetailsService;

	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(auth -> auth
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					.requestMatchers("/login/login" , "/login/signup").permitAll()	//ログインフォームとユーザー登録はログインしていなくてもアクセス可能
					.requestMatchers("/admin/**").hasAnyAuthority("ROLE_admin") //adminのみにアクセスを許可する
					.anyRequest().authenticated()
					)
			.formLogin(login ->login
					.loginPage("/login/login")
					.loginProcessingUrl("/login/login")
					.usernameParameter("userId")
					.passwordParameter("password")
					.defaultSuccessUrl("/user/mypage",true)
					.successHandler(successHandler)
					.permitAll()
					)
			.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//					.logoutUrl("/logout")
//					.logoutSuccessUrl("/logout")
					.addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.COOKIES)))
					.permitAll()
					)	//cookieの削除
			.sessionManagement(session -> session.invalidSessionUrl("/error/session"))
			.rememberMe(remember -> remember.key("uniqueKey")	//トークンの識別キー
					.rememberMeParameter("remember-me")		//checkboxのname属性
					.rememberMeCookieName("remember-me")	//Cookie名
					.tokenValiditySeconds(2592000) 			//有効期限30日
					);
		
		return http.build();
		
	}
	
	
	
}
