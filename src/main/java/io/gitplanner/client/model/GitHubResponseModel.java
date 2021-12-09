package io.gitplanner.client.model;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GitHubResponseModel extends BaseModel {
	private String accessToken;
	private String scope;
	private String tokenType;
	private String error;
	private String error_description;
	private String error_uri;

	public boolean isUnauthorized() {
	    return ObjectUtils.isEmpty(accessToken);
	}
}
