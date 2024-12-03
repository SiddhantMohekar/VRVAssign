package vrv.vrvassign.Security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import vrv.vrvassign.Security.CustomOAuth2SuccessHandler;

@Configuration
public class WebSecurityConfig {
    
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    SecurityContextRepository securityContextRepository(){
        return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
         new HttpSessionSecurityContextRepository());
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    CookieClearingLogoutHandler cookies = new CookieClearingLogoutHandler("our-custom-cookies");
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(authorize->authorize.requestMatchers(HttpMethod.GET,"/eventapi/events/**","/showGetEvent","/getEvent")
        .hasAnyRole("USER","ADMIN")
        .requestMatchers(HttpMethod.GET,"/showCreateEvent","/createEvent","/createResponse")
        .hasAnyRole("ADMIN")
        .requestMatchers(HttpMethod.POST,"/getEvent").hasAnyRole("USER","ADMIN")
        .requestMatchers(HttpMethod.POST,"/eventapi/event","/saveEvent").hasRole("ADMIN")
        .requestMatchers("/","/login","/showReg","/registerUser").permitAll()
        ).oauth2Login(oauth2 -> oauth2.successHandler(new CustomOAuth2SuccessHandler())).logout((logout)-> logout.logoutSuccessUrl("/").deleteCookies("our-custom-cookies").invalidateHttpSession(true));

        

        http.securityContext(context->context.requireExplicitSave(true));
        return http.build();
    }

}
