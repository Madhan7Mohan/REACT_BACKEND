package net.springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OtpRequest {

    @Id
    private String username;
    private String otp;

    public OtpRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OtpRequest(String username, String otp) {
        this.username = username;
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
