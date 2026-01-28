package org.bank.account.model;

public class Account {
    private  long accountId;
    private long customerId;
    private String accountNumber;
    private String accountType;
    private String accountStatus;


    public Account(){
        this.accountType="MINOR";
        this.accountStatus="ACTIVE";
    }

    public long getAccountId() {
        return accountId;
    }

    public void  setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
