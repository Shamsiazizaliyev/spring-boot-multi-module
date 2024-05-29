package az.ingress;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


@Service
public class JwtService {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public String findUsername(String token) {


       //  Function<Claims,String> f=(Claims c)->{return c.getSubject();};

          return exportToken(token, Claims::getSubject);
    }

    private <T>T exportToken(String token, Function<Claims,T> claimsTFunction) {


        final Claims claims= Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();////tokene gore  butun claimleri getirir

        return claimsTFunction.apply(claims);

     }

    private Key getKey() {
        byte [] key= Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(key);
    }

    public boolean tokenControl(String jwt, UserDetails userDetails) {
        //token icindeki userle userdetailsi eyni olmasi  ve tokenin kecerliyini yoxluyur
        final String username=findUsername(jwt);

        return (username.equals(userDetails.getUsername()) && ! exportToken(jwt,Claims::getExpiration).before(new Date()));
    }

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();


    }
}
