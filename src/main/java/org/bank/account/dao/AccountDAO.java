
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

    static final int UPDATE_ACCOUNT_TYPE=1;

    String InsertSQL="Insert into account(customer_id,account_number,account_type,account_status)" +
            "VALUES(?,?,?,?)";

    String GetByCustomerIdSQL = "SELECT * FROM account WHERE customer_id = ?";

    String UpdateSQL="Update Account SET account_type='MAJOR' WHERE customer_id = ? and account_type='MINOR'";


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
            throw new DataException("Failed to insert",e);
        }
    }

    public List<Account> getAccountsByCustomerId(long customerID) throws SQLException {
        List<Account> AccountList=new ArrayList<>();
        try(Connection con= DbConnection.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(GetByCustomerIdSQL)){

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


    public int UpdateAccount(long customerID ) throws SQLException {
        try (Connection con = DbConnection.getConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(UpdateSQL)) {

            ps.setLong(UPDATE_ACCOUNT_TYPE, customerID);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataException("failed to convert account from minor to major");
        }
    }
}
