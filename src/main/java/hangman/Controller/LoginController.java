package hangman.Controller;

import hangman.Model.DatabaseManager;
import hangman.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField nameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField usernameText;

    @FXML
    void logIn(ActionEvent event) throws SQLException {
        User user = DatabaseManager.readUser(usernameText.getText());
        if (user != null) {
            GameController.user = DatabaseManager.readUser(usernameText.getText());
            try {
                changeScene(event, "/hangman/game-view.fxml");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            showAlert();
        }
    }

    @FXML
    void signUp(ActionEvent event) throws SQLException {
        if (nameText.isDisabled()) {
            nameText.setDisable(false);
            return;
        }

        if (DatabaseManager.findUser(usernameText.getText())) {
            showAlert();
            return;
        }

        DatabaseManager.createUser(usernameText.getText(), passwordText.getText(), nameText.getText());
        logIn(event);
    }

    @FXML
    void showLeaderboard(ActionEvent event) {
        try {
            changeScene(event, "/hangman/leaderboard-view.fxml");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Username");
        alert.setContentText("Please enter a valid username");

        if (alert.showAndWait().get() == ButtonType.OK) {
            usernameText.clear();
            passwordText.clear();
            nameText.clear();
            nameText.setDisable(true);
        }
    }

    private void changeScene(ActionEvent event, String address) throws IOException {
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(address)));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        loginStage.close();
    }
}
