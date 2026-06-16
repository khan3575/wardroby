package com.khan.wardroby.dto;

public class PasswordResetTokenDTO {
    private Long userId;
    private String TokenHash;
    private Boolean used;
    private Boolean expired;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTokenHash() {
        return TokenHash;
    }

    public void setTokenHash(String tokenHash) {
        TokenHash = tokenHash;
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
        return "ResetDTO{" +
                "userId=" + userId +
                ", TokenHash='" + TokenHash + '\'' +
                ", used=" + used +
                ", expired=" + expired +
                '}';
    }


}
