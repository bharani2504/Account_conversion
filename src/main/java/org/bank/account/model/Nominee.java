package org.bank.account.model;

public class Nominee {
    private long nomineeId;
    private long accountId;
    private String nomineeName;
    private String relationship;
    private boolean isGuardian;

    public Nominee(){
        this.isGuardian=true;
    }
    public long getNomineeId() {
        return nomineeId;
    }

    public void setNomineeId(long nomineeId) {
        this.nomineeId = nomineeId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    public boolean isGuardian() {
        return isGuardian;
    }

    public void setGuardian(boolean guardian) {
        isGuardian = guardian;
    }

}
