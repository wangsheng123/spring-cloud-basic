package com.ws.oauth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;

public class Test {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // $2a$10$06msMGYRH8nrm4iVnKFNKOoddB8wOwymVhbUzw/d3ZixD7Nq8ot72
        //$2a$10$06msMGYRH8nrm4iVnKFNKOoddB8wOwymVhbUzw/d3ZixD7Nq8ot72
        String webApp = bCryptPasswordEncoder.encode("123456");
        System.out.println(webApp);
        boolean matches = bCryptPasswordEncoder.matches("123456", "$2a$10$B98FO3XCTvvQdp1y2kMMaugfOij//e9AKnhZpXI/MMtKkymcWaRkq");
        System.out.println(matches);
    }
}
