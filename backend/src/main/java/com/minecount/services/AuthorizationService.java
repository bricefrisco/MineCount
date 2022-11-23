package com.minecount.services;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.minecount.models.Claim;
import com.minecount.models.exceptions.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.security.interfaces.RSAPublicKey;

@ApplicationScoped
public class AuthorizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);
    private static final String TENANT_URL = "https://dev--344u2vv.auth0.com/";
    private static final String AUDIENCE = "/minecount";
    private static final String USER_ID_CLAIM = "/minecount/user_id";

    public static String validate(String token, Claim claim) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new AuthorizationException("Unauthorized.");
        }

        try {
            token = token.replace("Bearer ", "");

            JwkProvider provider = new UrlJwkProvider(TENANT_URL);
            DecodedJWT jwt = JWT.decode(token);
            Jwk jwk = provider.get(jwt.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

            JWTVerifier verifier = JWT.require(algorithm)               // 1. Verify JWT against public key
                    .withIssuer(TENANT_URL)                             // 2. Verify the issuer
                    .withAudience(AUDIENCE)                             // 3. Verify token audience claims
                    .withArrayClaim("permissions", claim.getName())  // 4. Verify the specified claim
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim(USER_ID_CLAIM).asString();
        } catch (Exception e) {
            LOGGER.warn(String.format("Authorization request failed for token '%s': %s", token, e.getMessage()));
            throw new AuthorizationException("Unauthorized");
        }
    }
}
