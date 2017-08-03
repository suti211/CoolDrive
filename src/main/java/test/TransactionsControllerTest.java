package test;

import controller.TransactionsController;
import controller.UserController;
import dto.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mudzso on 2017.08.03..
 */
public class TransactionsControllerTest {
    private static TestDbManager testDbManager = new TestDbManager(ConnectionUtil.DatabaseName.CoolDrive_Test);
    private static UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive_Test);
    private static TransactionsController transactionsController = new TransactionsController(ConnectionUtil.DatabaseName.CoolDrive_Test);

    @BeforeClass
    public static void setUp(){
        testDbManager.fillUsersTableWithDumbData(3);
        testDbManager.fillTransactionsTableWithDumbData(1,2,0);
        testDbManager.fillTransactionsTableWithDumbData(3,4,1);


    }
    @Test
    public void getTransaction() throws Exception {
        Transaction expected = new Transaction(
                1,
                0,
                "test1",
                "user1",
                "3525",
                "miskolc",
                "codecool1",
                "codecool2",
                "063098543",
                "100",
                "2017"
        );
        Transaction result = transactionsController.getTransaction(1);
        assertEquals(expected,result);

    }

    @Test
    public void getAllTransaction() throws Exception {
        List<Transaction> expected = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            expected.add(transactionsController.getTransaction(i));
        }


        List<Transaction> result = transactionsController.getAllTransaction();
        assertArrayEquals(expected.toArray(),result.toArray());
        assertEquals(true,result.containsAll(expected));

    }

    @Test
    public void getAllTransaction1() throws Exception {
        Transaction transaction;
        List<Transaction> expected = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            transaction = transactionsController.getTransaction(i);
            if (transaction.getUserId()==1)
            expected.add(transaction);
        }
        List<Transaction> result = transactionsController.getAllTransaction(1);
        assertArrayEquals(expected.toArray(),result.toArray());

    }

    @AfterClass
    public static void shutDown(){
        testDbManager.clearDataBase("Users");
        testDbManager.clearDataBase("Transactions");
    }

}