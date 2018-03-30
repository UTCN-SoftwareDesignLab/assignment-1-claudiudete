package Repository.User;

import Repository.RightsRolesRepository;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;
import static org.junit.Assert.*;

public class UserRepositoryMySQLTest {

    private static UserRepositoryMySQL userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setupClass() {
        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository=new RightsRolesRepository(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);

    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void findByUsernameAndPassword() {

        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        User user=new UserBuilder().setUsername("dete").setPassword("nimic").setRoles(Collections.singletonList(customerRole)).build();
        userRepository.save(user);
        Notification<User> not=userRepository.findByUsernameAndPassword("dete","nimic");
        assertTrue(!not.hasError());

    }

    @Test
    public void save(){
        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        User user=new UserBuilder().setUsername("dete").setPassword("nimic").setRoles(Collections.singletonList(customerRole)).build();
        assertTrue(userRepository.save(user));



    }

    @Test
    public void deleteById() {
        Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        User user=new UserBuilder().setId(3l).setUsername("nimic").setPassword("parola").setRoles(Collections.singletonList(customerRole)).build();
        userRepository.save(user);
        assertTrue(userRepository.deleteById(3l));




    }

    @Test
    public void updateUser() {
        Role adminRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        User user=new UserBuilder().setUsername("detesan").setPassword("nimicc").setRoles(Collections.singletonList(adminRole)).build();
        userRepository.save(user);
        Notification<User> not=userRepository.findByUsernameAndPassword("detesan","nimicc");
        User user1=not.getResult();

        User user2=new UserBuilder().setId(user1.getId()).setUsername("dt").setPassword("nimicc").build();
        assertTrue(userRepository.updateUser(user2));
        }


}