package com.db.awmd.challenge;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class DevChallengeApplication {
  public static void main(String[] args) throws Exception {

    SpringApplication.run(DevChallengeApplication.class, args);
    System.out.println(">>>>>>SERVER STARTED");
  }
}
