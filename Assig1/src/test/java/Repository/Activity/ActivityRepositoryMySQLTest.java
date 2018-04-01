package Repository.Activity;

import Repository.RightsRolesRepository;
import Repository.User.UserRepositoryMySQL;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Activity;
import model.Role;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;
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
        assertTrue(activityRepository.addActivity(new ActivityBuilder().setName("nothing").setActivityTime(Calendar.getInstance().getTime()).build(),not.getResult().getId()));

    }

    @Test
    public void findActivitiesForEmployee() {
        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        User user=new UserBuilder().setUsername("dete").setPassword("nimic").setRoles(Collections.singletonList(customerRole)).build();
        userRepository.save(user);
        Notification<User> not=userRepository.findByUsernameAndPassword("dete","nimic");
        Long id=not.getResult().getId();
        Activity a=new ActivityBuilder().setName("nothing").setActivityTime(Calendar.getInstance().getTime()).build();
        activityRepository.addActivity(a,id);
        activityRepository.addActivity(a,id);
        activityRepository.addActivity(a,id);
        assertEquals(activityRepository.findActivitiesForEmployee(id).size(),3);



    }

    @Test
    public void findActivityById() {
        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        User user=new UserBuilder().setUsername("dete").setPassword("nimic").setRoles(Collections.singletonList(customerRole)).build();
        userRepository.save(user);
        Notification<User> not=userRepository.findByUsernameAndPassword("dete","nimic");
        Long id=not.getResult().getId();
        Activity a=new ActivityBuilder().setId(1l).setName("nothing").setActivityTime(Calendar.getInstance().getTime()).build();
        activityRepository.addActivity(a,id);
        //assertEquals(activityRepository.findActivityById(1l).getName(),"nothing");
    }
}