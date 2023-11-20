package gei.id.tutelado.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Cliente extends Persona {

    @Column(nullable = false, unique=false)
    private String tipoCliente;

    @Column(nullable = false, unique=false)
    private Set<Long> telefonos = new HashSet<Long>();

    @OneToMany(mappedBy="propietario", fetch= FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE} )
    private Set<Maquina> maquinasCli = new HashSet<Maquina>();


}
