package obligatorioalgoritmos;

import java.util.ArrayList;
import obligatorioalgoritmos.Retorno.Resultado;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArbolEmpresa {

    NodoEmpresa raiz;
    Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public void ArbolEmpresa() {
        NodoEmpresa raiz = new NodoEmpresa();
    }

    public Retorno agregarEmpresa(Empresa empresa) {

        Retorno r = new Retorno(Resultado.OK);

        Matcher mather = pattern.matcher(empresa.geteMail_contacto());

        if (mather.find() == true) {
            if (raiz == null) {
                NodoEmpresa nuevo = new NodoEmpresa(empresa);
                nuevo.empresa = empresa;
                nuevo.der = new ArbolEmpresa();
                nuevo.izq = new ArbolEmpresa();
                raiz = nuevo;

            } else {
                if (raiz.empresa.getNombre().compareTo(empresa.getNombre()) < 1) {
                    (raiz.der).agregarEmpresa(empresa);
                } else if (raiz.empresa.getNombre().compareTo(empresa.getNombre()) == 0) {
                    r = new Retorno(Resultado.ERROR_2);
                } else {
                    (raiz.izq).agregarEmpresa(empresa);
                }
            }

        } else {
            r = new Retorno(Resultado.ERROR_1);
        }
        return r;
    }

    public ArbolEmpresa buscarEmpresa(String llave) {
        ArbolEmpresa arbol = null;
        if (raiz != null) {
            if (raiz.getEmpresa().getNombre().compareTo(llave) == 0) {
                return this;
            }
            else {
                if (raiz.getEmpresa().getNombre().compareTo(llave) < 1) {
                    arbol = raiz.der.buscarEmpresa(llave);
                } else {
                    arbol = raiz.izq.buscarEmpresa(llave);
                }
                return null; // Solo para evitar el error en compilacion
            }
        }
        return arbol;
    }

    public boolean isEmpty() {
        return this.raiz == null;
    }

    public Retorno imprimirInOrder() {
        Retorno r = new Retorno(Resultado.OK);
        if (raiz != null) {
            raiz.izq.imprimirInOrder();
            System.out.println(raiz.empresa.getNombre() + " " + raiz.getEmpresa().geteMail_contacto());
            raiz.izq.imprimirInOrder();
        }
        return r;
    }
<<<<<<< HEAD
    
}
   
    
    
    
    
    
    

=======

}
>>>>>>> 6da219f26245db5bd73c484d0d9315348e583fb5
