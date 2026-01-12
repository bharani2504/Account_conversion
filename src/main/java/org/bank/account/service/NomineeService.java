package org.bank.account.service;

import org.bank.account.dao.NomineeDAO;
import org.bank.account.model.Nominee;

import java.sql.SQLException;
import java.util.List;

public class NomineeService {
    NomineeDAO nomineeDAO=new NomineeDAO();

    public static void insert(Nominee nominee){
        NomineeDAO.insert(nominee);;
    }
    public List<Nominee> getNomineeByAccountID(long accountId) throws SQLException {
        return nomineeDAO.getNomineeByAccountId(accountId);
    }

    public void delete(long accountId) {
        nomineeDAO.delete(accountId);
    }
}
