package it.unibs.diabetici.client;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
  @Override public void start(Stage stage){
    var api = new ApiClient("http://localhost:8080");

    TextField email = new TextField(); 
    email.setPromptText("Email");
    PasswordField password = new PasswordField(); 
    password.setPromptText("Password");
    Button loginBtn = new Button("Accedi"); 
    Label status = new Label();

    loginBtn.setOnAction(e->{
      try{
        var r = api.login(email.getText(), password.getText());
        String who = r.pazienteId()!=null ? ("Paziente #"+r.pazienteId()) : ("Diabetologo #"+r.diabetologoId());
        status.setText("Benvenuto: "+who+" — Ruolo: "+r.role());
      }catch(Exception ex){ status.setText("Credenziali non valide o server non raggiungibile"); }
    });

    VBox v = new VBox(10,new Label("Telemedicina — Login"), email, password, loginBtn, status);
    v.setPadding(new Insets(24)); v.setAlignment(Pos.CENTER); v.setPrefWidth(380);
    stage.setTitle("Pazienti Diabetici — Login");
    stage.setScene(new Scene(new StackPane(v), 440, 300));
    stage.show();
  }
  public static void main(String[] args){ launch(args); }
}
