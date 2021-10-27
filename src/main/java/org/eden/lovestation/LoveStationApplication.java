package org.eden.lovestation;

import org.eden.lovestation.util.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties
public class LoveStationApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LoveStationApplication.class, args);
    }

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public void run(String... args) throws Exception {
        // two month
        jwtUtil.setLifeTime(86400);
        String userID = "ba53b215-73b3-4d8b-8711-b932f5c2646c";
        String referralEmployeeID = "0df735d1-f7c9-488d-b684-3f607321df27";
        String adminID = "201c200d-27cc-4a2d-8768-bd2b8853ccba";
        String landlordID = "2d715366-fb97-4e85-adc8-210886710b17";
        String volunteerID = "d7d5b3fb-a2de-45cc-aeb9-097cc3f457ec";
        String firmID = "405135d9-bfc0-4a74-b5c7-a2343d99e4ce";

        String userToken = jwtUtil.sign(userID, "user");
        String referralEmployeeToken = jwtUtil.sign(referralEmployeeID, "referral_employee");
        String adminToken = jwtUtil.sign(adminID, "admin");
        String landlordToken = jwtUtil.sign(landlordID, "landlord");
        String volunteerToken = jwtUtil.sign(volunteerID, "volunteer");
        String firmToken = jwtUtil.sign(firmID, "firm");

        System.out.println("userToken: " + userToken);
        System.out.println("referralEmployeeToken: " + referralEmployeeToken);
        System.out.println("adminToken: " + adminToken);
        System.out.println("landlordToken: " + landlordToken);
        System.out.println("volunteerToken: " + volunteerToken);
        System.out.println("firmToken: " + firmToken);

    }
}
