package gei.id.tutelado.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

public class Cliente extends Persona {

    //Atributos
    @Column(nullable = false, unique=false)
    private String tipoCliente;     //( "PERSONAFISICA  ||  "EMPRESA" )

    @Column(nullable = true, unique=false)
    private Set<Long> telefonos = new HashSet<Long>();

    @OneToMany(mappedBy="propietario", fetch= FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE} )
    private Set<Maquina> maquinas = new HashSet<Maquina>();

    //Getters
    public String getTipoCliente() { return tipoCliente; }
    public Set<Long> getTelefonos() { return telefonos; }
    public Set<Maquina> getMaquinas() { return maquinas; }

    //Setters
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
    public void setTelefonos(Set<Long> telefonos) { this.telefonos = telefonos; }
    public void setMaquinas(Set<Maquina> maquinas) { this.maquinas = maquinas; }


    //Metodo de conveniencia de agregación para asegurarnos de que
    // actualizamos los dos extremos de la asociación al mismo tiempo
    public void agregarMaquina(Maquina maquina) {

        //Comprobamos que la máquina no tenga propietario
        if (maquina.getPropietario() != null)
            throw new RuntimeException ("ERROR: La maquina ya tiene propietario");

        //Actualizamos lado propietario
        maquina.setPropietario(this);
        //Actualizamos lado inverso
        this.maquinas.add(maquina);
    }


    //Metodo de conveniencia de eliminación para asegurarnos de que
    // actualizamos los dos extremos de la asociación al mismo tiempo
    public void eliminarMaquina(Maquina maquina) {

        //Comprobamos si esa máquina pertenece al cliente
        if (!this.getNif().equals(maquina.getPropietario().getNif()))
            throw new RuntimeException ("ERROR: La maquina no pertenece al cliente");

        //Actualizamos lado propietario
        maquina.setPropietario(null);       //TODO revisar esta linea
        //Actualizamos lado inverso
        this.maquinas.remove(maquina);
    }
}
