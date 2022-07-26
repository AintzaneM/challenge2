package com.db.awmd.challenge.domain;
import com.db.awmd.challenge.service.EmailNotificationService;
import com.db.awmd.challenge.service.NotificationService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data

public class Transaction implements Runnable{
    @NotNull
    private final  String transactionId;
    private  final Account accountFrom;
    private final  Account accountTo;
    @NotNull
    @Min(value = 0, message = "Amount transferred must be positive.")
    private final double amount;
    @Autowired
    private NotificationService notificationService;
    public Transaction(String transactionId, Account accountFrom, Account accountTo, double amount) {

        this.transactionId = transactionId;
        this. accountFrom =  accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    @JsonCreator
    public Transaction(
            @JsonProperty("transactionId") String transactionId,
            @JsonProperty("amount") double amount,
            @JsonProperty("accountFrom") Account  accountFrom,
            @JsonProperty("accountTo") Account accountTo)  {

        this.transactionId = transactionId;
        this.amount = amount;
        this.accountFrom =  accountFrom;
        this.accountTo = accountTo;
    }


    @Override
    public void run() {
        EmailNotificationService notificationService = new EmailNotificationService();

        synchronized (accountFrom) {
            System.out.println("Balance from " + accountFrom.getAccountId() + ": " + accountFrom.getBalance());
            System.out.println("Balance from " +accountTo.getAccountId() + ": " + accountTo.getBalance());
            accountFrom.withdraw(amount);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }

            synchronized (accountTo) {
                accountTo.deposit(amount);
                System.out.println("Balance from " + accountFrom.getAccountId() + ": " + accountFrom.getBalance());
                System.out.println("Balance from " +accountTo.getAccountId() + ": " + accountTo.getBalance());

            }
            notificationService.notifyAboutTransfer(getAccountTo(), "The account with ID: " +accountFrom.getAccountId()+ " has transferred " + amount+ " into your account.");
            notificationService.notifyAboutTransfer(getAccountFrom(), "The transaction to the account with ID: " +accountTo.getAccountId()+ " has been completed for the amount of: " + amount + ".");
        }
        System.out.println("Transaction completed! " +amount+ " have been transferred from " + accountFrom.getAccountId() + " to " + accountTo.getAccountId());
        System.out.println("-----");
    }
}
