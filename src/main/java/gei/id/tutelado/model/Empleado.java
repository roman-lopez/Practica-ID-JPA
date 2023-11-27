package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@NamedQueries({
        @NamedQuery (name="Empleado.numeroCobraMasDe1500",
                query="SELECT COUNT(e) FROM Empleado e WHERE e.salario > 1500"),
        @NamedQuery (name="Empleado.recuperaMaquinas",
                query="SELECT e FROM Empleado e ORDER BY e.nif"),
        @NamedQuery (name="Empleado.recuperaPorNif",
                query="SELECT e FROM Empleado e where e.nif=:nif"),
        @NamedQuery (name="Empleado.recuperaTodos",
                query="SELECT e FROM Empleado e ORDER BY e.nif"),
        @NamedQuery(name = "Empleado.recuperaMaquinasAsignadas",
                query = "SELECT e, m FROM Empleado e LEFT JOIN e.maquinas m ORDER BY e.idPersona")
})

@Entity
public class Empleado extends Persona {

    //Atributos
    @Column(nullable = false, unique=false)
    private LocalDateTime fechaContratacion;

    @Column(nullable = false, unique=false)
    private Double salario;

    @ManyToMany(cascade={}, fetch= FetchType.LAZY)
    @JoinTable (name="t_emp_maq",
                joinColumns = @JoinColumn(name="idPersona"),
                inverseJoinColumns = @JoinColumn(name="idMaquina"))
    private Set<Maquina> maquinas = new HashSet<Maquina>();

    //Constructor sin parámetros
    public Empleado () { }



    //Getters
    public LocalDateTime getFechaContratacion() { return fechaContratacion; }
    public Double getSalario() { return salario; }
    public Set<Maquina> getMaquinas() { return maquinas; }

    //Setters
    public void setFechaContratacion(LocalDateTime fechaContratacion) { this.fechaContratacion = fechaContratacion; }
    public void setSalario(Double salario) { this.salario = salario; }
    public void setMaquinas(Set<Maquina> maquinas) { this.maquinas = maquinas; }

    //Metodo para la asociación
    public void asignarMaquina(Maquina maquina) {
        if (this.maquinas.contains(maquina))
            throw new RuntimeException ("ERROR: La maquina " + maquina.getCodMaquina()
                    + " ya estaba asignada al empleado " + this.getNif());

        //Actualizamos lado propietario
        this.maquinas.add(maquina);
    }
}
