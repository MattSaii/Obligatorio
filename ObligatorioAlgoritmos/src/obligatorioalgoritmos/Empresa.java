
package obligatorioalgoritmos;

public class Empresa {
    public String nombre;
    public String direccion;
    public String pais;
    public String eMail_contacto;
    public String color;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String geteMail_contacto() {
        return eMail_contacto;
    }

    public void seteMail_contacto(String eMail_contacto) {
        this.eMail_contacto = eMail_contacto;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public Empresa()
    {
         
    }
    
    public String toString(){
        return this.getNombre().toString()+" "+this.geteMail_contacto().toString();
    }
    
}
