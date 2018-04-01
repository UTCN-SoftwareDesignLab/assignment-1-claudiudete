package Repository.Account;

import Repository.Client.ClientRepository;
import Repository.Client.ClientRepositoryMySQL;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class AccountRepositoryMySQLTest {

     private static AccountRepository accountRepository;
     private static ClientRepository clientRepository;


     @BeforeClass
    public static void setupClass() throws Exception {

        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository=new AccountRepositoryMySQL(connection);
        clientRepository=new ClientRepositoryMySQL(connection,accountRepository);
    }

    @Before
    public void cleanUp()
    {
        accountRepository.removeAll();
    }

    @Test
    public void addAccount() {

         Client c=new ClientBuilder().setName("aaa").setAddress("marasti").setIdCardNumber("123BC").setPersNumCode(1234567890l).setId(1l).build();
         clientRepository.save(c);
         Client cl=clientRepository.findByName("aaa");
         Account account=new AccountBuilder().setSum(2000l).setType("spendings").setCreationDate(Calendar.getInstance().getTime()).build();
         assertTrue(accountRepository.addAccount(account,cl.getId()));

    }

    @Test
    public void removeAccount() {

        Client c=new ClientBuilder().setName("aaa").setAddress("marasti").setIdCardNumber("123BC").setPersNumCode(1234567890l).setId(1l).build();
        clientRepository.save(c);
        Client cl=clientRepository.findByName("aaa");
        Account account=new AccountBuilder().setSum(2000l).setType("spendings").setCreationDate(Calendar.getInstance().getTime()).build();
        accountRepository.addAccount(account,cl.getId());
        assertTrue(accountRepository.removeAccount(1l));
    }

    @Test
    public void findAccountsForClient() {
        Client c=new ClientBuilder().setName("aaa").setAddress("marasti").setIdCardNumber("123BC").setPersNumCode(1234567890l).setId(1l).build();
        clientRepository.save(c);
        Client cl=clientRepository.findByName("aaa");
        Account account=new AccountBuilder().setSum(2000l).setType("spendings").setCreationDate(Calendar.getInstance().getTime()).build();
        accountRepository.addAccount(account,cl.getId());
        accountRepository.addAccount(account,cl.getId());
        accountRepository.addAccount(account,cl.getId());
        assertEquals(accountRepository.findAccountsForClient(cl.getId()).size(),3);
    }

    @Test
    public void removeAccountsForClient() {

        Client c=new ClientBuilder().setName("aaa").setAddress("marasti").setIdCardNumber("123BC").setPersNumCode(1234567890l).setId(1l).build();
        clientRepository.save(c);
        Client cl=clientRepository.findByName("aaa");
        Account account=new AccountBuilder().setSum(2000l).setType("spendings").setCreationDate(Calendar.getInstance().getTime()).build();
        accountRepository.addAccount(account,cl.getId());
        accountRepository.addAccount(account,cl.getId());
        accountRepository.addAccount(account,cl.getId());
        accountRepository.removeAccountsForClient(cl.getId());
        assertEquals(accountRepository.findAccountsForClient(cl.getId()).size(),0);
    }

    @Test
    public void findAccountById() {
        Client c=new ClientBuilder().setName("aaa").setAddress("marasti").setIdCardNumber("123BC").setPersNumCode(1234567890l).setId(1l).build();
        clientRepository.save(c);
        Client cl=clientRepository.findByName("aaa");
        Account account=new AccountBuilder().setSum(2000l).setType("spendings").setCreationDate(Calendar.getInstance().getTime()).build();
        accountRepository.addAccount(account,cl.getId());


    }

    @Test
    public void changeAccountSum() {

        Client c=new ClientBuilder().setName("aaa").setAddress("marasti").setIdCardNumber("123BC").setPersNumCode(1234567890l).setId(1l).build();
        clientRepository.save(c);
        Client cl=clientRepository.findByName("aaa");
        Account account=new AccountBuilder().setSum(2000l).setType("spendings").setCreationDate(Calendar.getInstance().getTime()).build();
        accountRepository.addAccount(account,cl.getId());
        List<Account> accounts=accountRepository.findAccountsForClient(cl.getId());
        Long id=accounts.get(0).getId();
        accountRepository.changeAccountSum(1000l,id);
        assertEquals(1000l,accountRepository.findAccountById(id).getSum());
    }
}