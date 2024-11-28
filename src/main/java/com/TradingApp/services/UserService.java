package com.TradingApp.services;

import com.TradingApp.domain.VerificationType;
import com.TradingApp.modals.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long userId) throws Exception;

    User enableTwoFactorAuthentication(VerificationType verificationType,String sendTo, User user);

    User updatePassword(User user,String newPassword);

}
