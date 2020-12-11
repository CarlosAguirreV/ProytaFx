/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import static controlador.Main.*;
import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador de la Acerca.fxml
 *
 * @author Carlos Aguirre
 */
public class ControladorAcerca implements Initializable {

    @FXML
    private JFXButton btnCerrar;
    @FXML
    private JFXButton btnDonativo;
    @FXML
    private JFXButton btnGitHub;
    @FXML
    private Label lblSobre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Poner iconos.
        btnCerrar.setGraphic(new ImageView(new Image("recursos/cancelar.png")));
        btnDonativo.setGraphic(new ImageView(new Image("recursos/donar.png")));
        btnGitHub.setGraphic(new ImageView(new Image("recursos/github.png")));
        
        // Definir texto acerca de.
        lblSobre.setText(
            "Título"
            + "\n" + NOMBRE_APLICACION + " (Fx)"
            + "\n\nDesarrollador"
            + "\nCarlos Aguirre"
            + "\n\nSobre esta aplicación"
            + "\nVersión: " + VERSION_APLICACION
            + "\nEste programa te permite gestionar tus proyectos e ideas de manera sencilla y ordenada."
            + "\nPara almacenar la información se emplea una pequeña base de datos SQLite. La base de datos se almacena en el archivo " + NOMBRE_ARCHIVO + "."
            + "\nSi te gusta el proyecto y quieres contribuir puedes hacer un pequeño donativo haciendo click en el botón Invitar a un cafe."
            + "\nMuchas gracias por usar esta aplicación.");
    }

    @FXML
    private void clickCerrar(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickDonativo(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(WEB_DONATIVO));
        } catch (Exception ex) {
            mostrarLinkWeb("Donativo", WEB_DONATIVO);
        }
    }

    @FXML
    private void clickGitHub(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(WEB_GITHUB));
        } catch (Exception ex) {
            mostrarLinkWeb("Git Hub", WEB_GITHUB);
        }
    }
    
    private void mostrarLinkWeb(String titulo, String url) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText("No se pudo abrir el navegador.\nAún así puedes acceder a la Web de forma manual copiando la URL de abajo en el navegador.");
        alert.setContentText(url);
        
        alert.showAndWait();
    }

}
