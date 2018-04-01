package Repository.Account;

import java.sql.*;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static database.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;


    public AccountRepositoryMySQL(Connection connection)
    {
        this.connection=connection;
    }

    public boolean addAccount(Account account,Long client_id)
    {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?)");
            insertStatement.setLong(1, client_id);
            insertStatement.setLong(2,account.getSum());
            insertStatement.setString(3, account.getType());
            insertStatement.setDate(4, new java.sql.Date(account.getCreationDate().getTime()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAccount(Long id)
    {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM account WHERE id=?");
            deleteStatement.setLong(1,id);
            deleteStatement.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }



    public List<Account> findAccountsForClient(Long clientId) {
        try {
            List<Account> accounts = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from " +ACCOUNT+ " where `client_id`=\'" + clientId + "\'";
            ResultSet accountsRS = statement.executeQuery(fetchAccountSql);
            while (accountsRS.next()) {
                long accountId = accountsRS.getLong("id");
                accounts.add(findAccountById(accountId));
            }
            return accounts;
        } catch (SQLException e) {

        }
        return null;
    }



    public void removeAccountsForClient(Long client_id)
    {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM account WHERE client_id=?");
            deleteStatement.setLong(1,client_id);
            deleteStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

    //de schimbat cu builderu daca nu avem nevoie de tot
    public Account findAccountById(Long accId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchAccSql = "Select * from " + ACCOUNT + " where `id`=\'" + accId + "\'";
            ResultSet accResultSet = statement.executeQuery(fetchAccSql);
            accResultSet.next();
            String accType = accResultSet.getString("type");
            Long   sum=accResultSet.getLong("sum");
            Date date =accResultSet.getDate("creationDate");

            return new AccountBuilder().setId(accId).setSum(sum).setType(accType).setCreationDate(date).build();
        } catch (SQLException e) {
           // e.printStackTrace();
            return null;
        }


    }

    public boolean changeAccountSum(Long sum,Long id)
    {
        try{
            PreparedStatement changeStatement=connection.prepareStatement("UPDATE account SET sum=? WHERE id=?");
            changeStatement.setLong(1,sum);
            changeStatement.setLong(2,id);
            changeStatement.executeUpdate();
            return true;

        }

        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void removeAll()
    {
        try {
            PreparedStatement removeStatement = connection.prepareStatement("DELETE FROM account WHERE id>=0");
            removeStatement.executeUpdate();
        }
        catch(SQLException e)
        {

        }
    }






}
