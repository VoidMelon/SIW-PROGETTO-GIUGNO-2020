package it.uniroma.siw.romatre.progetto.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static it.uniroma.siw.romatre.progetto.model.Utente.ADMIN_ROLE;




@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	DataSource datasource;
	
	public void configure (HttpSecurity http) throws Exception{
		
		http
		      .authorizeRequests()
		      
		      .antMatchers(HttpMethod.GET, "/" , "/index","/login","/users/register").permitAll()
		
		      .antMatchers(HttpMethod.POST, "/login","/users/register").permitAll()
		      
		      .antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
		      
		      .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
		      
		      .anyRequest().authenticated()
		      
		      .and().formLogin()
		      
		      .defaultSuccessUrl("/home")
		      
		      .and().logout()
		      
		      .logoutUrl("/logout")
		      
		      .logoutSuccessUrl("/index")
		      
		      .invalidateHttpSession(true)
		      
		      .clearAuthentication(true).permitAll();
		      
		      
		      
		      
	}
	
	
	
	public void configure (AuthenticationManagerBuilder auth) throws Exception{
		
		auth.jdbcAuthentication()
		    
		
		
		    .dataSource(this.datasource)
		      
		    .authoritiesByUsernameQuery("SELECT username, role FROM utente WHERE username=?")
		      
		    .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM utente WHERE username=? ");
		
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
