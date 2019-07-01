package dhbw.smapa.uaa.security;

import dhbw.smapa.uaa.exception.JWTValidationException;
import dhbw.smapa.uaa.service.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static dhbw.smapa.uaa.security.SecurityConstants.EXPIRATION_TIME;

@Component
public class JWTTokenProvider {

    private final UserDetailsServiceImpl userDetailsService;
    private final String SECRET;

    @Autowired
    public JWTTokenProvider(@Value("${security.token.secret}") String secret, UserDetailsServiceImpl userDetailsService) {
        this.SECRET = secret;
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String username) {

        Date now = new Date();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JWTValidationException();
        }
    }

    public boolean validateToken(HttpServletRequest req) {
        String bearerToken = resolveToken(req);
        try {
            Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(bearerToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JWTValidationException();
        }
    }
}
