package org.bank.account.dao;

import org.bank.account.dbconfiguration.DbConnection;
import org.bank.account.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CustomerDAO {

private static final Logger log= LoggerFactory.getLogger(CustomerDAO.class);


    static final int INSERT_CUSTOMER_NAME=1;
    static final int INSERT_DATE_OF_BIRTH=2;
    static final int INSERT_GENDER=3;
    static final int INSERT_PHONE_NO=4;
    static final int INSERT_EMAIL=5;
    static final int INSERT_ADDRESS=6;
    static final int INSERT_AADHAR_NO=7;
    static final int INSERT_CUSTOMER_STATUS=8;

   String InsertSQL="Insert into customer(customer_name,date_of_birth,gender,phone_no,email,address,aadhar_no,customer_status)" +
           "VALUES(?,?,?,?,?,?,?,?)";

   String SelectAllSQL="Select * from customer";


  public void insert(Customer customer){

      try(Connection con= DbConnection.getConnect().getConnection();
         PreparedStatement ps=con.prepareStatement(InsertSQL)){

          ps.setString(INSERT_CUSTOMER_NAME,customer.getCustomerName());
          ps.setDate(INSERT_DATE_OF_BIRTH,customer.getDateOfBirth());
          ps.setString(INSERT_GENDER,customer.getGender());
          ps.setString(INSERT_PHONE_NO,customer.getPhoneNo());
          ps.setString(INSERT_EMAIL,customer.getEmail());
          ps.setString(INSERT_ADDRESS,customer.getAddress());
          ps.setString(INSERT_AADHAR_NO,customer.getAadharNo());
          ps.setString(INSERT_CUSTOMER_STATUS, customer.getCustomerStatus());

          int changedRows=ps.executeUpdate();

          if(changedRows==0){
              log.error("Insert failed,fields are not inserted in customer:{}",customer.getCustomerId());
          }

          else{
              log.info("sucessfully values are inserted in customer:{}",customer.getCustomerId());
          }

      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }
}
