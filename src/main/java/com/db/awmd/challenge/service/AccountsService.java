package com.db.awmd.challenge.service;
import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.exception.AmountTransactionException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Objects;

@Service
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public void createAccount(Account account) {

    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

  public Account getBalance(BigDecimal balance) {
    return this.accountsRepository.getBalance(balance);
  }

  public void createTransaction(Transaction transaction) {
    EmailNotificationService notificationService = new EmailNotificationService();

    if (!Objects.equals(transaction.getAccountFrom(), transaction.getAccountTo())) {
      synchronized (transaction.getAccountFrom()) {
        System.out.println("TRANSACTION STARTS");
        System.out.println("Balance before withdraw");
        System.out.println("Balance from " + transaction.getAccountFrom() + ": " + getAccount(transaction.getAccountFrom()).getBalance());
        System.out.println("Balance from " + transaction.getAccountTo() + ": " + getAccount(transaction.getAccountTo()).getBalance());
        this.withdraw(transaction.getAccountFrom(), transaction.getAmount());
        try {
          Thread.sleep(3000);
        } catch (Exception e) {
          e.getMessage();
        }

        synchronized (transaction.getAccountTo()) {
          this.deposit(transaction.getAccountTo(), transaction.getAmount());
          System.out.println("Balance after deposit");
          System.out.println("Balance from " + transaction.getAccountFrom() + ": " + getAccount(transaction.getAccountFrom()).getBalance());
          System.out.println("Balance from " + transaction.getAccountTo() + ": " + getAccount(transaction.getAccountTo()).getBalance());
        }

        this.accountsRepository.createTransaction(transaction);
        System.out.println("TRANSACTION DONE");
        notificationService.notifyAboutTransfer(new Account(transaction.getAccountFrom()), "You have completed a transaction. Account from ID: " + transaction.getAccountFrom() + ". Account to ID: " + transaction.getAccountTo() + ". Amount transferred: " + transaction.getAmount() + ". Your balance is: " + getAccount(transaction.getAccountFrom()).getBalance());
        notificationService.notifyAboutTransfer(new Account(transaction.getAccountTo()), "You have received a transaction. From account ID: " + transaction.getAccountFrom() + ". To account ID: " + transaction.getAccountTo() + ". Amount transferred: " + transaction.getAmount() + ". Your balance is: " + getAccount(transaction.getAccountTo()).getBalance());
      }
      }else if (Objects.equals(transaction.getAccountFrom(), transaction.getAccountTo())) {
        System.out.println("TRANSACTION FAILS");
        throw new AmountTransactionException(
                "The accountIdFrom shouldn't be the same to the accountIdTo");
      }
    }

  public Account deposit (String accountId, double amount) throws AmountTransactionException {
    final Account account = this.accountsRepository.getAccount(accountId);
    BigDecimal balance = account.getBalance().add(BigDecimal.valueOf(amount));
    account.setBalance(balance);
    return account;
  }

  public Account withdraw(String accountId, double amount) throws AmountTransactionException, DuplicateAccountIdException {
     final Account account = this.accountsRepository.getAccount(accountId);
      BigDecimal balance = account.getBalance().subtract(BigDecimal.valueOf(amount));

      if(account.getBalance().compareTo(BigDecimal.valueOf(amount)) == -1) {
        throw new AmountTransactionException("Insufficient Balance");
      }
      if(amount <= 0) {
        throw new AmountTransactionException("Amount to transfer should be positive");
      }

      account.setBalance(balance);
      return account;
  }

  public Transaction getTransaction(String transactionId) {
    return this.accountsRepository.getTransaction(transactionId);
  }

  public Transaction getAccountFrom(String accountFrom) {
    return this.accountsRepository.getAccountFrom(accountFrom);
  }

  public Transaction getAccountTo(String accountTo) {
    return this.accountsRepository.getAccountTo(accountTo);
  }

  public Transaction getAmount(double amount) {
    return this.accountsRepository.getAmount(amount);
  }

}
