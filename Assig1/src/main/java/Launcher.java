import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ComponentFactory;

public class Launcher extends Application{



        @Override
        public void start(Stage primaryStage) throws Exception {

            // just load fxml file and display it in the stage:
            ComponentFactory c=ComponentFactory.instance();
            LoginController loginController=new LoginController(c.getAuthenticationService());


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
            loader.setController(loginController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        // main method to support non-JavaFX-aware environments:

        public static void main(String[] args) {


            launch(args);
        }

}
