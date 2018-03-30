package Repository.Activity;

import java.sql.*;
import model.Activity;
import model.builder.ActivityBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACTIVITY;
import static database.Constants.Tables.RIGHT;

public class ActivityRepositoryMySQL implements ActivityRepository {

    private final Connection connection;

    public ActivityRepositoryMySQL(Connection connection)
    {
        this.connection=connection;
    }

    public boolean addActivity(Activity activity,Long user_id)
    {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `" + ACTIVITY + "` values (null, ?, ?, ?)");
            insertStatement.setLong(1, user_id);
            insertStatement.setString(2,activity.getName());
            insertStatement.setDate(3,new java.sql.Date(activity.getActivityTime().getTime()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }

    public List<Activity> findActivitiesForEmployee(Long user_id)
    {
        try
        {
            List<Activity> activities = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACTIVITY + " where `emp_id`=\'" + user_id + "\'";
            ResultSet activityResultSet = statement.executeQuery(fetchRoleSql);
            while (activityResultSet.next()) {
                long actId =activityResultSet.getLong("id");
                activities.add(findActivityById(actId));
            }
            return activities;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Activity findActivityById(Long id)
    {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACTIVITY + " where `id`=\'" + id + "\'";
            ResultSet actResultSet = statement.executeQuery(fetchRoleSql);
            actResultSet.next();
            String actName = actResultSet.getString("name");
            Date creationDate=actResultSet.getDate("activityTime");
            return new ActivityBuilder().setId(id).setName(actName).setActivityTime(creationDate).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void removeAll()
    {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from activity where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
