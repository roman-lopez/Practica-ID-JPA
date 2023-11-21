package gei.id.tutelado.model;

import javax.persistence.*;

@TableGenerator(name="generadorIdsPersonas", table="tabla_ids",
        pkColumnName="nombre_id", pkColumnValue="idPersona",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)

@NamedQueries ({
        @NamedQuery (name="Persona.recuperaPorNif",
                query="SELECT p FROM Persona u where p.nif=:nif"),
        @NamedQuery (name="Persona.recuperaTodos",
                query="SELECT p FROM Persona p ORDER BY p.nif")
})

@Entity
public abstract class Persona {

    //Atributos
    @Id
    @GeneratedValue(generator="generadorIdsPersonas")
    private Long idPersona;

    @Column(nullable = false, unique = true)
    private String nif;

    @Column(nullable = false, unique=false)
    private String nombrePila;

    @Column(nullable = false, unique=false)
    private String apellidos;

    @Column(nullable = true, unique=true)
    private String email;

    //Getters
    public Long getIdPersona() { return idPersona; }
    public String getNif() { return nif; }
    public String getNombrePila() { return nombrePila; }
    public String getApellidos() { return apellidos; }
    public String getEmail() { return email; }

    //Setters
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }
    public void setNif(String nif) { this.nif = nif; }
    public void setNombrePila(String nombrePila) { this.nombrePila = nombrePila; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setEmail(String email) { this.email = email; }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nif == null) ? 0 : nif.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Persona other = (Persona) obj;
        if (nif == null) {
            return other.nif == null;
        } else return nif.equals(other.nif);
    }

    @Override
    public String toString() {
        String result = "Persona [id=" + idPersona + ", nif=" + nif + ", nombre=" + nombrePila +
                ", apellidos=" + apellidos;
        if (this.email != null) result += ", email=" + email;
        result += "]";
        return result;
    }
}

