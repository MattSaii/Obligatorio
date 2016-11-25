
package obligatorioalgoritmos;

public class NodoEmpresa {
    
    Empresa empresa;
    NodoEmpresa izq;
    NodoEmpresa der;
    

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public NodoEmpresa getIzq() {
        return izq;
    }

    public void setIzq(NodoEmpresa izq) {
        this.izq = izq;
    }

    public NodoEmpresa getDer() {
        return der;
    }

    public void setDer(NodoEmpresa der) {
        this.der = der;
    }

    public NodoEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    
    public NodoEmpresa(Empresa empresa, NodoEmpresa izq, NodoEmpresa der) {
        this.empresa = empresa;
        this.izq = izq;
        this.der = der;
    }
    public NodoEmpresa(){
        
    }
    

    
}
