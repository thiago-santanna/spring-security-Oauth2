package com.tss.webapps.aulaspringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AulaSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AulaSpringSecurityApplication.class, args);
    }


    @RestController
    class HttpController {
        @GetMapping("/public")
        String publicRouter(){
            return "<h1>Página Pública, essa rota livre para o acesso!</h1>";
        }

        @GetMapping("/private")
        String privateRouter(){
            return """
                    <h1>Página Privada, essa rota é proibida o acesso!</h1>
                    """;
        }

        @GetMapping("/cookie")
        String privateRouter(@AuthenticationPrincipal OidcUser principal){
            System.out.println(principal.getClaims());

            return String.format("""
                    <h1>Página Privada, essa rota é proibida o acesso!</h1>
                    <h3>Princpal: %s</h3>
                    <h3>Email attribute: %s</h3>
                    <h3>Authorities: %s</h3>
                    <h3>JWT: %s</h3>
                    """, principal, principal.getAttribute("email"), principal.getAuthorities(), principal.getIdToken().getTokenValue());
        }

        @GetMapping("jwt")
        String jwt(@AuthenticationPrincipal Jwt jwt) {
            System.out.println(jwt);
            return String.format("""
                    <h1>Principal: %s\n</h1>
                    <h3>Email attibute: %s\n</h3>
                    <pre>JWT: %s</pre>
                    """, jwt.getClaims(), jwt.getClaim("email"), jwt.getTokenValue());
        }
    }
}
