package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.validation.Notification;
import service.ActivityService;
import service.AdministratorOperationService;
import service.AuthenticationService;
import view.ComponentFactory;

import java.io.IOException;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class AdministratorController {

    private AdministratorOperationService administratorOperationService;
    private ActivityService activityService;
    private LoginController loginController;


    public AdministratorController(AdministratorOperationService administratorOperationService,ActivityService activityService,LoginController loginController)
    {
        this.administratorOperationService=administratorOperationService;
        this.activityService=activityService;
        this.loginController=loginController;

    }
     @FXML
     private TextField idText;

     @FXML
    private TextField usernameText;

     @FXML
    private TextField passwordText;

     @FXML
     private RadioButton adminRadio;

     @FXML
     private RadioButton empRadio;

     @FXML
     private Button logOffBtn;

     @FXML
     private ToggleGroup role;

     private void showAlert(String s)
     {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setHeaderText(null);
         alert.setContentText(s);
         alert.showAndWait();

     }
     @FXML
     public void createUser()
     {
       try {


           String username = usernameText.getText();
           String password = passwordText.getText();
           String role;

           if (adminRadio.isSelected()) role = ADMINISTRATOR;
           else role = EMPLOYEE;

           Notification<Boolean> registerNotification = administratorOperationService.createUser(role, username, password);
           if (registerNotification.hasError()) {

               showAlert(registerNotification.getFormattedErrors());

           } else {
               if (!registerNotification.getResult()) {

                   showAlert("Creation unsuccessful!");
               } else {
                   showAlert("Successful creation!");

               }
           }
       }
       catch(Exception e)
         {

         }
     }
      @FXML
     public void deleteUser()
     {
         try
         {
             Long id=Long.parseLong(idText.getText());
            boolean result= administratorOperationService.deleteUser(id);

            if(result)
            {
                showAlert("Deleted user!");
            }
            else
            {
                showAlert("User does not exist!");
            }

         }
         catch(NumberFormatException e)
         {
            showAlert("Id must be a number!");
         }
     }

     @FXML

    public void updateUser()
     {
         try {

             Long id = Long.parseLong(idText.getText());
             String username = usernameText.getText();
             String password = passwordText.getText();
             Notification<Boolean> updateNotification = administratorOperationService.updateUser(id,username,password);
             if (updateNotification.hasError()) {

                 showAlert(updateNotification.getFormattedErrors());

             } else {
                 if (updateNotification.getResult()) {

                     showAlert("Update unsuccessful");
                 } else {
                     showAlert("Update successful");

                 }
             }


         }
         catch(NumberFormatException e)
         {
             showAlert("Id must be a number");
         }


     }

     public void generateReport()
     {
         try {
             Long emp_id = Long.parseLong(idText.getText());
             activityService.generateReport(emp_id);
             showAlert("Generater report!");
         }
         catch(NumberFormatException e)
         {
             showAlert("Id is a number!");
         }
     }

     public void logoff() throws IOException {


         FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
         loader.setController(this.loginController);
         Parent root = loader.load();
         Scene scene = new Scene(root);
         Stage window =(Stage)logOffBtn.getScene().getWindow();
         window.setScene(scene);
         window.show();
     }
}
