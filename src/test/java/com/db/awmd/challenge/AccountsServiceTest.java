package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.exception.AmountTransactionException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.AccountsService;
import java.math.BigDecimal;

import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsServiceTest {

  @Autowired
  private AccountsService accountsService;

  @Test
  public void addAccount() throws Exception {
    Account account = new Account("Id-123");
    account.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(account);

    assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(account);
  }

  @Test
  public void addAccount_failsOnDuplicateId() throws Exception {
    String uniqueId = "Id-" + System.currentTimeMillis();
    Account account = new Account(uniqueId);
    this.accountsService.createAccount(account);

    try {
      this.accountsService.createAccount(account);
      fail("Should have failed when adding duplicate account");
    } catch (DuplicateAccountIdException ex) {
      assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
    }
  }

  @Test
  public void addTransaction() throws Exception {
    Account accountFrom = new Account("Id-123", new BigDecimal(20));
    this.accountsService.createAccount(accountFrom);
    Account accountTo = new Account("Id-1234", new BigDecimal(40));
    this.accountsService.createAccount(accountTo);
    Transaction transaction1 = new Transaction("t1","Id-123", "Id-1234", 10);
    this.accountsService.createTransaction(transaction1);
    Transaction transaction2 = new Transaction("t2", "Id-1234", "Id-123", 5);
    this.accountsService.createTransaction(transaction2);

    try{
      this.accountsService.createTransaction(transaction1);
      this.accountsService.createTransaction(transaction2);
      fail("Should have failed when accountFromId = accountToId");
    }catch(AmountTransactionException ex){
      assertThat(ex.getMessage()).isEqualTo("Transaction id " + transaction1.getTransactionId() + " can't be made!");}

  }

  }
