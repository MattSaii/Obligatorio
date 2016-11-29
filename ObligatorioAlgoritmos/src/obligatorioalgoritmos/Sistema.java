package obligatorioalgoritmos;

import obligatorioalgoritmos.Retorno.Resultado;

public class Sistema implements ISistema {

    private Punto[] Puntos;
    private ArbolEmpresa arbolE;
    private Retorno retorno;
    private NodoTramo[] tramos;

    @Override
    public Retorno inicializarSistema(int cantPuntos) {
        if (cantPuntos <= 0) {
            return new Retorno(Resultado.ERROR_1);
        } else {
            Puntos = new Punto[cantPuntos];
            tramos = new NodoTramo[cantPuntos];
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
    public Retorno registrarDC(String nombre, Double coordX, Double coordY, String empresa, int capacidadCPUenHoras, int costoCPUporHora) {

        DataCenter miDC = new DataCenter();
        miDC.setNombre(nombre);
        miDC.setX(coordX);
        miDC.setY(coordY);

        miDC.setEmpresaPropietaria(arbolE.darEmpresa(empresa));
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
