package Repository.Activity;

import Repository.RightsRolesRepository;
import Repository.User.UserRepositoryMySQL;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Activity;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;
import static org.junit.Assert.*;

public class ActivityRepositoryMySQLTest {

    private static  ActivityRepositoryMySQL activityRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepositoryMySQL userRepository;

    @BeforeClass
    public static void setupClass() {
        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        activityRepository=new ActivityRepositoryMySQL(connection);
        rightsRolesRepository=new RightsRolesRepository(connection);
        userRepository=new UserRepositoryMySQL(connection,rightsRolesRepository);



    }
    @Before
    public void cleanUp() {
        activityRepository.removeAll();
        userRepository.removeAll();
    }

    @Test
    public void addActivity() {
        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        User user=new UserBuilder().setUsername("dete").setPassword("nimic").setRoles(Collections.singletonList(customerRole)).build();
        userRepository.save(user);
        Notification<User> not=userRepository.findByUsernameAndPassword("dete","nimic");
        assertTrue( activityRepository.addActivity(new Activity(1l,"transaction",new Date(2018,11,30)),not.getResult().getId()));


    }

    @Test
    public void findActivitiesForEmployee() {

    }

    @Test
    public void findActivityById() {
       // Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
      //  User user=new UserBuilder().setUsername("dete").setPassword("nimic").setRoles(Collections.singletonList(customerRole)).build();
       // userRepository.save(user);
      //  Notification<User> not=userRepository.findByUsernameAndPassword("dete","nimic");
      //  activityRepository.addActivity(new Activity(1l,"transaction",new Date(2018,11,30)),not.getResult().getId());
       // Activity act=activityRepository.findActivityById(1l);
      //  assertEquals(act.getId(),1l);
    }
}