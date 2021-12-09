package io.gitplanner.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.gitplanner.client.model.GitHubRequestModel;
import io.gitplanner.client.model.GitHubResponseModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GitHubClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${oauth.github.client_id}")
    private String id;

    @Value("${oauth.github.client_secret}")
    private String secret;

    public ResponseEntity<GitHubResponseModel> getAccessToken(GitHubRequestModel params) {
        log.debug("### Authorize access token ###");

        params.setClientId(id);
        params.setClientSecret(secret);

        log.debug("### params: " + params.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(params.toString(), headers);

        URI uri = URI.create("https://github.com/login/oauth/access_token");

        ResponseEntity<GitHubResponseModel> tokenInfo = restTemplate.postForEntity(uri, request, GitHubResponseModel.class);

        log.debug("### result: " + tokenInfo.getBody());

        return tokenInfo;
    }
}