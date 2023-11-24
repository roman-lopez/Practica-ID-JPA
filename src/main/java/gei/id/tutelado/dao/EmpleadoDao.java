package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Empleado;

import java.util.List;
import java.util.Set;

public interface EmpleadoDao {

    Empleado restauraMaquinas(Empleado empleado);

    Integer numeroCobraMasDe1500(Set<Empleado> empleados);


    void setup (Configuracion config);

    // OPERACIONS POR ATRIBUTOS LAZY
    //Usuario restauraEntradasLog (Usuario user);
    // Recibe un usuario coa coleccion de entradas de log como proxy SEN INICIALIZAR
    // Devolve unha copia do usuario coa coleccion de entradas de log INICIALIZADA

    //QUERIES ADICIONAIS
   // List<Usuario> recuperaTodos();
}
