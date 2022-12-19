package com.example.ejercicio2.auth.model;

public class AuthResponse {

	private Integer id;
	private String email;
	private String accessToken;

	public AuthResponse() { }
	
	public AuthResponse(Integer id, String email, String accessToken) {
		this.id = id;
		this.email = email;
		this.accessToken = accessToken;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "AuthResponse [email=" + email + ", accessToken=" + accessToken + ", id=" + id + "]";
	}

}
