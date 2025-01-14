package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.entity.Users;
import com.service.UserDao;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDao userDao;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			Users user = userDao.findByUsername(username);
			if (user != null) {
				System.out.println(user.getUsername());
				return org.springframework.security.core.userdetails.User.withUsername(username)
						.password(user.getPassword()).roles(user.getRole()).build();
			} else {
				System.out.println(user != null);
			}
			throw new UsernameNotFoundException("User not found");
		}).passwordEncoder(passwordEncoder());
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(requests -> requests
                        .antMatchers("/video-library-student").hasRole("STUDENT")
                        .antMatchers("/joinactivity").hasRole("STUDENT")
                        .antMatchers("/view-video").hasAnyRole("STUDENT", "TEACHER")
                        .antMatchers("/forum").hasAnyRole("STUDENT", "TEACHER")
                        .antMatchers("/video-upload").hasRole("TEACHER")
                        .antMatchers("/video-library-teacher").hasRole("TEACHER")
                        .antMatchers("/school-submission").hasRole("TEACHER")
                        .antMatchers("/dashboard").hasRole("TEACHER")
                        .antMatchers("/createreport").hasRole("TEACHER")
                        .antMatchers("/AddActivity").hasRole("TEACHER")
                        .antMatchers("/videos/getVideos").hasRole("TEACHER")
                        .antMatchers("/validate-video").hasRole("ADMIN")
                        .antMatchers("/validated-video").hasRole("ADMIN")
                        .antMatchers("/crew-resource").hasRole("ADMIN")
                        .antMatchers("/manage-school").hasRole("ADMIN")
                        .antMatchers("/resource-page").hasRole("ADMIN")
                        .antMatchers("/school-list").hasRole("ADMIN")
                        .antMatchers("/school-profile").hasRole("ADMIN")
                        .antMatchers("/school-review").hasRole("ADMIN")
                        .antMatchers("/resources/**", "/css/**", "/js/**").permitAll()
                        .antMatchers("/register").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .map(auth -> auth.replace("ROLE_", ""))  // Remove ROLE_ prefix if present
                                    .findFirst()
                                    .orElse("");
                            request.getSession().setAttribute("role", role); 
                            
                         // Retrieve the user ID and store it in the session
                            Object principal = authentication.getPrincipal();
                            if (principal instanceof org.springframework.security.core.userdetails.User) {
                                String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
                                Users user = userDao.findByUsername(username);  // Fetch the user object from the database
                                if (user != null) {
                                    request.getSession().setAttribute("userId", user.getId());  // Store the user ID in the session
                                }
                            }
                        })
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .permitAll())
                .csrf().disable();
    }
}
// end class