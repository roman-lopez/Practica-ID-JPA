package gei.id.tutelado.dao.persona;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Persona;
import java.util.List;

public interface PersonaDao {

    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    Persona almacena (Persona persona);
    Persona modifica (Persona persona);
    void elimina (Persona persona);
    Persona recuperaPorNif (String nif);

    //QUERIES ADICIONAIS
    List<Persona> recuperaTodos();
}
