package Repository.Client;

import Repository.Account.AccountRepository;
import Repository.Account.AccountRepositoryMySQL;
import Repository.EntityNotFoundException;
import Repository.RightsRolesRepository;
import Repository.User.UserRepositoryMySQL;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass() {
        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository=new AccountRepositoryMySQL(connection);
        clientRepository=new ClientRepositoryMySQL(connection,accountRepository);

    }

    @Before
    public void cleanUp()
    {
        clientRepository.removeAll();
    }
    @Test
    public void findByID() throws EntityNotFoundException {

        Client c=new ClientBuilder().setPersNumCode(1234567890l).setIdCardNumber("bla").setName("nimic").setAddress("aaa").build();
        clientRepository.save(c);
        Client cl=clientRepository.findByName("nimic");
        assertTrue(clientRepository.findByID(cl.getId())!=null);

    }

    @Test
    public void save() {
        Client c=new ClientBuilder().setPersNumCode(1234567890l).setIdCardNumber("bla").setName("nimic").setAddress("aaa").build();
        assertTrue(clientRepository.save(c));



    }

    @Test
    public void updateClient() {

        Client c=new ClientBuilder().setPersNumCode(1234567890l).setIdCardNumber("bla").setName("nimic").setAddress("aaa").build();
        clientRepository.save(c);
        Client cl=clientRepository.findByName("nimic");

        clientRepository.updateClient(cl);
        assertTrue(clientRepository.findByName("nimic")!=null);
    }

    @Test
    public void deleteClient() {
        Client c=new ClientBuilder().setPersNumCode(1234567890l).setIdCardNumber("bla").setName("nimic").setAddress("aaa").build();
        clientRepository.save(c);
        Client cl=clientRepository.findByName("nimic");
        Long id=cl.getId();
        assertTrue(clientRepository.deleteClient(id));
    }

    @Test
    public void findByName() {
        Client c=new ClientBuilder().setPersNumCode(1234567890l).setIdCardNumber("bla").setName("nimic").setAddress("aaa").build();
        clientRepository.save(c);
        Client cl=clientRepository.findByName("nimic");
        assertTrue(cl!=null);

    }
}