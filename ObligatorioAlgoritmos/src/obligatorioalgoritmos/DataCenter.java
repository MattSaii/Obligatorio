
package obligatorioalgoritmos;

public class DataCenter extends Punto{
    public Empresa empresaPropietaria;
    public String nombre;
    public int capacidadCPUenHoras;
    public int costoCPUxHora;
    public boolean estado;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    

    public Empresa getEmpresaPropietaria() {
        return empresaPropietaria;
    }

    public void setEmpresaPropietaria(Empresa empresaPropietaria) {
        this.empresaPropietaria = empresaPropietaria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadCPUenHoras() {
        return capacidadCPUenHoras;
    }

    public void setCapacidadCPUenHoras(int capacidadCPUenHoras) {
        this.capacidadCPUenHoras = capacidadCPUenHoras;
    }

    public int getCostoCPUxHora() {
        return costoCPUxHora;
    }

    public void setCostoCPUxHora(int costoCPUxHora) {
        this.costoCPUxHora = costoCPUxHora;
    }
    public DataCenter()
    {
        
    }
    
    
}
