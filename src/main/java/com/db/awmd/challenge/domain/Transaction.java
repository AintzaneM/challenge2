package com.db.awmd.challenge.domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Data

public class Transaction {

    @NotNull
    private   String transactionId;
    private String    accountFrom;
    private   String accountTo;
    @NotNull
    @Min(value = 0, message = "Amount transferred must be positive.")
    private  double amount;

    public Transaction(String transactionId, String accountFrom, String accountTo, double amount) {
        this.transactionId = transactionId;
        this.accountFrom =  accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    @JsonCreator (mode = JsonCreator.Mode.PROPERTIES)
    public Transaction(
            @JsonProperty("transactionId") String transactionId,
            @JsonProperty("amount") double amount,
            @JsonProperty("accountFrom") String  accountFrom,
            @JsonProperty("accountTo") String accountTo)  {

        this.transactionId = transactionId;
        this.accountFrom =  accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }
    public Transaction() {}
}
