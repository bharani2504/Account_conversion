package org.bank.account.dao;


import org.bank.account.dbconfiguration.DbConnection;
import org.bank.account.exception.DataException;

import org.bank.account.model.Nominee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NomineeDAO {
    private static final Logger log= LoggerFactory.getLogger(AccountDAO.class);


    static final int INSERT_ACCOUNT_ID=1;
    static final int INSERT_NOMINEE_NAME=2;
    static final int INSERT_RELATIONSHIP=3;
    static final int INSERT_IS_GUARDIAN =4;

    static final int UPDATE_GUARDIAN=1;
    static final int DELETE_BRANCH_ID=1;
    static final int GET_ACCOUNT_BY_ACCOUNTID=1;
    static String InsertSQL="Insert into nominee(account_id,nominee_name,relationship, is_guardian)" +
            "VALUES(?,?,?,?)";

    static  String GetByAccountIdSQL=" Select * from nominee where account_id=? ";
    static String UpdateGuardianSQL="Update nominee set is_guardian=False " +
            "where account_id IN (Select account_id from account where customer_id = ?)";

    static String DELETE_SQL= "Select * from nominee where account_id=?";


    public static void insert(Nominee nominee){

        try(Connection con= DbConnection.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(InsertSQL)){
            ps.setLong(INSERT_ACCOUNT_ID,nominee.getAccountId());
            ps.setString(INSERT_NOMINEE_NAME,nominee.getNomineeName());
            ps.setString(INSERT_RELATIONSHIP,nominee.getRelationship());
            ps.setBoolean(INSERT_IS_GUARDIAN, nominee.isGuardian());

            int changedRows=ps.executeUpdate();

            if(changedRows==0){
                log.error("Insert failed,fields are not inserted in nominee:{}",nominee.getNomineeId());
            }

            else{
                log.info("sucessfully values are inserted in nominee:{}",nominee.getNomineeId());
            }

        } catch (SQLException e) {
            throw new DataException("failed to insert",e);
        }
    }
    public List<Nominee> getNomineeByAccountId(long accountId) throws SQLException {
        List<Nominee> NomineeList =new ArrayList<>();
        try(Connection con= DbConnection.getConnect().getConnection();
            PreparedStatement ps=con.prepareStatement(GetByAccountIdSQL)){

            ps.setLong(GET_ACCOUNT_BY_ACCOUNTID,accountId);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                NomineeList.add(mapping(rs));
            }
            log.info("successfully fetched customer details,count={}", NomineeList.size());
            return NomineeList;
        } catch (SQLException e) {
            throw new DataException("Failed to fetch customer details",e);
        }
    }

    public Nominee mapping(ResultSet rs) throws SQLException {
        Nominee nominee=new Nominee();

        nominee.setNomineeId(rs.getLong("nominee_id"));
        nominee.setAccountId(rs.getLong("account_id"));
        nominee.setNomineeName(rs.getString("nominee_name"));
        nominee.setRelationship(rs.getString("relationship"));
        nominee.setGuardian(rs.getBoolean("is_guardian"));

        return nominee;
    }
    public int UpdateNominee(long customerID ) throws SQLException {
        try (Connection con = DbConnection.getConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(UpdateGuardianSQL)) {

            ps.setLong(UPDATE_GUARDIAN, customerID);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataException("failed to update guardian status",e);
        }
    }

    public void delete(long accountId) {
        try(Connection con = DbConnection.getConnect().getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_SQL)){
            ps.setLong(DELETE_BRANCH_ID, accountId);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                log.error("Delete failed: no nominee found with this id:{}", accountId);

            } else {
                log.info("Successfully deleted nominee with id:{}", accountId);

            }


        } catch (SQLException e) {
            throw new DataException("Failed to Delete Branch ",e);
        }
    }


}
