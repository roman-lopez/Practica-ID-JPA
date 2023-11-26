package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.*;

import java.util.List;

public interface MaquinaDao {

    void setup (Configuracion config);

    Maquina almacena (Maquina maquina);
    Maquina modifica (Maquina maquina);
    void elimina (Maquina maquina);
    Maquina recuperaPorCodigo (String codMaquina);


    //QUERIES ADICIONALES
    List<Maquina> recuperaTodasPropietario(Cliente c);
    List<Maquina> recuperaMaquinasEmpresa();
}
