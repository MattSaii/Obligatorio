package obligatorioalgoritmos;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import obligatorioalgoritmos.Retorno.Resultado;
import org.omg.CORBA.DATA_CONVERSION;

public class Sistema implements ISistema {

    private Punto[] Puntos;
    private ArbolEmpresa arbolE = new ArbolEmpresa();
    private Retorno retorno;
    private NodoTramo[] tramos;

    @Override
    public Retorno inicializarSistema(int cantPuntos) {
        if (cantPuntos <= 0) {
            return new Retorno(Resultado.ERROR_1);
        } else {
            Puntos = new Punto[cantPuntos];
            tramos = new NodoTramo[cantPuntos];
            arbolE= new ArbolEmpresa();
            return new Retorno(Resultado.OK);
        }
    }

    @Override
    public Retorno destruirSistema() {
        Puntos = null;
        System.gc();
        return new Retorno(Resultado.OK);
    }
    public Punto[] getCantPuntos(){
        return Puntos;
    }

    @Override
    public Retorno registrarEmpresa(String nombre, String direccion, String pais, String email_contacto, String color) {
        Empresa miEmpresa = new Empresa();

        miEmpresa.setNombre(nombre);
        miEmpresa.setDireccion(direccion);
        miEmpresa.setPais(pais);
        miEmpresa.seteMail_contacto(email_contacto);
        miEmpresa.setColor(color);
        
        retorno=arbolE.agregarEmpresa(miEmpresa);
        return retorno;

    }

    @Override
    public Retorno registrarCiudad(String nombre, Double coordX, Double coordY) {
            Ciudad ciudad = new Ciudad();
            boolean Estado = false;         
            for(int i=0; i <= ((Puntos.length)-1);i++)
            {   
                if(Puntos[i] != null)
                {
                  if(Puntos[i].getX() == coordX && Puntos[i].getY()==coordY)
                  {
                      return new Retorno(Resultado.ERROR_2);
                  }
                }
                else
                {
                Puntos[i] = ciudad;
                NodoTramo n = new NodoTramo();
                n.punto = ciudad;
                tramos[i] = n;
                i = Puntos.length;
                Estado = true;
                }
            }
            if(Estado)
            {
                return new Retorno(Resultado.OK);
            }
            else
            {
               return new Retorno(Resultado.ERROR_1); 
            }   
    }
    
    public Ciudad darCiudad(double x, double y ){
        int i;
        Ciudad c = new Ciudad();
        for (i = 0; i <=(Puntos.length -1); i++) {
            if(Puntos[i] != null){
                if(Puntos[i] instanceof Ciudad){
                    if(Puntos[i].getX() == x && Puntos[i].getY() == y){
                        c = (Ciudad) Puntos[i];
                    }
                }
            }
        }
        return c;
    }
    public DataCenter darDataCenter(double x, double y ){
        int i;
        DataCenter d = new DataCenter();
        for (i = 0; i <=(Puntos.length -1); i++) {
            if(Puntos[i] != null){
                if(Puntos[i] instanceof DataCenter){
                    if(Puntos[i].getX() == x && Puntos[i].getY() == y){
                        d = (DataCenter) Puntos[i];
                    }
                }
            }
        }
        return d;
    }
    
    @Override
    public Retorno registrarDC(String nombre, Double coordX, Double coordY, String empresa, int capacidadCPUenHoras, int costoCPUporHora) {

        DataCenter miDC = new DataCenter();
        miDC.setNombre(nombre);
        miDC.setX(coordX);
        miDC.setY(coordY);

        miDC.setEmpresaPropietaria(arbolE.buscarEmpresa(empresa).raiz.getEmpresa());
        miDC.setCapacidadCPUenHoras(capacidadCPUenHoras);
        miDC.setCostoCPUxHora(costoCPUporHora);

        if (Puntos[Puntos.length - 1] == null) {
            for (int i = 0; i <= (Puntos.length - 1); i++) {
                if (Puntos[i] != null) {
                    if (Puntos[i].getX() == coordX && Puntos[i].getY() == coordY) {
                        retorno = new Retorno(Resultado.ERROR_3);
                    }
                }
                if (Puntos[i] == null) {
                    if (miDC.getEmpresaPropietaria() == null) {
                        retorno = new Retorno(Resultado.ERROR_4);
                    } else {
                        if (miDC.getCapacidadCPUenHoras() <= 0) {
                            retorno = new Retorno(Resultado.ERROR_2);
                        } else {
                            Puntos[i] = miDC;
                            retorno = new Retorno(Resultado.OK);
                        }
                    }
                }
            }
        } else {
            retorno = new Retorno(Resultado.ERROR_1);
        }
        return retorno;
    }

    @Override
    public Retorno registrarTramo(Double coordXi, Double coordYi,
            Double coordXf, Double coordYf, int peso) {
        if (peso <= 0) {
            retorno = new Retorno(Resultado.ERROR_1);
        } else if (!checkPuntos(coordXi, coordYi) || !checkPuntos(coordXf, coordYf)) {
            retorno = new Retorno(Resultado.ERROR_2);
        } else if (checkTramo(coordXi, coordYi, coordXf, coordYf)) {
            retorno = new Retorno(Resultado.ERROR_3);
        } else {
            int pos = darPosXCoord(coordXi, coordYi);
            NodoTramo nodo = new NodoTramo();
            nodo.setPeso(peso);
            nodo.setPunto(darPuntoXCoord(coordXf, coordYf));
            if (tramos[pos] != null) {
                NodoTramo aux = tramos[pos];
                while (aux.getNodoSig() != null) {
                    aux = aux.getNodoSig();
                }
                aux.setNodoSig(nodo);

            } else {
                tramos[pos] = nodo;
            }
            //Vice
            int posVice = darPosXCoord(coordXf, coordYf);
            NodoTramo nodoVice = new NodoTramo();
            nodoVice.setPeso(peso);
            nodoVice.setPunto(darPuntoXCoord(coordXi, coordYi));
            if (tramos[posVice] != null) {
                NodoTramo aux = tramos[posVice];
                while (aux.getNodoSig() != null) {
                    aux = aux.getNodoSig();
                }
                aux.setNodoSig(nodoVice);

            } else {
                tramos[posVice] = nodoVice;
            }
            return retorno = new Retorno(Resultado.OK);
        }

        // TODO Auto-generated method stub
        return new Retorno(Resultado.NO_IMPLEMENTADA);
    }

    public Punto darPuntoXCoord(Double x, Double y) {
        int i = 0;
        Punto punto = null;
        while (i < Puntos.length) {
            if (Puntos[i].getX() == x && Puntos[i].getY() == y) {
                punto = Puntos[i];
                i = Puntos.length;
            }
            i++;
        }
        return punto;
    }

    public int darPosXCoord(Double x, Double y) {
        int i = 0;
        int pos = -1;
        while (i < Puntos.length) {
            if (Puntos[i].getX() == x && Puntos[i].getY() == y) {
                pos = i;
                i = Puntos.length;
            }
            i++;
        }
        return pos;
    }

    public boolean checkPuntos(Double x, Double y) {
        int i = 0;
        boolean esta = false;
        while (i < Puntos.length) {
            if (Puntos[i].getX() == x && Puntos[i].getY() == y) {
                esta = true;
                i = Puntos.length;
            }
            i++;
        }
        return esta;
    }

    public boolean checkTramo(Double Xi, Double Yi, Double Xf, Double Yf) {
        int pos = darPosXCoord(Xi, Yi);
        boolean esta = false;
        if (tramos[pos] != null) {
            NodoTramo aux = tramos[pos];
            while (aux.getNodoSig() != null && !esta) {
                if (aux.getPunto().getX() == Xf && aux.getPunto().getY() == Yf) {
                    esta = true;
                }
                aux = aux.getNodoSig();
            }
            if (aux.getPunto().getX() == Xf && aux.getPunto().getY() == Yf) {
                esta = true;
            }

        }

        return esta;
    }

    @Override
    public Retorno eliminarTramo(Double coordXi, Double coordYi,
            Double coordXf, Double coordYf) {
      //Recorre array de tramo y de puntos 
        //recorrer los nodosSig
         if(!checkPuntos(coordXi, coordYi) || !checkPuntos(coordXf, coordYf)){
            return new Retorno(Resultado.ERROR_1);
        }
        else if(!checkTramo(coordXi, coordYi, coordXf, coordYf) || !checkTramo(coordXf, coordYf, coordXi, coordYi)){
            // No hay tramos registrados
            return new Retorno(Resultado.ERROR_2);
        }
        else{
            int pos = darPosXCoord(coordXi, coordYi);
            NodoTramo aux = tramos[pos];
            NodoTramo auxAnterior = aux;
            boolean seguir = true;

            Punto punto = aux.getPunto();
            
            if(punto.getX()== coordXf && punto.getY()== coordYf){
                tramos[pos] = aux.getNodoSig();
            }
            else{
                while(aux.getNodoSig()!= null && seguir){
                    punto = aux.getPunto();
                    if(punto.getX()== coordXf && punto.getY()== coordYf){
                        auxAnterior.setNodoSig(aux.getNodoSig());
                        seguir = false;
                    }
                    auxAnterior = aux;
                    aux = aux.getNodoSig();
                }
                if(seguir){
                    punto = aux.getPunto();
                    if(punto.getX()== coordXf && punto.getY()== coordYf){
                        auxAnterior.setNodoSig(null);
                    }
                }
            }

            //------------Viceversa--------------

            int posVice = darPosXCoord(coordXf, coordYf);
            NodoTramo auxVice = tramos[posVice];
            NodoTramo auxAnteriorVice = auxVice;
            boolean seguirVice = true;

            Punto puntoVice = auxVice.getPunto();
            
            if(puntoVice.getX()== coordXi && puntoVice.getY()== coordYi){
                tramos[posVice] = auxVice.getNodoSig();
            }
            else{
                while(auxVice.getNodoSig()!= null && seguirVice){
                    puntoVice = auxVice.getPunto();
                    if(puntoVice.getX()== coordXi && puntoVice.getY()== coordYi){
                        auxAnteriorVice.setNodoSig(auxVice.getNodoSig());
                        seguirVice = false;
                    }
                    auxAnteriorVice = auxVice;
                    auxVice = auxVice.getNodoSig();
                }
                if(seguirVice){
                    puntoVice = auxVice.getPunto();
                    if(puntoVice.getX()== coordXi && puntoVice.getY()== coordYi){
                        auxAnteriorVice.setNodoSig(null);
                    }
                }
            }

            return new Retorno(Resultado.OK);
        }
    }

    @Override
    public Retorno eliminarPunto(Double coordX, Double coordY) {
  Retorno r = new Retorno(Resultado.ERROR_1);
        boolean estado = true;
        int i;
            int pos = darPosXCoord(coordX, coordY);
            NodoTramo tramo = tramos[pos];
            
            while(tramo.getNodoSig()!= null){
                Punto punto = tramo.getPunto();
                eliminarTramo(punto.getX(), punto.getY(), coordX, coordY);
                tramo = tramo.getNodoSig();
            }
            Punto punto = tramo.getPunto();
            eliminarTramo(punto.getX(), punto.getY(), coordX, coordY);

            tramos[pos] = null;
            
        for(i=0; i <= (Puntos.length -1) ;i++)
        {
            if(Puntos[i] != null)
            {
             if(Puntos[i].getX()== coordX && Puntos[i].getY()== coordY)
             {  
                Puntos[i] = Puntos[i + 1];
                r = new Retorno(Resultado.OK);
                estado = false;
             }
             if(!estado)
             {
                 Puntos[i] = Puntos[i + 1];
             }
            }
        }
        return r;        
    }

    @Override
    public Retorno mapaEstado() {
        // TODO Auto-generated method stub
        if(Puntos != null){
            String defaultUrl = "http://maps.google.com/maps/api/staticmap?center=Golfo+de+Guinea&zoom=1&size=512x512&maptype=roadmap";
            String pipe = "%7C";    // |

            for(Punto p : Puntos){
                if(p != null){
                    String color;
                    if(p instanceof DataCenter){
                        DataCenter dc = (DataCenter)p;
                        Empresa empresa = arbolE.buscarEmpresa(dc.getEmpresaPropietaria().getNombre()).raiz.getEmpresa();

                        color = String.format("%s", empresa.getColor());
                    }
                    else{
                        color = String.format("%s", "yellow");

                        // Preguntar si DC deben tener el color de la empresa. YA ESTA HECHO
                    }
                    String x = String.format("%.6f", p.getX());
                    String y = String.format("%.6f",  p.getY());

                    String result = "&markers=color:" + color + pipe + x + "," + y;

                    defaultUrl += result;
                }
            }

            try {
                Desktop.getDesktop().browse(new URL(defaultUrl).toURI());
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Retorno(Resultado.OK);
    }

    @Override
    public Retorno procesarInformacion(Double coordX, Double coordY,
            int esfuerzoCPUrequeridoEnHoras) {
        // TODO Auto-generated method stub
        return new Retorno(Resultado.NO_IMPLEMENTADA);
    }

    @Override
    public Retorno listadoRedMinima() {
        // TODO Auto-generated method stub
        return new Retorno(Resultado.NO_IMPLEMENTADA);
    }

    @Override
    public Retorno listadoEmpresas() {
        // TODO Auto-generated method stub
        return arbolE.imprimirInOrder();
    }
    public String listadoComboBoxEmpresa() {
            StringBuilder string = new StringBuilder();
            return arbolE.listadoComboBoxEmpresa(string).toString();
	}
    @Override
    public String magico(){
        return arbolE.listarEmpresa();
    
    }
}
