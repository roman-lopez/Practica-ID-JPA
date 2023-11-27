package gei.id.tutelado.model;

public class EmpleadoDTO {
    private Empleado empleado;
    private Maquina maquina;

    public EmpleadoDTO(Empleado empleado, Maquina maquina) {
        this.empleado = empleado;
        this.maquina = maquina;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }
}
