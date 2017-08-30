package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.api.jdbc.Statement;

import dao.TransactionsDao;
import dto.Transaction;
import util.ConnectionUtil;

/**
 * Created by David Szilagyi on 2017. 07. 24..
 */
public class TransactionsController extends DatabaseController implements TransactionsDao {

    private final Logger LOG = LoggerFactory.getLogger(PermissionsController.class);

    public TransactionsController(ConnectionUtil.DatabaseName database) {
        super(database);
    }

    public Transaction getTransaction(int transactionId) {
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Transactions WHERE id = ?")) {
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new Transaction(rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("zip"),
                        rs.getString("city"),
                        rs.getString("address1"),
                        rs.getString("address2"),
                        rs.getString("phone"),
                        rs.getString("bought"),
                        rs.getString("boughtDate"));
            }
            LOG.info("getAllTransaction(int userId) is success");
        } catch (SQLException e) {
            LOG.error("getAllTransaction(int userId) is failed with Exception", e);
        }
        return null;
    }

    public List<Transaction> getAllTransaction(int userId) {
        List<Transaction> transactionList = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Transactions WHERE userId = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                transactionList.add(new Transaction(rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("zip"),
                        rs.getString("city"),
                        rs.getString("address1"),
                        rs.getString("address2"),
                        rs.getString("phone"),
                        rs.getString("bought"),
                        rs.getString("boughtDate")));
            }
            LOG.info("getAllTransaction(int userId) is success");
        } catch (SQLException e) {
            LOG.error("getAllTransaction(int userId) is failed with Exception", e);
        }
        return transactionList;
    }

    public List<Transaction> getAllTransaction() {
        List<Transaction> transactionList = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Transactions")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                transactionList.add(new Transaction(rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("zip"),
                        rs.getString("city"),
                        rs.getString("address1"),
                        rs.getString("address2"),
                        rs.getString("phone"),
                        rs.getString("bought"),
                        rs.getString("boughtDate")));
            }
            LOG.info("getAllTransaction() is success");
        } catch (SQLException e) {
            LOG.error("getAllTransaction() is failed with Exception", e);
        }
        return transactionList;
    }

    public int addTransaction(Transaction transaction) {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO Transactions" +
                "(userId, firstname, lastname, zip, city, address1, address2, phone, bought, boughtDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, transaction.getUserId());
            ps.setString(2, transaction.getFirstName());
            ps.setString(3, transaction.getLastName());
            ps.setString(4, transaction.getZip());
            ps.setString(5, transaction.getCity());
            ps.setString(6, transaction.getAddress1());
            ps.setString(7, transaction.getAddress2());
            ps.setString(8, transaction.getPhone());
            ps.setString(9, transaction.getBought());
            int success = ps.executeUpdate();
            if (success > 0){
                LOG.info("Add transaction to transactions is succeeded(userId: {}, bought {}, date: {})",transaction.getUserId(), transaction.getBought(), transaction.getBoughtDate());
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            LOG.error("Add transaction to transactions is failed with Exception",e);
        }
        LOG.debug("Add transaction to transactions is failed(userId: {}, bought {}, date: {})",transaction.getUserId(), transaction.getBought(), transaction.getBoughtDate());
        return -1;
    }
}
