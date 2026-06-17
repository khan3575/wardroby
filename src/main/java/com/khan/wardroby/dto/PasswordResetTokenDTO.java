package com.khan.wardroby.dto;

public class PasswordResetTokenDTO {
    private Long userId;
    private String token;
    private Boolean used;
    private Boolean expired;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "PasswordResetTokenDTO{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                ", used=" + used +
                ", expired=" + expired +
                '}';
    }


}
