package com.asn.smarthelmetproject.service;

import com.asn.smarthelmetproject.model.User;

public interface UserService {
    User register(String phoneNumber, String name); // ✅ updated method signature

    User verifyOtp(String phoneNumber, String otp, String name); // ✅ include name for OTP verification
}
