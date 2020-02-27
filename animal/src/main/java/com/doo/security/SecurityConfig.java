package com.doo.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	UsersService us;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		log.info("security config..............");;

		http.authorizeRequests().antMatchers("/**").permitAll();
		
		http.formLogin().loginPage("/member/login");
		http.logout().logoutUrl("/member/logout").invalidateHttpSession(true).logoutSuccessUrl("/");; //

		//로그인한 사용자..... 정확히 모르겠어
		http.userDetailsService(us);

	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/material-pro/**");
		//super.configure(web);
	}

//	private PersistentTokenRepository getJDBCRepository() {
//
//		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
//		repo.setDataSource(dataSource);
//		return repo;
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		log.info("build Auth global........");

		//메모리에(이번 한번만) 저장된 정보로 로그인하기
		// Member(user=manager, password=1111, role=admin)가 로그인한거고
		// roles에 따라 위에서 정한 경로마다 roles을 확인해서 같은경우에만 보여줌.
		// 아직 admin, manager 대소관계는 안 준듯
		//auth.inMemoryAuthentication()
		//	.withUser("user")
		//	.password("{noop}1111") //패스워드 암호화 안함
		//	.roles("MANAGER");
		
		//String query1 = "SELECT uid username, '{noop}'||upw password, true enabled FROM tbl_members WHERE uid= ?";
		//String query2 = "SELECT member uid, role_name role FROM tbl_member_roles WHERE member = ?";
		//
		//auth.jdbcAuthentication()
		//	.dataSource(dataSource)
		//	.usersByUsernameQuery(query1) //username, password, enabled 컬럼이 있어야 한다.
		//	.rolePrefix("ROLE_") //BASIC,MANAGER,ADMIN을 ROLE_BASIC,ROLE_MANAGER,ROLE_ADMIN
		//	.authoritiesByUsernameQuery(query2);
		
		auth.userDetailsService(us).passwordEncoder(passwordEncoder());

	}

}
