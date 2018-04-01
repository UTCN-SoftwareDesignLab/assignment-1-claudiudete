package controller;

import Repository.RightsRolesRepository;
import Repository.User.UserRepositoryMySQL;
import com.mysql.jdbc.Connection;
import database.DBConnectionFactory;
import javafx.fxml.FXML;
import view.ComponentFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.validation.Notification;
import service.AuthenticationService;
import service.AuthenticationServiceMySQL;



public class LoginController {

    private AuthenticationService authenticationService;





    public LoginController(AuthenticationService authenticationService)
    {
        this.authenticationService=authenticationService;
    }





    @FXML
    private TextField usernameText;

    @FXML
    private TextField passwordText;

    @FXML
    private Button loginBtn;




    public void login()
    {
        try {
            String username = usernameText.getText();
            String password = passwordText.getText();
            Notification<User> loginNotification = authenticationService.login(username, password);
            if (loginNotification.hasError()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText(loginNotification.getFormattedErrors());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Login successful");
                alert.showAndWait();

                if(loginNotification.getResult().getRoles().get(0).getRole().equals("administrator")) {



                    ComponentFactory c=ComponentFactory.instance();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdministratorView.fxml"));
                    loader.setController(c.getAdministratorController());
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage window = (Stage)usernameText.getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                }
                else
                {
                    ComponentFactory componentFactory = ComponentFactory.instance();
                    EmployeeController employeeController=new EmployeeController(componentFactory.getEmployeeOperationService(),
                            loginNotification.getResult().getId(),componentFactory.getActivityService());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeView.fxml"));
                    loader.setController(employeeController);
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage window =(Stage)usernameText.getScene().getWindow();
                    window.setScene(scene);
                    window.show();

                }

            }
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Login unsuccessful");
            alert.showAndWait();
        }




    }





}
