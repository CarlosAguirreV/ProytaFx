package controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Clase principal, esta es la que inicia todo.
 *
 * @author Carlos Aguirre
 */
public class Main extends Application {

    public static final String VERSION_APLICACION = "11.12.20";
    public static final String NOMBRE_APLICACION = "Proyta";
    public static final String NOMBRE_ARCHIVO = "bd_proyectos";
    public static final String WEB_DONATIVO = "https://ko-fi.com/carlosaguirrev";
    public static final String WEB_GITHUB = "https://github.com/CarlosAguirreV/ProytaFx.git";

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
            escenario.setTitle("Proyta Fx");

            // Ponerle un icono a la aplicacion
            escenario.getIcons().add(new Image("recursos/icono1.png"));
            escenario.getIcons().add(new Image("recursos/icono2.png"));
            escenario.getIcons().add(new Image("recursos/icono3.png"));
            escenario.getIcons().add(new Image("recursos/icono4.png"));

            // Hacer que la ventana este maximizada por defecto
            escenario.setMaximized(true);

            // Lo que pasara al cerrar la ventana
            escenario.setOnCloseRequest((WindowEvent event) -> {
                ControladorVistaTabla controlador = (ControladorVistaTabla) cargadorFXML.getController();
                controlador.accionCerrar();
                System.exit(0);
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
