package com.db.awmd.challenge;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevChallengeApplication {

  public static void main(String[] args) {
    SpringApplication.run(DevChallengeApplication.class, args);
    System.out.println("Server Started....");
    final Account accA = new Account("Acc 1" );
    final Account accB = new Account("Acc 2");
    accA.deposit(1000.00);
    accB.deposit(1000.00);



    Transaction t1 = new Transaction("T01",  accA, accB, 100.00 );
    Transaction t2 = new Transaction("T02", accA, accB, 500.00  );


    t1.run();
    t2.run();

  }
}
