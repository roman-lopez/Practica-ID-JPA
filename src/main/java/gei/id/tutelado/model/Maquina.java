package gei.id.tutelado.model;

import javax.persistence.*;


@TableGenerator(name="generadorIdsMaquinas", table="tabla_ids",
        pkColumnName="nombre_id", pkColumnValue="idMaquina",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)

@NamedQueries ({
        @NamedQuery (name="Maquina.recuperaPorCodigo",
                query="SELECT m FROM Maquina m where m.codMaquina=:codMaquina"),
        @NamedQuery (name="Maquina.recuperaTodas",
                query="SELECT m FROM Maquina m"),

        @NamedQuery (name="Maquina.recuperaTodasPropietario",
                query="SELECT m FROM Maquina m JOIN m.propietario c WHERE c=:c"),
        @NamedQuery (name="Maquina.recuperaMaquinasEmpresa",
                query="SELECT m FROM Maquina m WHERE m.propietario IN " +
                "(SELECT c FROM Cliente c WHERE c.tipoCliente='EMPRESA')")
})

@Entity
public class Maquina {

    //Atributos
    @Id
    @GeneratedValue(generator="generadorIdsMaquinas")
    private Long idMaquina;

    @Column(nullable = false, unique = true)
    private String codMaquina;

    @Column(nullable = true, unique=false)
    private String modelo;

    @ManyToOne(cascade={}, fetch= FetchType.EAGER)
    //El JoinColumn es para indicar que va a ser una FK en el relacional
    @JoinColumn (nullable=false, unique=false)
    private Cliente propietario;

    //Constructor sin parámetros
    public Maquina () { }

    //Getters
    public Long getIdMaquina() { return idMaquina; }
    public String getCodMaquina() { return codMaquina; }
    public String getModelo() { return modelo; }
    public Cliente getPropietario() { return propietario; }

    //Setters
    public void setIdMaquina(Long idMaquina) { this.idMaquina = idMaquina; }
    public void setCodMaquina(String codMaquina) { this.codMaquina = codMaquina; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setPropietario(Cliente propietario) { this.propietario = propietario; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codMaquina == null) ? 0 : codMaquina.hashCode());
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
        Maquina other = (Maquina) obj;
        if (codMaquina == null) {
            return other.codMaquina == null;
        } else return codMaquina.equals(other.codMaquina);
    }

    @Override
    public String toString() {
        String result = "Maquina [id=" + idMaquina + ", codigo=" + codMaquina;
        if (this.modelo != null) result += ", modelo=" + modelo;
        result += "]";
        return result;
    }

}
