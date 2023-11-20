package gei.id.tutelado.model;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Empleado extends Persona {
    @Column(nullable = false, unique=false)
    private LocalDateTime fechaContratacion;

    @Column(nullable = false, unique=false)
    private Set<Maquina> maquinasEmp = new HashSet<Maquina>();

    @Column(nullable = false, unique=false)
    private Double salario;
}
