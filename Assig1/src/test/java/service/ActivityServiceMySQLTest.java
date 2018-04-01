package service;

import Repository.Account.AccountRepository;
import Repository.Account.AccountRepositoryMySQL;
import Repository.Activity.ActivityRepository;
import Repository.Activity.ActivityRepositoryMySQL;
import Repository.Client.ClientRepository;
import Repository.Client.ClientRepositoryMySQL;
import Repository.RightsRolesRepository;
import Repository.User.UserRepository;
import Repository.User.UserRepositoryMySQL;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Activity;
import model.Client;
import model.Role;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.*;

public class ActivityServiceMySQLTest {
    private static ActivityService activityService;
    private static ActivityRepository activityRepository;
    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepository userRepository;


    @BeforeClass
    public static void setupClass()
    {
        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        activityRepository=new ActivityRepositoryMySQL(connection);
        activityService=new ActivityServiceMySQL(activityRepository);
        accountRepository=new AccountRepositoryMySQL(connection);
        rightsRolesRepository=new RightsRolesRepository(connection);
        userRepository=new UserRepositoryMySQL(connection,rightsRolesRepository);
        clientRepository=new ClientRepositoryMySQL(connection,accountRepository);
    }

    @Before
    public void setUp() throws Exception {
        activityRepository.removeAll();
    }

    @Test
    public void addActivity() {
        String test_password="Abcde12#";
        String test_username="nimic@nimic.com";
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        userRepository.save(new UserBuilder().setUsername(test_username).setPassword(test_password).setRoles(Collections.singletonList(customerRole)).build());
        User user=userRepository.findByUsernameAndPassword(test_username,test_password).getResult();
        activityService.addActivity(user.getId(),"savings",Calendar.getInstance().getTime());
        activityService.addActivity(user.getId(),"savings",Calendar.getInstance().getTime());
        activityService.addActivity(user.getId(),"savings",Calendar.getInstance().getTime());
        activityService.addActivity(user.getId(),"savings",Calendar.getInstance().getTime());
       assertEquals( activityRepository.findActivitiesForEmployee(user.getId()).size(),4);






    }
}