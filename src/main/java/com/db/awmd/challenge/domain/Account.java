package com.db.awmd.challenge.domain;

import com.db.awmd.challenge.service.NotificationService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class Account {

  @NotNull

  private final String accountId;

  @NotNull
  @Min(value = 0, message = "Initial balance must be positive.")
  private  BigDecimal balance ;

  public Account(String accountId) {
    this.accountId = accountId;
    this.balance = BigDecimal.ZERO;
  }
  @JsonCreator
  public Account(@JsonProperty("accountId") String accountId,
    @JsonProperty("balance") BigDecimal balance) {
    this.accountId = accountId;
    this.balance = balance;
  }
  public void withdraw(double amount) {
    balance = balance.subtract(BigDecimal.valueOf(amount));
    System.out.println(accountId +  " withdrawal of: " + amount );
  }
  public void deposit(double amount) {
    balance = balance.add(BigDecimal.valueOf(amount));
    System.out.println(accountId + " deposit of: " + amount );
  }
  public BigDecimal getBalance() {
    return balance;
  }
}
