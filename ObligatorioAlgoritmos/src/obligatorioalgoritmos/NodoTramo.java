/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorioalgoritmos;

/**
 *
 * @author user
 */
public class NodoTramo {
    int peso;
    Punto punto;
    NodoTramo nodoSig;

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Punto getPunto() {
        return punto;
    }

    public void setPunto(Punto punto) {
        this.punto = punto;
    }

    public NodoTramo getNodoSig() {
        return nodoSig;
    }

    public void setNodoSig(NodoTramo nodoSig) {
        this.nodoSig = nodoSig;
    }

    public NodoTramo() {
    }

    void getNodoSig(NodoTramo nuevoNodo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    
}
