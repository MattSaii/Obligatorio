
package obligatorioalgoritmos;

public class NodoEmpresa {
    
    Empresa empresa;
    ArbolEmpresa izq;
    ArbolEmpresa der;
    

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ArbolEmpresa getIzq() {
        return izq;
    }

    public void setIzq(ArbolEmpresa izq) {
        this.izq = izq;
    }

    public ArbolEmpresa getDer() {
        return der;
    }

    public void setDer(ArbolEmpresa der) {
        this.der = der;
    }

    public NodoEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    
    public NodoEmpresa(Empresa empresa, ArbolEmpresa izq, ArbolEmpresa der) {
        this.empresa = empresa;
        this.izq = izq;
        this.der = der;
    }
    public NodoEmpresa(){
        
    }
    

    
}
