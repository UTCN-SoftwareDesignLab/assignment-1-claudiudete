package service;

import Repository.Account.AccountRepository;
import Repository.Account.AccountRepositoryMySQL;
import Repository.Client.ClientRepository;
import Repository.Client.ClientRepositoryMySQL;
import Repository.RightsRolesRepository;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.Right;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccountServiceMySQLTest {

    private static RightsRolesRepository rightsRolesRepository;
    private static AccountService accountService;
    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass()
    {
        Connection connection= (Connection) new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository=new RightsRolesRepository(connection);
        accountRepository =new AccountRepositoryMySQL(connection);
        clientRepository=new ClientRepositoryMySQL(connection,accountRepository);
        accountService=new AccountServiceMySQL(accountRepository,clientRepository);
    }

    @Before
    public void setUp() throws Exception {
        accountRepository.removeAll();
    }

    @Test
    public void addAccountToClient() {
        String name="nimic";
        String address="blabla";
        String idCardNumber="123Ab";
        Long persNumCode=1234567890l;
        Client client=new ClientBuilder().setName(name).setAddress(address).setPersNumCode(persNumCode).setIdCardNumber(idCardNumber).build();
        clientRepository.save(client);
        Client cl=clientRepository.findByName(name);
        Long sum=1000l;
        String type="savings";
        assertTrue(accountService.addAccountToClient(cl.getId(),sum,type));

    }

    @Test
    public void removeAccount() {
        String name="nimic";
        String address="blabla";
        String idCardNumber="123Ab";
        Long persNumCode=1234567890l;
        Client client=new ClientBuilder().setName(name).setAddress(address).setPersNumCode(persNumCode).setIdCardNumber(idCardNumber).build();
        clientRepository.save(client);
        Client cl=clientRepository.findByName(name);
        Long sum=1000l;
        String type="savings";
        accountService.addAccountToClient(cl.getId(),sum,type);
        List<Account> accounts=accountRepository.findAccountsForClient(cl.getId());
        assertTrue(accountService.removeAccount(accounts.get(0).getId()));

    }

    @Test
    public void transferBetweenAccounts() {
        String name="nimic";
        String address="blabla";
        String idCardNumber="123Ab";
        Long persNumCode=1234567890l;
        Client client=new ClientBuilder().setName(name).setAddress(address).setPersNumCode(persNumCode).setIdCardNumber(idCardNumber).build();
        clientRepository.save(client);
        Client cl=clientRepository.findByName(name);
        Long sum=1000l;
        String type="savings";
        accountService.addAccountToClient(cl.getId(),sum,type);
        accountService.addAccountToClient(cl.getId(),sum,type);
        List<Account> accounts=accountRepository.findAccountsForClient(cl.getId());
        accountService.transferBetweenAccounts(accounts.get(0).getId(),accounts.get(1).getId(),1000l);
        accounts=accountRepository.findAccountsForClient(cl.getId());
        assertEquals(accounts.get(0).getSum(),0l);


    }

    @Test
    public void payBill() {
        String name="nimic";
        String address="blabla";
        String idCardNumber="123Ab";
        Long persNumCode=1234567890l;
        Client client=new ClientBuilder().setName(name).setAddress(address).setPersNumCode(persNumCode).setIdCardNumber(idCardNumber).build();
        clientRepository.save(client);
        Client cl=clientRepository.findByName(name);
        Long sum=1000l;
        String type="savings";
        accountService.addAccountToClient(cl.getId(),sum,type);
        accountService.addAccountToClient(cl.getId(),sum,type);
        List<Account> accounts=accountRepository.findAccountsForClient(cl.getId());
        accountService.payBill(accounts.get(0).getId(),1000l);
        accounts=accountRepository.findAccountsForClient(cl.getId());
        assertEquals(accounts.get(0).getSum(),0l);



    }

    @Test
    public void updateAccount() {
        String name="nimic";
        String address="blabla";
        String idCardNumber="123Ab";
        Long persNumCode=1234567890l;
        Client client=new ClientBuilder().setName(name).setAddress(address).setPersNumCode(persNumCode).setIdCardNumber(idCardNumber).build();
        clientRepository.save(client);
        Client cl=clientRepository.findByName(name);
        Long sum=1000l;
        String type="savings";
        accountService.addAccountToClient(cl.getId(),sum,type);
        List<Account> accounts=accountRepository.findAccountsForClient(cl.getId());
        accountService.updateAccount(accounts.get(0).getId(),3000l);
        accounts=accountRepository.findAccountsForClient(cl.getId());
        assertEquals(accounts.get(0).getSum(),3000l);



    }
}