package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Empleado;
import gei.id.tutelado.model.Persona;

import java.util.List;
import java.util.Set;

public interface EmpleadoDao extends PersonaDao{


    @Override
    Empleado recuperaPorNif (String nif);


    void setup (Configuracion config);

    //QUERIES ADICIONALES
    Long numeroCobraMasDe1500();

    List<Object[]> recuperaMaquinasAsignadas();

    // OPERACIONES POR ATRIBUTOS LAZY
    Empleado restauraMaquinas(Empleado empleado);






    // OPERACIONS POR ATRIBUTOS LAZY
    //Usuario restauraEntradasLog (Usuario user);
    // Recibe un usuario coa coleccion de entradas de log como proxy SEN INICIALIZAR
    // Devolve unha copia do usuario coa coleccion de entradas de log INICIALIZADA

    //QUERIES ADICIONAIS
   // List<Usuario> recuperaTodos();
}
