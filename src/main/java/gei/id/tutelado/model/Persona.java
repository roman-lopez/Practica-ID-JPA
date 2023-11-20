package gei.id.tutelado.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public abstract class Persona {
    @Id
    @GeneratedValue(generator="generadorIdsPersonas")
    private Long idPersona;

    @Column(nullable = false, unique = true)
    private String nif;

    @Column(nullable = false, unique=false)
    private String nombrePila;

    @Column(nullable = false, unique=false)
    private String apellidos;

    @Column(nullable = false, unique=false)
    private String email;
}
