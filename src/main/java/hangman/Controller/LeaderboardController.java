package hangman.Controller;

import hangman.Model.DatabaseManager;
import hangman.Model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    @FXML
    private ListView<String> recordsList;

    @FXML
    void backButton(ActionEvent event) throws IOException {
        Stage leaderboardStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/hangman/login-view.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        leaderboardStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Game> records = DatabaseManager.readGame();
            for (Game record : records) {
                recordsList.getItems().add(record.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
