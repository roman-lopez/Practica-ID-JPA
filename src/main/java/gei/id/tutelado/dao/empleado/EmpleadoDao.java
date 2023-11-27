package gei.id.tutelado.dao.empleado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.persona.PersonaDao;
import gei.id.tutelado.model.Empleado;

import java.util.List;

public interface EmpleadoDao extends PersonaDao {


    @Override
    Empleado recuperaPorNif (String nif);

    @Override
    List recuperaTodos ();

    void setup (Configuracion config);

    //QUERIES ADICIONALES
    //Long numeroCobraMasDe1500();

    //List<Object[]> recuperaMaquinasAsignadas();

    // OPERACIONES POR ATRIBUTOS LAZY
    Empleado restauraMaquinas(Empleado empleado);






    // OPERACIONS POR ATRIBUTOS LAZY
    //Usuario restauraEntradasLog (Usuario user);
    // Recibe un usuario coa coleccion de entradas de log como proxy SEN INICIALIZAR
    // Devolve unha copia do usuario coa coleccion de entradas de log INICIALIZADA

    //QUERIES ADICIONAIS
   // List<Usuario> recuperaTodos();
}
