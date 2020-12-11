package modelo;

import javafx.collections.ObservableList;

/**
 * Interfaz que con tiene todos los metodos usados por el controlador de la BD.
 * 
 * @author Carlos Aguirre
 */
public interface ControlBDInterfaz {
    public POJOProyecto getRegistroPorId(int id);
    public POJOProyecto getUltimoRegistroCreado();
    public void getRegistros(ObservableList<POJOProyecto> registros);
    public void getRegistrosPorTitulo(ObservableList<POJOProyecto> registros, String titulo);
    public void getRegistrosPorTituloEsp(ObservableList<POJOProyecto> registros, String titulo);
    public void getRegistrosEstado(ObservableList<POJOProyecto> registros, int estado);
    public void getRegistrosPrioridad(ObservableList<POJOProyecto> registros, int prioridad);
    public boolean addRegistro(POJOProyecto registro);
    public boolean removeRegistro(int id);
    public boolean modifyRegistro(POJOProyecto registro);
    public String getMensajeError();
    public boolean desconectar();
}
