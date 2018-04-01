package service;

import Repository.RightsRolesRepository;
import Repository.User.UserRepository;
import Repository.User.UserRepositoryMySQL;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;
import static org.junit.Assert.*;

public class AdministratorOperationServiceMySQLTest {

    private static AdministratorOperationService administratorOperationService;
    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setupClass() throws Exception {
        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository=new RightsRolesRepository(connection);
        userRepository=new UserRepositoryMySQL(connection,rightsRolesRepository);
        administratorOperationService=new AdministratorOperationServiceMySQL(userRepository, rightsRolesRepository);

    }
    @Before
    public void cleanUp()
    {
        userRepository.removeAll();
    }

    @Test
    public void createUser() {
        assertTrue(administratorOperationService.createUser("administrator","nimiccc@nimic.com","Abcde1234$").getResult());
    }

    @Test
    public void updateUser() {
        String username="nimiccc@nimi.com";
        String password="blabla123#";
        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        userRepository.save(new UserBuilder().setPassword(password).setUsername(username).setRoles(Collections.singletonList(customerRole)).build());
        User u=userRepository.findByUsernameAndPassword(username,password).getResult();

        assertTrue(administratorOperationService.updateUser(u.getId(),u.getUsername(),u.getPassword()).getResult());
    }

    @Test
    public void deleteUser() {

        String username="nimiccc@nimi.com";
        String password="blabla123#";
        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        userRepository.save(new UserBuilder().setPassword(password).setUsername(username).setRoles(Collections.singletonList(customerRole)).build());
        User u=userRepository.findByUsernameAndPassword(username,password).getResult();
        assertTrue(administratorOperationService.deleteUser(u.getId()));

    }
}