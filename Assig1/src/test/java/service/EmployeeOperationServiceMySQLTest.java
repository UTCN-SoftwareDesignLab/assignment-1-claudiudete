package service;

import Repository.Account.AccountRepository;
import Repository.Account.AccountRepositoryMySQL;
import Repository.Activity.ActivityRepository;
import Repository.Client.ClientRepository;
import Repository.Client.ClientRepositoryMySQL;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeOperationServiceMySQLTest {

    private static EmployeeOperationService employeeOperationService;
    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;
    private static ActivityRepository activityRepository;


    @BeforeClass
    public static void setupClass() throws Exception {
        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository=new AccountRepositoryMySQL(connection);
        clientRepository=new ClientRepositoryMySQL(connection,accountRepository);
        accountRepository=new AccountRepositoryMySQL(connection);
        employeeOperationService=new EmployeeOperationServiceMySQL(clientRepository,activityRepository);
    }

    @Before
    public void cleanUp()
    {
        clientRepository.removeAll();
    }

    @Test
    public void saveClient()
    {
        String name="nimic";
        String address="blabla";
        String idCardNumber="123Ab";
        Long persNumCode=1234567890l;


        assertTrue(employeeOperationService.saveClient(name,idCardNumber,persNumCode,address).getResult());

    }
    @Test
    public void deleteClient()
    {
        String name="nimic";
        String address="blabla";
        String idCardNumber="123Ab";
        Long persNumCode=1234567890l;
        Client u=new ClientBuilder().setName(name).setAddress(address).setPersNumCode(persNumCode).setIdCardNumber(idCardNumber).build();
        clientRepository.save(u);
        Client clinet=clientRepository.findByName(name);
        assertTrue( employeeOperationService.deleteClient(clinet.getId()));

    }
    @Test
    public void updateClient()
    {
        String name="nimic";
        String address="blabla";
        String idCardNumber="123Ab";
        Long persNumCode=1234567890l;
        Client u=new ClientBuilder().setName(name).setAddress(address).setPersNumCode(persNumCode).setIdCardNumber(idCardNumber).build();
        clientRepository.save(u);
        Client clinet=clientRepository.findByName(name);
        assertTrue(employeeOperationService.updateClient(clinet.getId(),name,idCardNumber,persNumCode,address).getResult());
    }
}