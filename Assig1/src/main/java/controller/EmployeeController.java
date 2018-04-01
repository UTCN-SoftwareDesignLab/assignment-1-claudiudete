package controller;

import Repository.Activity.ActivityRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.validation.Notification;
import service.ActivityService;
import service.AdministratorOperationService;
import service.EmployeeOperationService;
import view.ComponentFactory;

import java.io.IOException;
import java.util.Calendar;

public class EmployeeController {

    private EmployeeOperationService employeeOperationService;
    private ActivityService activityService;
    private long id;


    public EmployeeController(EmployeeOperationService employeeOperationService,Long id,ActivityService activityService)
    {
        this.employeeOperationService=employeeOperationService;
        this.id=id;
        this.activityService=activityService;

    }

    public long getId()
    {
        return this.id;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField idCardNumberText;

    @FXML
    private TextField numericalCodeText;

    @FXML
    private TextField addressText;

    @FXML
    private Button createBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button logoffBtn;

    @FXML
    private Button accountBtn;

    private void showAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();

    }

    public void createClient() {
        try {
            String name = nameText.getText();
            String idCardNumber = idCardNumberText.getText();
            Long persNumCode = Long.parseLong(numericalCodeText.getText());
            String address = addressText.getText();
            Notification<Boolean> registerNotification = employeeOperationService.saveClient(name, idCardNumber, persNumCode, address);

            if (registerNotification.hasError()) {

                showAlert(registerNotification.getFormattedErrors());

            } else {
                if (!registerNotification.getResult()) {

                    showAlert("Creation unsuccessful!");

                } else {
                    activityService.addActivity(this.id,"createclient", Calendar.getInstance().getTime());
                    showAlert("Successful creation!");



                }

            }
        }
        catch(NumberFormatException e)
        {
            showAlert("Personal numeric code should be a number!");
        }
    }


    public void updateClient() {
        try {

            Long id = Long.parseLong(idText.getText());
            String name = nameText.getText();
            String idCardNumber = idCardNumberText.getText();
            Long persNumCode = Long.parseLong(numericalCodeText.getText());
            String address = addressText.getText();
            Notification<Boolean> registerNotification = employeeOperationService.updateClient(id, name, idCardNumber, persNumCode, address);

            if (registerNotification.hasError()) {

                showAlert(registerNotification.getFormattedErrors());

            } else {
                if (!registerNotification.getResult()) {

                    showAlert("Update unsuccessful!");
                } else {
                    activityService.addActivity(this.id,"update_client", Calendar.getInstance().getTime());
                    showAlert("Successful update!");

                }

            }
        }
        catch(NumberFormatException e)
        {
            showAlert("Id and personal numerical code must be numbers!");
        }

    }

    public void removeClient()
    {
        try {
            Long id = Long.parseLong(idText.getText());
            employeeOperationService.deleteClient(id);
            showAlert("Deleted client if exists!");
            activityService.addActivity(this.id,"delete_client", Calendar.getInstance().getTime());
        }
        catch(NumberFormatException e)
        {
            showAlert("Id must be a number!");
        }
    }

    public void logoff() throws IOException
    {
        ComponentFactory componentFactory = ComponentFactory.instance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
        loader.setController(componentFactory.getLoginController());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window =(Stage)logoffBtn.getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void goToAccounts() throws IOException
    {

        ComponentFactory componentFactory=ComponentFactory.instance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActivityView.fxml"));
        AccountController accountController=new AccountController(componentFactory.getAccountService(),id,componentFactory.getActivityService(),componentFactory.getEmployeeOperationService());
        loader.setController(accountController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window =(Stage)accountBtn.getScene().getWindow();
        window.setScene(scene);
        window.show();
    }








}
