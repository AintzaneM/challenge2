package com.db.awmd.challenge.repository;
import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.exception.AmountTransactionException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

import java.math.BigDecimal;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);

  Account getBalance(BigDecimal balance);

  void clearAccounts();
  void createTransaction(Transaction transaction) throws AmountTransactionException;
  Transaction getTransaction(String transactionId);
  Transaction getAccountFrom(String accountFrom);
  Transaction getAccountTo(String accountTo);
  Transaction getAmount(double amount);

}



