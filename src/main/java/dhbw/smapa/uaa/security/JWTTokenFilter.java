package dhbw.smapa.uaa.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTTokenFilter extends GenericFilterBean {

    private final JWTTokenProvider JWTTokenProvider;

    JWTTokenFilter(JWTTokenProvider JWTTokenProvider) {
        this.JWTTokenProvider = JWTTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = JWTTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null && JWTTokenProvider.validateToken(token)) {
            Authentication auth = JWTTokenProvider.getAuthentication(token);
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
    }


}
