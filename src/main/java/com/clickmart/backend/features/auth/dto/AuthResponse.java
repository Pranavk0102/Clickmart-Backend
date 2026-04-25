package com.clickmart.backend.features.auth.dto;


public class AuthResponse {

	private String token;
	private String refreshToken;
	private String type;
	private UserDTO user;
	public AuthResponse(String token, String refreshToken, String type, UserDTO user) {
		this.token = token;
		this.refreshToken = refreshToken;
		this.type = type;
		this.user = user;
	}
	public AuthResponse() {
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
}
