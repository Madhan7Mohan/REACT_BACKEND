package net.springboot.repository;

public interface OtpRepository {



        void storeOtp(String username, String otp);

        String getStoredOtp(String username);



}
