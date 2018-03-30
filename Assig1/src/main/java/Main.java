import Repository.Account.AccountRepository;
import Repository.Account.AccountRepositoryMySQL;
import Repository.Activity.ActivityRepository;
import Repository.Activity.ActivityRepositoryMySQL;
import Repository.Client.ClientRepository;
import Repository.Client.ClientRepositoryMySQL;
import Repository.RightsRolesRepository;
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


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static database.Constants.Roles.ADMINISTRATOR;

public class Main {


    public static void main(String[] args) {

        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rep=new RightsRolesRepository(connection);
        UserRepositoryMySQL repo=new UserRepositoryMySQL(connection,rep);
        AccountRepository accountRepository=new AccountRepositoryMySQL(connection);
        ClientRepository clientRepository=new ClientRepositoryMySQL(connection,accountRepository);
        ActivityRepository activityRepository=new ActivityRepositoryMySQL(connection);
        Client client=new ClientBuilder().setName("bla").setIdCardNumber("blabla").setPersNumCode(12345l).setAddress("nimic").build();


       // Role customerRole = rep.findRoleByTitle(ADMINISTRATOR);
       // User user=new UserBuilder().setId(2l).setUsername("dt").setPassword("ceva").setRoles(Collections.singletonList(customerRole)).build();
       // repo.deleteByUsername("dt");

    }
}
