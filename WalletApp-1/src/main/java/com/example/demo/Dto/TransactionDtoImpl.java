package com.example.demo.Dto;

public class TransactionDtoImpl implements TransactionDtoInter {

    private int transactionId;
    private String transactionType;

    public TransactionDtoImpl(int transactionId, String transactionType) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
    }

    @Override
    public int getTransactionId() {
        return transactionId;
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

}
