package net.springboot.service;

import net.springboot.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public void generateOtp(String username) {
        String otp = generateRandomOtp();
        System.out.println("Generated OTP for " + username + ": " + otp);
        otpRepository.storeOtp(username, otp);

    }

    public boolean validateOtp(String username, String enteredOtp) {
        String storedOtp = otpRepository.getStoredOtp(username);
        return enteredOtp.equals(storedOtp);
    }

    private String generateRandomOtp() {
        return String.valueOf(100000 + new SecureRandom().nextInt(900000));
    }


}
