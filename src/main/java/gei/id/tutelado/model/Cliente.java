package gei.id.tutelado.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NamedQueries ({
        @NamedQuery (name="Cliente.recuperaPorNif",
                query="SELECT c FROM Cliente c where c.nif=:nif"),
        @NamedQuery (name="Cliente.recuperaTodos",
                query="SELECT c FROM Cliente c ORDER BY c.nif"),
})

@Entity
public class Cliente extends Persona {

    //Atributos
    @Column(nullable = false, unique=false)
    private String tipoCliente;     //( "PERSONAFISICA  ||  "EMPRESA" )

    @ElementCollection
    private Set<Long> telefonos = new HashSet<Long>();

    @OneToMany(mappedBy="propietario", fetch= FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.REMOVE} )
    private Set<Maquina> maquinas = new HashSet<Maquina>();

    //Constructor sin parámetros
    public Cliente () { }

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
        maquina.setPropietario(null);       //Cuando llamemos a este caso de uso, gestionar este NULL porque en SQL no puede haber NULL si es obligatorio
        //Actualizamos lado inverso
        this.maquinas.remove(maquina);
    }
}
