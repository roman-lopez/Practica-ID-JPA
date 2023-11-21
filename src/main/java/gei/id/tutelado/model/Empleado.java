package gei.id.tutelado.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Empleado extends Persona {

    //Atributos
    @Column(nullable = false, unique=false)
    private LocalDateTime fechaContratacion;

    @Column(nullable = false, unique=false)
    private Double salario;

    @ManyToMany(cascade={}, fetch= FetchType.LAZY)
    private Set<Maquina> maquinas = new HashSet<Maquina>();

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
