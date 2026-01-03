package org.bank.account.dao;

import org.bank.account.dbconfiguration.DbConnection;
import org.bank.account.model.Account;
import org.bank.account.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAO {


    private static final Logger log= LoggerFactory.getLogger(AccountDAO.class);


    static final int INSERT_CUSTOMER_ID=1;
    static final int INSERT_ACCOUNT_NUMBER=2;
    static final int INSERT_ACCOUNT_TYPE=3;
    static final int INSERT_ACCOUNT_STATUS=4;

    String InsertSQL="Insert into account(customer_id,account_number,account_type,account_status)" +
            "VALUES(?,?,?,?)";

    String SelectAllSQL="Select * from account";


    public void insert(Account account){

        try(Connection con= DbConnection.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(InsertSQL)){

            ps.setLong(INSERT_CUSTOMER_ID,account.getCustomerId());
            ps.setString(INSERT_ACCOUNT_NUMBER,account.getAccountNumber());
            ps.setString(INSERT_ACCOUNT_TYPE,account.getAccountType());
            ps.setString(INSERT_ACCOUNT_STATUS, account.getAccountStatus());

            int changedRows=ps.executeUpdate();

            if(changedRows==0){
                log.error("Insert failed,fields are not inserted in account:{}",account.getAccountId());
            }

            else{
                log.info("sucessfully values are inserted in account:{}",account.getAccountId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
