package com.example.authorization;

import java.sql.Timestamp;

public class UserInfo {

    private String name;
    private String email;
    private String role;
    private String accessToken;
    private Timestamp accessTokenExpiresAt;

    public UserInfo(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole();
        this.accessToken = userEntity.getAccessToken();
        this.accessTokenExpiresAt = userEntity.getAccessTokenExpiresAt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Timestamp getAccessTokenExpiresAt() {
        return accessTokenExpiresAt;
    }

    public void setAccessTokenExpiresAt(Timestamp accessTokenExpiresAt) {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
    }
}
