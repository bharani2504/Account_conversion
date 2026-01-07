package org.bank.account.service;

import org.bank.account.dao.AccountDAO;
import org.bank.account.dao.NomineeDAO;
import org.bank.account.model.Account;
import org.bank.account.model.Nominee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class NomineeServiceTest {

    private NomineeService nomineeService;
    private NomineeDAO nomineeDAO;

    @BeforeEach
    void setup() throws Exception {

        nomineeService = new NomineeService();
        nomineeDAO=mock(NomineeDAO.class);


        Field nomineefield =NomineeService.class.getDeclaredField("nomineeDAO");
        nomineefield.setAccessible(true);
        nomineefield.set(nomineeService,nomineeDAO);
    }


    @Test
    void doGet() throws SQLException {

        List<Nominee> nomineeList = List.of(new Nominee());
        when(nomineeDAO.getNomineeByAccountId(1)).thenReturn(nomineeList);

        Object acc = nomineeService.getNomineeByAccountID(1);
        assertEquals(acc,nomineeList);
        verify(nomineeDAO).getNomineeByAccountId(1);
    }

}
