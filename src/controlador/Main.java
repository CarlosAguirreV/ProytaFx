package controlador;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Carlos Aguirre
 */
public class Main extends Application {

    public static final String VERSION_APLICACION = "11.05.20";

    @Override
    public void start(Stage escenario) throws Exception {
        try {
            // Cargar  el archivo de vista fxml
            FXMLLoader cargadorFXML = new FXMLLoader();
            cargadorFXML.setLocation(Main.class.getResource("/vista/VistaTabla.fxml"));

            // Obtener el panel por defecto de la vista
            Pane panel = (Pane) cargadorFXML.load();

            // Crear una nueva escena con el panel por defecto
            Scene escena = new Scene(panel);

            // Meter la escena en el escenario
            escenario.setScene(escena);

            // Poner un titulo a la ventana
            escenario.setTitle("Control de proyectos (v" + VERSION_APLICACION + ")");

            // Ponerle un icono a la aplicacion
            escenario.getIcons().add(new Image("recursos/logo.png"));

            // Lo que pasara al cerrar la ventana
            escenario.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    ControladorVistaTabla controlador = (ControladorVistaTabla) cargadorFXML.getController();
                    controlador.accionCerrar();
                    System.exit(0);
                }
            });

            // Mostrarlo
            escenario.show();

        } catch (Exception e) {
            System.out.println("ERROR - Al cargar la vista.\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
