package controlador;

import modelo.ControlBD;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.POJOProyecto;

/**
 * Controlador de la VistaTabla.fxml
 *
 * @author Carlos Aguirre
 */
public class ControladorVistaTabla implements Initializable {

    // ############### CAMPOS ############### 
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXComboBox<String> cmbFiltro;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private TableView<POJOProyecto> tblProyectos;
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colPrioridad;
    @FXML
    private Label lblFiltro;
    @FXML
    private JFXButton btnAcerca;

    private ControlBD bd;
    private ObservableList<POJOProyecto> listaObservable;
    private String[] cadenasFiltro;

    // ############### AL INICIAR LA VENTANA FX ############### 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Crear el controlador de la BD
        this.bd = new ControlBD();

        // Crear la lista observable para la tabla
        this.listaObservable = FXCollections.observableArrayList();

        // Asignar los valores de cada columna a un campo del POJOProyecto
        this.colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.colEstado.setCellValueFactory(new PropertyValueFactory("cadenaEstado"));
        this.colPrioridad.setCellValueFactory(new PropertyValueFactory("cadenaPrioridad"));

        // Asignar a la tabla la lista observable
        this.tblProyectos.setItems(listaObservable);

        // Mostrar todos los proyectos en la tabla
        accionMostrarPorEstado(POJOProyecto.EN_PROCESO);

        // Rellenar el array de cadenas para el filtro (combo box)
        cadenasFiltro = new String[POJOProyecto.CADENAS_ESTADO.length + 1];
        cadenasFiltro[0] = "Todo";

        // Copiar los elementos que quedan al array
        System.arraycopy(POJOProyecto.CADENAS_ESTADO, 0, cadenasFiltro, 1, POJOProyecto.CADENAS_ESTADO.length);

        // Rellenar el combobox con los datos
        iniciarComboBox();

        // Cambiar el texto por defecto al no haber datos en la tabla
        Label etiquetaNueva = new Label("No hay proyectos que mostrar");
        etiquetaNueva.setStyle("-fx-font-size: 30; -fx-text-fill: #424242; -fx-font-weight: bold;");
        tblProyectos.setPlaceholder(etiquetaNueva);

        // Poner iconos a los botones
        ponerIconosBotones();
    }

    // ############### ACCIONES ############### 
    private void iniciarComboBox() {
        ObservableList<String> listaFiltro = FXCollections.observableArrayList();
        listaFiltro.addAll(cadenasFiltro);
        cmbFiltro.setItems(listaFiltro);
        cmbFiltro.setValue(cadenasFiltro[1]);
    }

    private void ponerIconosBotones() {
        btnBuscar.setGraphic(new ImageView(new Image("recursos/consultar.png")));
        btnNuevo.setGraphic(new ImageView(new Image("recursos/alta.png")));
        btnEditar.setGraphic(new ImageView(new Image("recursos/modificar.png")));
        lblFiltro.setGraphic(new ImageView(new Image("recursos/filtro.png")));
        btnAcerca.setGraphic(new ImageView(new Image("recursos/acerca.png")));
    }

    public void accionMostrarTodo() {
        // Obtener todos los proyectos
        bd.getRegistros(listaObservable);

        // Refrescar la tabla para que muestre los datos
        this.tblProyectos.refresh();
    }

    public void accionMostrarPorEstado(int estado) {
        // Obtener todos los proyectos
        bd.getRegistrosEstado(listaObservable, estado);

        // Refrescar la tabla para que muestre los datos
        this.tblProyectos.refresh();
    }

    public void accionBuscar(String titulo) {
        ObservableList<POJOProyecto> listaTemporal = FXCollections.observableArrayList();

        // Obtener todos los proyectos que tengan ese titulo o parecido
        bd.getRegistrosPorTitulo(listaTemporal, titulo);

        if (listaTemporal.size() > 0) {
            cmbFiltro.setValue("Búsqueda personalizada");

            this.listaObservable.clear();
            this.listaObservable.addAll(listaTemporal);
            tblProyectos.refresh();

        } else {
            mostrarMensaje("Información", "No hay resultados", "No se ha encontrado ningún proyecto con título '" + titulo + "'.\nAsegúrate de haberlo escrito bien.", false);
        }
    }

    public void accionNuevo() {
        mostrarVentanaEdicion(new POJOProyecto(), true, true);
    }

    public void accionEditar(POJOProyecto POJOseleccionado) {
        mostrarVentanaEdicion(POJOseleccionado, false, false);
    }

    // Este metodo es llamado desde la clase Main
    public void accionCerrar() {
        if (!bd.desconectar()) {
            mostrarMensaje("Error", "No se cerró la BD correctamente", "No se ha podido cerrar la BD.", true);
        }
        Platform.exit();
    }

    // Muestra los registros filtrado por el valor del Combo Box
    public void accionMostrarSeleccionado() {
        int indiceSeleccionado = cmbFiltro.getSelectionModel().getSelectedIndex();

        if (indiceSeleccionado == 0) {
            accionMostrarTodo();
        } else {
            accionMostrarPorEstado(indiceSeleccionado - 1);
        }
    }

    // ############### EVENTOS DE LA VENTANA FX ###############
    @FXML
    private void clickBuscar(MouseEvent event) {
        String resultado = mostrarBusqueda("Buscar", "Busqueda por titulo");

        if (!resultado.isEmpty()) {
            accionBuscar(resultado);
        }
    }

    @FXML
    private void clickNuevo(MouseEvent event) {
        accionNuevo();
    }

    @FXML
    private void clickEditar(MouseEvent event) {
        POJOProyecto proyectoSeleccionado = tblProyectos.getSelectionModel().getSelectedItem();
        if (proyectoSeleccionado == null) {
            mostrarMensaje("Información", "Selecciona algo primero", "Tienes que seleccionar un proyecto de la tabla antes.", false);
        } else {
            accionEditar(proyectoSeleccionado);
        }
    }

    @FXML
    private void clickAcerca(MouseEvent event) {
        mostrarVentanaAcerca();
    }

    @FXML
    private void clickCmbFiltro(ActionEvent event) {
        accionMostrarSeleccionado();
    }

    @FXML
    private void clickTabla(MouseEvent event) {
        POJOProyecto proyectoSeleccionado = tblProyectos.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 2 && proyectoSeleccionado != null) {
            accionEditar(proyectoSeleccionado);
        }
    }

    // ############### VENTANAS DE DIALOGO ############### 
    private void mostrarMensaje(String titulo, String cabecera, String mensaje, boolean error) {
        Alert alerta = new Alert(error ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private String mostrarBusqueda(String titulo, String cabecera) {
        TextInputDialog entradaDialogo = new TextInputDialog();
        entradaDialogo.setTitle(titulo);
        entradaDialogo.setHeaderText(cabecera);

        // Mostrar el cuadro de dialogo y esperar
        entradaDialogo.showAndWait();

        // Obtener el texto introducido
        String cadenaIntroducida = entradaDialogo.getEditor().getText().trim();

        return cadenaIntroducida;
    }

    private void mostrarVentanaEdicion(POJOProyecto proyectoSeleccionado, boolean modoEdicion, boolean nuevoProyecto) {
        try {
            // Cargar el archivo de vista fxml
            FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("/vista/VistaEdicion.fxml"));
            Parent root = cargadorFXML.load();

            // Pasarle los datos a la otra ventana
            ControladorVistaEdicion controlador = cargadorFXML.getController();
            controlador.setDatos(this, bd, proyectoSeleccionado, listaObservable, modoEdicion, nuevoProyecto);

            Scene escena = new Scene(root);
            Stage escenario = new Stage();
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setScene(escena);

            // Poner un titulo a la ventana
            escenario.setTitle("Edición de proyecto");

            // Ponerle un icono a la aplicacion
            escenario.getIcons().add(new Image("recursos/modificar2.png"));

            // Lo que pasara al cerrar la ventana
            escenario.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    ControladorVistaEdicion controlador = (ControladorVistaEdicion) cargadorFXML.getController();
                    if (controlador.hayCambios()) {
                        accionMostrarSeleccionado();
                    }
                }
            });

            // Mostrar
            escenario.showAndWait();

        } catch (IOException ex) {
            mostrarMensaje("Error", "Problema con la vista de edición", "No se ha podido cargar la vista edición.", true);
        }
    }

    private void mostrarVentanaAcerca() {
        try {
            // Cargar el archivo de vista fxml
            FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("/vista/VistaAcerca.fxml"));
            Parent root = cargadorFXML.load();
            
            Scene escena = new Scene(root);
            Stage escenario = new Stage();
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setScene(escena);

            // Poner un titulo a la ventana
            escenario.setTitle("Acerca de");

            // Ponerle un icono a la aplicacion
            escenario.getIcons().add(new Image("recursos/acerca2.png"));
            
            // Mostrar
            escenario.showAndWait();
            
        } catch (Exception ex) {
            mostrarMensaje("Error", "Problema con la vista de edición", "No se ha podido cargar la vista acerca de." + ex.getMessage(), true);
        }
    }
    
}
