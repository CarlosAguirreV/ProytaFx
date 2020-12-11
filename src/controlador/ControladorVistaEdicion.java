package controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import modelo.ControlBD;
import modelo.POJOProyecto;

/**
 * Controlador de la VistaEdicion.fxml
 *
 * @author Carlos Aguirre
 */
public class ControladorVistaEdicion implements Initializable {

    // ############### CAMPOS ############### 
    @FXML
    private JFXButton btnVolver;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtTitulo;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private JFXComboBox<String> cmbPrioridad;
    @FXML
    private JFXTextField txtFechaInicio;
    @FXML
    private JFXTextField txtFechaFin;
    @FXML
    private JFXTextField txtRequisitos;
    @FXML
    private JFXTextField txtDestino;
    @FXML
    private JFXTextField txtProblemas;
    @FXML
    private JFXTextField txtMejoras;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private FlowPane pnlSuperior;
    @FXML
    private FlowPane pnlInferior;

    private ControladorVistaTabla vistaTabla;
    private ControlBD bd;
    private POJOProyecto proyectoActual;
    private ObservableList<POJOProyecto> listaObservable;
    private boolean modoEdicion;
    private boolean nuevoProyecto;
    private boolean hayCambios;

    // ############### AL INICIAR LA VENTANA FX ###############
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarComboBox();
        ponerIconosBotones();
        hayCambios = false;
    }

    // ############### ACCIONES ###############
    // Metodo llamado desde el ControloadorVistaTabla para pasarle los datos
    public void setDatos(ControladorVistaTabla vistaTabla, ControlBD bd, POJOProyecto proyectoSeleccionado, ObservableList<POJOProyecto> listaObservable, boolean modoEdicion, boolean nuevoProyecto) {
        this.vistaTabla = vistaTabla;
        this.bd = bd;
        this.proyectoActual = proyectoSeleccionado;
        this.listaObservable = listaObservable;
        this.nuevoProyecto = nuevoProyecto;
        setModoEdicion(modoEdicion);

        mostrarDatos();
    }

    private void mostrarDatos() {
        txtID.setText(proyectoActual.getId() == -1 ? "Sin crear" : Integer.toString(proyectoActual.getId()));
        cmbEstado.setValue(POJOProyecto.CADENAS_ESTADO[proyectoActual.getEstado()]);
        cmbPrioridad.setValue(POJOProyecto.CADENAS_PRIORIDAD[proyectoActual.getPrioridad()]);
        txtTitulo.setText(getCadenaNoNull(proyectoActual.getTitulo()));
        txtFechaInicio.setText(getCadenaNoNull(proyectoActual.getFechaInicio()));
        txtFechaFin.setText(getCadenaNoNull(proyectoActual.getFechaFin()));
        txtRequisitos.setText(getCadenaNoNull(proyectoActual.getRequisitos()));
        txtDestino.setText(getCadenaNoNull(proyectoActual.getDestino()));
        txtProblemas.setText(getCadenaNoNull(proyectoActual.getProblemas()));
        txtMejoras.setText(getCadenaNoNull(proyectoActual.getMejoras()));
        txtDescripcion.setText(getCadenaNoNull(proyectoActual.getDescripcion()));
    }

    private POJOProyecto getProyecto() {
        POJOProyecto proyecto = new POJOProyecto();

        proyecto.setId(proyectoActual.getId());
        proyecto.setEstado(cmbEstado.getSelectionModel().getSelectedIndex());
        proyecto.setPrioridad(cmbPrioridad.getSelectionModel().getSelectedIndex());
        proyecto.setTitulo(txtTitulo.getText().trim());
        proyecto.setFechaInicio(txtFechaInicio.getText().trim());
        proyecto.setFechaFin(txtFechaFin.getText().trim());
        proyecto.setRequisitos(txtRequisitos.getText().trim());
        proyecto.setDestino(txtDestino.getText().trim());
        proyecto.setProblemas(txtProblemas.getText().trim());
        proyecto.setMejoras(txtMejoras.getText().trim());
        proyecto.setDescripcion(txtDescripcion.getText().trim());

        return proyecto;
    }

    public boolean hayCambios() {
        return hayCambios;
    }

    private String getCadenaNoNull(String cadena) {
        return cadena == null ? "" : cadena;
    }

    private void iniciarComboBox() {
        // Para el combo box de estado
        ObservableList<String> listaFiltro1 = FXCollections.observableArrayList();
        listaFiltro1.addAll(POJOProyecto.CADENAS_ESTADO);
        cmbEstado.setItems(listaFiltro1);
        cmbEstado.setValue(POJOProyecto.CADENAS_ESTADO[0]);

        // Para el combo box de prioridad
        ObservableList<String> listaFiltro2 = FXCollections.observableArrayList();
        listaFiltro2.addAll(POJOProyecto.CADENAS_PRIORIDAD);
        cmbPrioridad.setItems(listaFiltro2);
        cmbPrioridad.setValue(POJOProyecto.CADENAS_PRIORIDAD[0]);
    }

    private void ponerIconosBotones() {
        btnVolver.setGraphic(new ImageView(new Image("recursos/volver.png")));
        btnEditar.setGraphic(new ImageView(new Image("recursos/modificar.png")));
        btnCancelar.setGraphic(new ImageView(new Image("recursos/cancelar.png")));
        btnGuardar.setGraphic(new ImageView(new Image("recursos/guardar.png")));
        btnEliminar.setGraphic(new ImageView(new Image("recursos/eliminar.png")));
    }

    private String getFechaActual() {
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-YYY");
        return formatoFecha.format(calendario.getTime());
    }

    private void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;

        btnVolver.setManaged(!modoEdicion);
        btnVolver.setVisible(!modoEdicion);
        btnEditar.setManaged(!modoEdicion);
        btnEditar.setVisible(!modoEdicion);
        btnCancelar.setManaged(modoEdicion);
        btnCancelar.setVisible(modoEdicion);
        btnGuardar.setManaged(modoEdicion);
        btnGuardar.setVisible(modoEdicion);

        if (nuevoProyecto) {
            btnEliminar.setManaged(false);
            btnEliminar.setVisible(false);
        } else {
            btnEliminar.setManaged(modoEdicion);
            btnEliminar.setVisible(modoEdicion);
        }

        cmbEstado.setDisable(!modoEdicion);
        cmbPrioridad.setDisable(!modoEdicion);
        txtTitulo.setDisable(!modoEdicion);
        txtFechaInicio.setDisable(!modoEdicion);
        txtFechaFin.setDisable(!modoEdicion);
        txtRequisitos.setDisable(!modoEdicion);
        txtDestino.setDisable(!modoEdicion);
        txtProblemas.setDisable(!modoEdicion);
        txtMejoras.setDisable(!modoEdicion);
        txtDescripcion.setDisable(!modoEdicion);

        pnlSuperior.setStyle(modoEdicion ? "-fx-background-color: #312429" : "-fx-background-color: #263238");
        pnlInferior.setStyle(modoEdicion ? "-fx-background-color: #312429" : "-fx-background-color: #263238");

        // Quitar el color rojo del titulo si lo tenia
        if (!modoEdicion) {
            txtTitulo.setStyle("-fx-background-color: transparent");
        }
    }

    private boolean existeTitulo(String titulo) {
        boolean existe = false;

        for (POJOProyecto proyecto : listaObservable) {
            if (proyecto.getTitulo().equals(titulo)) {
                existe = true;
                break;
            }
        }

        return existe;
    }

    private void addProyecto() {
        POJOProyecto proyectoGenerado = getProyecto();

        if (bd.addRegistro(proyectoGenerado)) {
            hayCambios = true;
            this.proyectoActual = bd.getUltimoRegistroCreado();
            setModoEdicion(false);
            nuevoProyecto = false;
            mostrarDatos();
        } else {
            mostrarMensaje("Error", "Fallo al guardar", "No se ha podido guardar el proyecto. Ha ocurrido algo con la BD.", true);
        }
    }

    private void guardarCambios() {
        POJOProyecto proyectoGenerado = getProyecto();

        if (bd.modifyRegistro(proyectoGenerado)) {
            hayCambios = true;
            setModoEdicion(false);
            this.proyectoActual = proyectoGenerado;
            mostrarDatos();
        } else {
            mostrarMensaje("Error", "Fallo al modificar", "No se han podido guardar los cambios del proyecto. Ha ocurrido algo con la BD.", true);
        }
    }

    private void cerrarVentana() {
        if (hayCambios) {
            vistaTabla.accionMostrarSeleccionado();
        }

        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

    // ############### EVENTOS DE LA VENTANA FX ###############
    @FXML
    private void clickVolver(MouseEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clickEditar(MouseEvent event) {
        setModoEdicion(true);
    }

    @FXML
    private void clickCancelar(MouseEvent event) {
        if (nuevoProyecto) {
            cerrarVentana();
        } else {
            setModoEdicion(false);
            mostrarDatos();
        }
    }

    @FXML
    private void clickGuardar(MouseEvent event) {

        String tituloProyecto = txtTitulo.getText().trim();

        // Primero hay que comprobar que el titulo no este vacio
        if (tituloProyecto.isEmpty()) {
            mostrarMensaje("Aviso", "El título no puede estar vacio", "El campo título es un valor requerido, no puedes dejarlo en blanco.", true);
            txtTitulo.setStyle("-fx-background-color: #5E2B3C");
        } else {

            // Si ha llegado hasta aqui es porque cumple con todo, solo queda guardar
            if (nuevoProyecto) {
                // Si se trata de un proyecto nuevo, hay que comprobar que ese titulo no existe
                if (existeTitulo(tituloProyecto)) {
                    mostrarMensaje("Aviso", "Ese título ya existe", "El título '" + tituloProyecto + "' ya se asignó a otro proyecto. Escribe otro distinto.", true);
                } else {
                    addProyecto();
                }
            } else {
                if (proyectoActual.getTitulo().equals(tituloProyecto)) {
                    // Si es el mismo titulo
                    guardarCambios();

                } else if (existeTitulo(tituloProyecto)) {
                    // Si es un titulo distinto pero coincide con otro
                    mostrarMensaje("Aviso", "Ese título ya existe", "El título '" + tituloProyecto + "' ya se asignó a otro proyecto. Escribe otro distinto.", true);

                } else {
                    // Si es un titulo distinto pero no esta en la lista
                    guardarCambios();
                }
            }

        }

    }

    @FXML
    private void clickEliminar(MouseEvent event) {
        int id = proyectoActual.getId();

        if (id == POJOProyecto.SIN_CREAR) {
            mostrarMensaje("Aviso", "No se puede borrar algo que no ha sido creado aún", "Este proyecto aún no existe, guardalo primero antes de querer borrarlo.", true);
        } else {

            if (pedirConfirmacion("Confirmar", "¿Deseas borrar este proyecto?", "No podrás recuperarlo, piénsalo bien.")) {
                if (bd.removeRegistro(proyectoActual.getId())) {
                    hayCambios = true;
                    cerrarVentana();
                } else {
                    mostrarMensaje("Error", "No se pudo eliminar", "Fallo al borrar en la base de datos.", true);
                }
            }

        }

    }

    @FXML
    private void clickFechaInicio(MouseEvent event) {
        if (txtFechaInicio.getText().isEmpty() && modoEdicion) {
            txtFechaInicio.setText(getFechaActual());
        }
    }

    @FXML
    private void clickCmbEstado(ActionEvent event) {
        // Esto hace que si seleccionas Terminado ponga la fecha final del proyecto automaticamente si no tiene nada escrito
        // Tambien pone la fecha de inicio si esta no esta inicializada
        if (cmbEstado.getSelectionModel().getSelectedIndex() == POJOProyecto.TERMINADOS && modoEdicion) {
            if (txtFechaInicio.getText().isEmpty()) {
                txtFechaInicio.setText(getFechaActual());
            }
            if (txtFechaFin.getText().isEmpty()) {
                txtFechaFin.setText(getFechaActual());
            }
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

    private boolean pedirConfirmacion(String titulo, String cabecera, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(mensaje);

        Optional<ButtonType> resultado = alerta.showAndWait();

        return resultado.get() == ButtonType.OK;
    }

}
