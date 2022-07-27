package uz.oliymahad.oliymahadquroncourse.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Role;
import uz.oliymahad.oliymahadquroncourse.exception.JwtValidationException;
import uz.oliymahad.oliymahadquroncourse.security.oauth2.UserPrincipal;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static uz.oliymahad.oliymahadquroncourse.security.jwt.AuthConst.*;

@Service
public class JWTokenProvider {

    private static final String SECRET_ACCESS = "K1dBoiDQ9kt6axC0c5J86lX9UFkjn1dmoDWL5heshK2uM0Rym";

    private static final String SECRET_REFRESH = "9WylsYBO6od7KC12CcPpU2bn1g8ysCAac9bB0DrBVvYk2gEk";

    private static final SecretKey ACCESS_KEY = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_ACCESS));

    private static final SecretKey REFRESH_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_REFRESH));


    private long accessTokenExpirationTime = 1000 * 60 * 60;

    private long refreshTokenExpirationTime = 1000 * 60 * 60 * 24;


    public String generateAccessToken(User user) {
        return AUTH_TYPE + generateToken(user, false);
    }
    public String generateAccessToken(UserPrincipal user) {
        return AUTH_TYPE + generateToken(user, false);
    }

    public String generateRefreshToken(User user) {
        return AUTH_TYPE + generateToken(user, true);
    }

    public boolean validateAccessToken(String accessToken) {
        return parseToken(accessToken, false) != null;
    }

    public String validateRefreshToken(String refreshToken) {
        return parseToken(refreshToken, true)
                .getSubject();
    }

    public Authentication getAuthorities(String jwtAccessToken) {
        Claims claims = parseToken(jwtAccessToken, false);
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                getAuthorities(claims.get(ROLE_VALUE, Integer.class))
        );
    }

//    TODO - check user principle while jwt-token generation with user email and phone number
    private String generateToken(User user, boolean forRefresh) {
        return Jwts.builder()
                .setSubject(user.getEmail() != null?  user.getEmail() : user.getPhoneNumber())
                .claim(ROLE_VALUE, user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (forRefresh ? refreshTokenExpirationTime : accessTokenExpirationTime)))
                .signWith(forRefresh ? REFRESH_KEY : ACCESS_KEY,SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateToken(UserPrincipal user, boolean forRefresh) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(ROLE_VALUE, user.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (forRefresh ? refreshTokenExpirationTime : accessTokenExpirationTime)))
                .signWith(forRefresh ? REFRESH_KEY : ACCESS_KEY ,SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(final String token, boolean forRefresh) {
        try {
            return Jwts.parserBuilder().setSigningKey(forRefresh ? REFRESH_KEY : ACCESS_KEY)
                    .build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw new JwtValidationException(TOKEN_EXPIRED);
        } catch (Exception ex) {
            throw new JwtValidationException(INVALID_TOKEN);
        }
    }

    private Set<GrantedAuthority> getAuthorities(final int roleValue) {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : Role.values()) {
            if ((roleValue & role.flag) == role.flag)
                authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }
}