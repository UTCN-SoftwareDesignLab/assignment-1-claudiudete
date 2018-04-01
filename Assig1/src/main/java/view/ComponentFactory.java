package view;

import Repository.Account.AccountRepository;
import Repository.Account.AccountRepositoryMySQL;
import Repository.Activity.ActivityRepository;
import Repository.Activity.ActivityRepositoryMySQL;
import Repository.Client.ClientRepository;
import Repository.Client.ClientRepositoryMySQL;
import Repository.RightsRolesRepository;
import Repository.User.UserRepository;
import Repository.User.UserRepositoryMySQL;
import controller.AccountController;
import controller.AdministratorController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import service.*;

import java.sql.Connection;

public class ComponentFactory {

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;

    private final AdministratorOperationService administratorOperationService;
    private final EmployeeOperationService employeeOperationService;
    private final ActivityService activityService;
    private final AccountService accountService;

    private final AdministratorController administratorController;
    private final LoginController loginController;
    private final EmployeeController employeeController;
    private final  AccountController accountController;




    private static ComponentFactory instance;

    public static ComponentFactory instance() {
        if (instance == null) {
            instance = new ComponentFactory();
        }
        return instance;
    }

    public ComponentFactory() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(false).getConnection();
        this.rightsRolesRepository = new RightsRolesRepository(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.accountRepository=new AccountRepositoryMySQL(connection);
        this.activityRepository=new ActivityRepositoryMySQL(connection);
        this.clientRepository=new ClientRepositoryMySQL(connection,accountRepository);
        this.administratorOperationService=new AdministratorOperationServiceMySQL(userRepository,rightsRolesRepository);
        this.employeeOperationService=new EmployeeOperationServiceMySQL(clientRepository,activityRepository);
        this.activityService=new ActivityServiceMySQL(activityRepository);
        this.loginController=new LoginController(authenticationService);
        this.administratorController=new AdministratorController(administratorOperationService,activityService,loginController);
        this.employeeController=new EmployeeController(employeeOperationService,activityService,loginController);
        this.accountService=new AccountServiceMySQL(accountRepository,clientRepository);
        this.accountController=new AccountController(accountService,activityService,employeeOperationService,employeeController);


    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public AdministratorOperationService getAdministratorOperationService() {
        return administratorOperationService;
    }

    public EmployeeOperationService getEmployeeOperationService() {
         return employeeOperationService;
    }

    public ActivityService getActivityService()
    {
        return activityService;
    }

    public AdministratorController getAdministratorController() {
        return administratorController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AccountService getAccountService()
    {
        return this.accountService;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

     public AccountController getAccountController() {
        return accountController;
    }
}
