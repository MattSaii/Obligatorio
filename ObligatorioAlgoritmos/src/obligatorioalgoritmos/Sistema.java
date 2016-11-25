package obligatorioalgoritmos;

import obligatorioalgoritmos.Retorno.Resultado;

public class Sistema implements ISistema {

    private Punto[] Puntos;
    private ArbolEmpresa arbolE;
    private Retorno retorno;

    @Override
    public Retorno inicializarSistema(int cantPuntos) {
        if (cantPuntos <= 0) {
            return new Retorno(Resultado.ERROR_1);
        } else {
            Puntos = new Punto[cantPuntos];
            return new Retorno(Resultado.OK);
        }
    }

    @Override
    public Retorno destruirSistema() {
        Puntos = null;
        System.gc();
        return new Retorno(Resultado.OK);
    }

    @Override
    public Retorno registrarEmpresa(String nombre, String direccion, String pais, String email_contacto, String color) {
        Empresa miEmpresa = new Empresa();

        miEmpresa.setNombre(nombre);
        miEmpresa.setDireccion(direccion);
        miEmpresa.setPais(pais);
        miEmpresa.seteMail_contacto(email_contacto);
        miEmpresa.setColor(color);

        return arbolE.agregarEmpresa(miEmpresa);

    }

    @Override
    public Retorno registrarCiudad(String nombre, Double coordX, Double coordY) {

        Ciudad miCiudad = new Ciudad();
        miCiudad.setNombre(nombre);
        miCiudad.setX(coordX);
        miCiudad.setY(coordY);
        if (Puntos[Puntos.length - 1] == null) {
            for (int i = 0; i > Puntos.length - 1; i++) {
                if (Puntos[i] == null) {
                    Puntos[i] = miCiudad;
                    i = Puntos.length;
                    retorno = new Retorno(Resultado.OK);
                }
                if (Puntos[i] != null) {
                    if (Puntos[i].getX() == coordX && Puntos[i].getY() == coordY) {
                        i = Puntos.length;
                        retorno = new Retorno(Resultado.ERROR_2);
                    }
                }

            }
        } else {
            retorno = new Retorno(Resultado.ERROR_1);
        }
        return retorno;

    }
    @Override
    public Retorno registrarDC(String nombre, Double coordX, Double coordY,String empresa, int capacidadCPUenHoras, int costoCPUporHora) {
        
        DataCenter miDC = new DataCenter();
        miDC.setNombre(nombre);
        miDC.setX(coordX);
        miDC.setY(coordY);
        
        miDC.setEmpresaPropietaria(arbolE.darEmpresa(empresa));
        miDC.setCapacidadCPUenHoras(capacidadCPUenHoras);
        miDC.setCostoCPUxHora(costoCPUporHora);
        
        
        
        return retorno;
    }
    public int obtenerUltimoPunto(){
    	for(i=0; i <= (Puntos.length -1) ;i++)
        {
            if(Puntos[i] != null)
            {
                if(Puntos[i].getCoordX() == coordX && Puntos[i].getCoordY()== coordY)
                {
                    return new Retorno(Resultado.ERROR_3);
                }
            }
            else
            {
                Puntos[i] = dc;
                i = Puntos.length;
                Estado = true;
            }
        }
    	
    }
    @Override
    public Retorno registrarTramo(Double coordXi, Double coordYi,
            Double coordXf, Double coordYf, int peso) {
        // TODO Auto-generated method stub
        return new Retorno(Resultado.NO_IMPLEMENTADA);
    }

    @Override
    public Retorno eliminarTramo(Double coordXi, Double coordYi,
            Double coordXf, Double coordYf) {
        // TODO Auto-generated method stub
        return new Retorno(Resultado.NO_IMPLEMENTADA);
    }

    @Override
    public Retorno eliminarPunto(Double coordX, Double coordY) {
        // TODO Auto-generated method stub
        return new Retorno(Resultado.NO_IMPLEMENTADA);
    }

    @Override
    public Retorno mapaEstado() {
        // TODO Auto-generated method stub
        return new Retorno(Resultado.NO_IMPLEMENTADA);
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
        return new Retorno(Resultado.NO_IMPLEMENTADA);
    }

}
