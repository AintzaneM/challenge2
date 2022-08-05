package com.db.awmd.challenge.repository;
import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.exception.AmountTransactionException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

  private final Map<String, Account> accounts = new ConcurrentHashMap<>();
  private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();

  @Override
  public void createAccount(Account account) throws DuplicateAccountIdException {
    Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);

    if (previousAccount != null) {
      throw new DuplicateAccountIdException(
        "Account id " + account.getAccountId() + " already exists!");
    }
  }
  @Override
  public Account getAccount(String accountId) {
    Account account = accounts.get(accountId);

    return account;
  }
  @Override

  public Account getBalance(BigDecimal balance) {
    Account account= accounts.get(balance);
    return account;
  }

  @Override
  public void clearAccounts() {
    accounts.clear();
  }

  @Override
  public void createTransaction(Transaction transaction) throws AmountTransactionException {
    Transaction previousTransaction = transactions.putIfAbsent(transaction.getTransactionId(), transaction);

    if (previousTransaction != null) {
      throw new AmountTransactionException(
              "Transaction id " + transaction.getTransactionId() + " can't be made!");
    }
  }

  @Override
  public  Transaction getTransaction(String transactionId){
    return  transactions.get(transactionId);
  }

  @Override
  public Transaction getAccountFrom(String accountFrom) {
    Transaction transaction = transactions.get(accountFrom);
    return transaction;
  }

  @Override
  public Transaction getAccountTo(String accountTo) {
    return transactions.get(accountTo);
  }

  @Override
  public Transaction getAmount(double amount) throws AmountTransactionException  {
    return transactions.get(amount);
  }

}


