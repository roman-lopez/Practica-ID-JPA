package gei.id.tutelado.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class Maquina {
    @Id
    @GeneratedValue(generator="generadorIdsMaquinas")
    private Long idMaquina;

    @Column(nullable = false, unique = true)
    private String codMaquina;

    @Column(nullable = false, unique=false)
    private String modelo;

    @ManyToOne(cascade={}, fetch= FetchType.EAGER)
    //El JoinColumn es para indicar que va a ser una FK en el relacional
    @JoinColumn (nullable=false, unique=false)
    private Cliente propietario;

    //En el relacional solo aparece e uno de los dos lados, pero en OO en ambos. Se pone, no?
    /*
    @Column(nullable = false, unique=false)
    private Set<Empleado> supervisores = new HashSet<Empleado>();
    */
}
