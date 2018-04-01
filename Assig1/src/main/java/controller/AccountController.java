package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Activity;
import service.AccountService;
import service.ActivityService;
import service.EmployeeOperationService;
import view.ComponentFactory;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.Calendar;


public class AccountController {

    private AccountService accountService;
    private long id;
    private ActivityService activityService;
    private EmployeeOperationService employeeOperationService;
    private EmployeeController employeeController;

    public AccountController(AccountService accountService,ActivityService activityService,EmployeeOperationService employeeOperationService,EmployeeController employeeController) {
        this.accountService = accountService;

        this.activityService=activityService;
        this.employeeOperationService=employeeOperationService;
        this.employeeController=employeeController;
    }
    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id=id;
    }

    private void showAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();

    }

    @FXML
    private TextField idText;

    @FXML
    private TextField sumText;

    @FXML
    private RadioButton spendingsRadio;

    @FXML
    private RadioButton savingsRadio;

    @FXML
    private TextField fromText;

    @FXML
    private TextField toText;

    @FXML
    private Button backBtn;

    public void createAccount()
    {
        try {
            Long id = Long.parseLong(idText.getText());
            Long sum = Long.parseLong(sumText.getText());
            String type;
            if (spendingsRadio.isSelected()) type = "spendings";
            else type = "savings";

            if (sum<0) showAlert("Sum must be greater than 0");
            boolean a=accountService.addAccountToClient(id, sum, type);
            if(!a) showAlert("Client id does not exist");
            else {
                showAlert("Account created");
                activityService.addActivity(this.id,"created_account_for_id:"+id, Calendar.getInstance().getTime());
            }
        }
        catch(NumberFormatException e)
        {
            showAlert("Id and sum must be numbers");
        }




    }

    public void deleteAccount()
    {
        try {
            Long id = Long.parseLong(idText.getText());
            if (accountService.removeAccount(id))
            {
                showAlert("Account has been deleted!");
                activityService.addActivity(this.id,"deleted_account_id:"+id,Calendar.getInstance().getTime());
            }
        }
        catch(NumberFormatException e)
        {
            showAlert("Failed!");
        }
    }

    public void updateAccount()
    {
        try {
            Long id = Long.parseLong(idText.getText());
            Long sum = Long.parseLong(sumText.getText());
            if(sum<0) showAlert("Sum must be a positive number!");
            else
            {
               boolean a= accountService.updateAccount(id,sum);
               if(a)
               {
                   showAlert("Account updated!");
                   activityService.addActivity(this.id,"updated_account_id:"+id,Calendar.getInstance().getTime());
               }
               else showAlert("Account does not exist!");
            }
        }
        catch(NumberFormatException e)
        {
            showAlert("Sum must be a number");
        }
    }

    public void transferBetweenAccounts()
    {
        try {
            Long id1 = Long.parseLong(fromText.getText());
            Long id2 = Long.parseLong(toText.getText());
            Long sum = Long.parseLong(sumText.getText());
            if (sum < 0) showAlert("Sum must be positive!");
            else
            {
             int warning= accountService.transferBetweenAccounts(id1,id2,sum);
             if(warning==0) showAlert("Insufficient funds");
             if(warning==1) showAlert("Account does not exist!");
             if(warning==2)
             {
                 showAlert("Transfer successful!");
                 activityService.addActivity(this.id,"transfered_between:"+id1+" and "+id2,Calendar.getInstance().getTime());
             }
            }
        }
        catch(NumberFormatException e)
        {
            showAlert("Id's must be numbers!");
        }


    }

    public void payBill()
    {
        try
        {
            Long id=Long.parseLong(idText.getText());
            Long sum=Long.parseLong(sumText.getText());
            int warning=accountService.payBill(id,sum);
            if(warning==0) showAlert("Not enough money");
            else if(warning==1) showAlert("Account does not exist");
            else
            {
                showAlert("Payed bill!");
                activityService.addActivity(id,"payed_bill_of_sum:"+sum,Calendar.getInstance().getTime());
            }

        }
        catch(NumberFormatException e)
        {
            showAlert("Id must be a number");
        }
    }

    public void back() throws IOException
    {
        ComponentFactory componentFactory = ComponentFactory.instance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeView.fxml"));
        loader.setController(this.employeeController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window =(Stage)backBtn.getScene().getWindow();
        window.setScene(scene);
        window.show();
    }





}
