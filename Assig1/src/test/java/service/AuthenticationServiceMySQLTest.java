package service;

import Repository.RightsRolesRepository;
import Repository.User.UserRepository;
import Repository.User.UserRepositoryMySQL;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;
import static org.junit.Assert.*;

public class AuthenticationServiceMySQLTest {

    private static AuthenticationService authenticationService;
    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setupClass() throws Exception {
        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository=new RightsRolesRepository(connection);
        userRepository=new UserRepositoryMySQL(connection,rightsRolesRepository);
        authenticationService=new AuthenticationServiceMySQL(userRepository,rightsRolesRepository);

    }

    @Test
    public void login() {
       // String test_password="Abcde12#";
        //String test_username="nimic@nimic.com";
        //Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
       // User u=new UserBuilder().setPassword(test_password).setUsername(test_username).setRoles(Collections.singletonList(customerRole)).build();
        //userRepository.save(u);
        //assertNotNull(authenticationService.login(test_username,test_password).getResult());


    }
}