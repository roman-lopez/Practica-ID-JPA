package gei.id.tutelado.dao.cliente;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.persona.PersonaDao;
import gei.id.tutelado.model.Cliente;
import gei.id.tutelado.model.Empleado;

import java.util.List;

public interface ClienteDao extends PersonaDao {

    void setup (Configuracion config);

    @Override
    Cliente recuperaPorNif (String nif);

    @Override
    List recuperaTodos ();

    // OPERACIONES POR ATRIBUTOS LAZY
    Cliente restauraMaquinas(Cliente cliente);
}
