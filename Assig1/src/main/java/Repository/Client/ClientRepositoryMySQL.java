package Repository.Client;

import Repository.Account.AccountRepository;
import Repository.EntityNotFoundException;
import java.sql.*;

import model.Activity;
import model.Client;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static database.Constants.Tables.ACTIVITY;
import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository  {

    private final Connection connection;
    private final AccountRepository accountRepository;

    public ClientRepositoryMySQL(Connection connection,AccountRepository accountRepository)
    {

        this.connection=connection;
        this.accountRepository=accountRepository;
    }

    public Client findByID(Long id) throws EntityNotFoundException
    {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where id=" + id;
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return getClientFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }


        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }
    }



    private Client getClientFromResultSet(ResultSet rs) throws SQLException
    {
        return new ClientBuilder().setId(rs.getLong("id"))
                                  .setName(rs.getString("name"))
                                  .setIdCardNumber(rs.getString("idCardNumber"))
                                  .setPersNumCode(rs.getLong("persNumCode"))
                                  .setAddress(rs.getString("address"))
                                  .setAccounts(accountRepository.findAccountsForClient(rs.getLong("id")))
                                  .build();

    }

    public boolean save(Client client) {
        try {
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertClientStatement.setString(1, client.getName());
            insertClientStatement.setString(2, client.getIdCardNumber());
            insertClientStatement.setLong(3,client.getPersNumCode());
            insertClientStatement.setString(4,client.getAddress());
            insertClientStatement.executeUpdate();

            //ResultSet rs = insertClientStatement.getGeneratedKeys();
           // rs.next();
            //long userId = rs.getLong(1);
            //client.setId(userId);



            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateClient(Client client)
    {
        try
        {
            PreparedStatement updateStatement=connection
                    .prepareStatement("UPDATE client SET name=?, idCardNumber=?, persNumCode=?, address=? WHERE id=?");
            updateStatement.setString(1,client.getName());
            updateStatement.setString(2,client.getIdCardNumber());
            updateStatement.setLong(3,client.getPersNumCode());
            updateStatement.setString(4,client.getAddress());
            updateStatement.setLong(5,client.getId());
            updateStatement.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    public boolean deleteClient(Long client_id)
    {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM client WHERE id=?");
            deleteStatement.setLong(1,client_id);
            accountRepository.removeAccountsForClient(client_id);
            deleteStatement.executeUpdate();
            return true;

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public Client findByName(String name)
    {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + CLIENT + " where `name`=\'" + name + "\'";
            ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
            roleResultSet.next();
            Long id = roleResultSet.getLong("id");
            String cname = roleResultSet.getString("name");

            return new ClientBuilder().setId(id).setName(cname).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void removeAll()
    {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE from client WHERE id>=0");
            deleteStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }








}
