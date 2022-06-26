package usuario.apijwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import usuario.apijwt.service.ImplemetacaoUserDetailService;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity  extends WebSecurityConfigurerAdapter {

    @Autowired
    private ImplemetacaoUserDetailService implemetacaoUserDetailService;

    // => configuração de acessos de HTTP

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    // => validando usuario pelo token de acesso
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        // => ativando a permissão para acesso da pagina icial
        .disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()

                // => Url de Logout - Redireciona após o user deslogar do sistema
                .anyRequest().authenticated().and().logout().logoutSuccessUrl("index")

                // => Mapeia Url de logout e invalida usuario
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        /**
         * Filtra a requsição de login , para autenticação
         * Filtra demais requisções para verificar a presença do TOKEN JWT no heder HTTP
         */



    }



   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       /*
      => Service que vai consultar usuario no banco de dados
        */

       auth.userDetailsService(implemetacaoUserDetailService)

               // => Criptografia de senha de Acesso
               .passwordEncoder(new BCryptPasswordEncoder());
   }



}
