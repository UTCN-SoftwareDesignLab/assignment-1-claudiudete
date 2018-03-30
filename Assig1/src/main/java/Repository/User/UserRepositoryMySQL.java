package Repository.User;

import Repository.RightsRolesRepository;
import Repository.User.UserRepository;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;

import java.sql.*;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }


    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();

        try {
            Statement statement = connection.createStatement();

            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            User user = new UserBuilder()
                    .setId(userResultSet.getLong("id"))
                    .setUsername(userResultSet.getString("username"))
                    .setPassword(userResultSet.getString("password"))
                    .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                    .build();
            findByUsernameAndPasswordNotification.setResult(user);
            return findByUsernameAndPasswordNotification;
        } catch (SQLException e) {

            findByUsernameAndPasswordNotification.addError("Invalid email or password!");
            return findByUsernameAndPasswordNotification;
        }
    }

   //schimb aici cu constanta
    public  boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)");
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Long id)
    {
        try
        {
            rightsRolesRepository.removeRolesForUser(id);

            PreparedStatement st = connection.prepareStatement("DELETE FROM user WHERE id= ?");
            st.setLong(1,id);
            st.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }

    }

    public boolean updateUser(User user)
    {
        try
        {
            PreparedStatement updateStatement=connection
                    .prepareStatement("UPDATE user SET username=?, password=? WHERE id=?");
            updateStatement.setString(1,user.getUsername());
            updateStatement.setString(2,user.getPassword());
            updateStatement.setLong(3,user.getId());
            updateStatement.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
          return false;
        }
    }

    public void removeAll()
    {
        rightsRolesRepository.removeAllUserRoles();
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
