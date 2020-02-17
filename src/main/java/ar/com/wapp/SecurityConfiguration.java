package ar.com.wapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.com.wapp.business.IAuthTokenBusiness;
import ar.com.wapp.model.persistence.UsuarioRepository;
import ar.com.wapp.web.Constantes;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		String[] resources = new String[] { "/", "/index.html", "/favicon.png", "/app.js", "/config/**",
				"/directives/**", "/controllers/**", "/img/**", "/lib/**", "/services/**", "/views/**", "/images/*",
				Constantes.URL_DENY, "/webjars/**", "/swagger-resources/**", "/swagger-ui.html",
				Constantes.URL_CORE + "/version", "/vendor/**", Constantes.URL_USUARIOS };
		http.authorizeRequests().antMatchers(resources).permitAll();
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/dologin*").permitAll().anyRequest().authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.POST, Constantes.URL_USUARIOS).permitAll();

		http.formLogin().loginPage("/deny").loginProcessingUrl("/dologin").successForwardUrl(Constantes.URL_LOGINOK)
				.failureForwardUrl(Constantes.URL_DENY);

		http.logout().deleteCookies("JSESSIONID", "rm", "SESSION");
		http.httpBasic();
		http.addFilterAfter(new CustomTokenAuthenticationFilter(authTokenService, usuariosDAO),
				UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Autowired
	private IAuthTokenBusiness authTokenService;
	@Autowired
	private UsuarioRepository usuariosDAO;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}
