package io.gitplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.gitplanner.client.GitHubClient;
import io.gitplanner.client.model.GitHubRequestModel;
import io.gitplanner.client.model.GitHubResponseModel;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class OauthController {

    @Value("${oauth.github.client_id}")
    String clientId;

    @Value("${oauth.github.client_secret}")
    String clientSecret;


    @Autowired
    private GitHubClient client;

    @GetMapping(value = "/github/profiles")
    private Mono<?> profiles() {
        return Mono.just("{name: 'chris'}");
    }

    @PostMapping(value = "/github/authorize")
    private Mono<GitHubResponseModel> authorize(@RequestBody GitHubRequestModel params) {
        try {
            params.setClientId(clientId);
            params.setClientSecret(clientSecret);

            ResponseEntity<GitHubResponseModel> accessTokenInfo = client.getAccessToken(params);
            GitHubResponseModel body = accessTokenInfo.getBody();

            if(!HttpStatus.OK.equals(accessTokenInfo.getStatusCode()) || body.isUnauthorized()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, body.getError());
            }

            return Mono.just(body);

        }catch (Exception e) {
            return Mono.error(e);
        }
    }
}