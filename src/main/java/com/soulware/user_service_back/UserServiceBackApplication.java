package com.soulware.user_service_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class UserServiceBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceBackApplication.class, args);
    }

}
