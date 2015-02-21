package fr.charbo.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(final HttpSecurity http) throws Exception {

    http
    .formLogin().and()
    .authorizeRequests()
    .antMatchers("/index.html", "/admin/admin.html", "/login.html", "/vendor/**", "/**.js",  "/**/**.js","/**.css", "/search/**", "/").permitAll()
    .anyRequest().authenticated()
    .and().csrf()
    .csrfTokenRepository(this.csrfTokenRepository()).and()
    .addFilterAfter(this.csrfHeaderFilter(), CsrfFilter.class);
  }

  private Filter csrfHeaderFilter() {
    return new OncePerRequestFilter() {
      @Override
      protected void doFilterInternal(final HttpServletRequest request,
          final HttpServletResponse response, final FilterChain filterChain)
              throws ServletException, IOException {
        final CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
            .getName());
        if (csrf != null) {
          Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
          final String token = csrf.getToken();
          if ((cookie == null) || ((token != null)
              && !token.equals(cookie.getValue()))) {
            cookie = new Cookie("XSRF-TOKEN", token);
            cookie.setPath("/");
            response.addCookie(cookie);
          }
        }
        filterChain.doFilter(request, response);
      }
    };
  }

  private CsrfTokenRepository csrfTokenRepository() {
    final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    repository.setHeaderName("X-XSRF-TOKEN");
    return repository;
  }
}

