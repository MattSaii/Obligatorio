package obligatorioalgoritmos;

import obligatorioalgoritmos.Retorno.Resultado;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArbolEmpresa {

    NodoEmpresa raiz;
    Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public Retorno agregarEmpresa(Empresa empresa) {
        NodoEmpresa nuevo = new NodoEmpresa(empresa);
        Retorno r = new Retorno(Resultado.OK);

        Matcher mather = pattern.matcher(empresa.geteMail_contacto());

        if (mather.find() == true) {
            if (raiz == null) {
                raiz = nuevo;
            } else {
                NodoEmpresa aux = raiz;
                NodoEmpresa ant = raiz;
                while (aux != null) {
                    ant = aux;
                    if (aux.empresa.getNombre().compareTo(empresa.getNombre()) < 1) {
                        aux = aux.der;
                    } else if (aux.empresa.getNombre().compareTo(empresa.getNombre()) == 0) {
                        r = new Retorno(Resultado.ERROR_2);
                    } else {
                        aux = aux.izq;
                    }
                }

                if (ant.empresa.getNombre().compareTo(empresa.getNombre()) < 1) {
                    ant.der = nuevo;
                } else if (aux.empresa.getNombre().compareTo(empresa.getNombre()) == 0) {
                    r = new Retorno(Resultado.ERROR_2);
                } else {
                    ant.izq = nuevo;
                }
            }

        } else {
            r = new Retorno(Resultado.ERROR_1);
        }
        return r;
    }
    public Empresa darEmpresa(String nombre) {
        return buscar(nombre, raiz);
    }

    public Empresa buscar(String llave, NodoEmpresa nodo) {
        if (nodo == null) {
            //fatalError("La llave " + llave + " no existe en el arreglo");
        } else if (nodo.getEmpresa().getNombre().compareTo(llave)==0) {
            return nodo.empresa;
        } else if (nodo.getEmpresa().getNombre().compareTo(llave)<1) {
            return buscar(llave, nodo.izq);
        } else {
            return buscar(llave, nodo.der);
        }
        return null; // Solo para evitar el error en compilacion
    }
    public boolean isEmpty(){
        return this.raiz == null;
    }
    
}
