package org.jobportal.portal.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.jobportal.portal.constants.ApplicationConstants;
import org.jobportal.portal.entity.JobPortalUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@PropertySource(value="classpath:jwt.properties")
public class JwtUtil {
    public final Environment env;

    public String generateJwtToken(Authentication authentication) {
        String jwtToken;
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        var fetchedUser = (JobPortalUser) authentication.getPrincipal();
        jwtToken = Jwts.builder().issuer("Job Portal").subject("JWT Token")
                .claim("name", fetchedUser.getName())
                .claim("email", fetchedUser.getEmail())
                .claim("mobileNumber", fetchedUser.getMobileNumber())
                .claim("roles", authentication.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date((new java.util.Date()).getTime() + 24 * 60 * 60 * 1000))
                .signWith(secretKey).compact();
        return jwtToken;
    }
}
