package com.lomatech.cms.user.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random random = new SecureRandom();
    private final String ALPHA_NUMERIC = "132321ABCDEFGHIJKLMNOPQRST";

    public String generateUserId(int length){
        return generateRandomUserId(length);
    }

    private String generateRandomUserId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < 10; i++) {
            sb.append(ALPHA_NUMERIC.charAt(random.nextInt(ALPHA_NUMERIC.length())));
        }
        return sb.toString();
    }
}
