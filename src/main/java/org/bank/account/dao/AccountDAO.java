
package org.bank.account.dao;

import org.bank.account.dbconfiguration.DbConnection;
import org.bank.account.exception.DataException;
import org.bank.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {


    private static final Logger log= LoggerFactory.getLogger(AccountDAO.class);


    static final int INSERT_CUSTOMER_ID=1;
    static final int INSERT_ACCOUNT_NUMBER=2;
    static final int INSERT_ACCOUNT_TYPE=3;
    static final int INSERT_ACCOUNT_STATUS=4;

    static  final int GET_ACCOUNT_BY_CUSTOMERID=1;

    String InsertSQL="Insert into account(customer_id,account_number,account_type,account_status)" +
            "VALUES(?,?,?,?)";

    String GetByCustomerIdSql = "SELECT * FROM account WHERE customer_id = ?";




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

    public List<Account> getAccountsByCustomerId(long customerID) throws SQLException {
        List<Account> AccountList=new ArrayList<>();
        try(Connection con= DbConnection.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(GetByCustomerIdSql)){

            ps.setLong(GET_ACCOUNT_BY_CUSTOMERID,customerID);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                AccountList.add(mapping(rs));
            }
            log.info("successfully fetched customer details,count={}", AccountList.size());
            return  AccountList;
        } catch (SQLException e) {
            throw new DataException("Failed to fetch customer details",e);
        }
    }

    public Account mapping(ResultSet rs) throws SQLException {
        Account account =new Account();

        account.setAccountId(rs.getLong("account_id"));
        account.setCustomerId(rs.getLong("customer_id"));
        account.setAccountNumber(rs.getString("account_number"));
        account.setAccountType(rs.getString("account_type"));
        account.setAccountStatus(rs.getString("account_status"));
        return account;
    }
}
