package net.springboot.service;

import net.springboot.repository.OtpRepository;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryOtpRepository implements OtpRepository {

    private Map<String, String> otpStorage = new HashMap<>();

    @Override
    public void storeOtp(String username, String otp) {
        otpStorage.put(username, otp);
    }

    @Override
    public String getStoredOtp(String username) {
        return otpStorage.get(username);
    }
}
