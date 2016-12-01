package obligatorioalgoritmos;

// Interfaz del sistema
// No modificar esta clase!!!!!!!!!
public interface ISistema {

	Retorno inicializarSistema (int cantPuntos);
	
	Retorno destruirSistema();
	
	Retorno registrarEmpresa(String nombre, String direccion, String
			pais, String email_contacto, String color);
	
	Retorno registrarCiudad(String nombre, Double coordX, Double
			coordY);
	
	Retorno registrarDC(String nombre, Double coordX, Double coordY,
			String empresa, int capacidadCPUenHoras, int costoCPUporHora);
	
	Retorno eliminarTramo(Double coordXi, Double coordYi, Double
			coordXf, Double coordYf);
        Punto PuntoXposicion(int i);
        
	Retorno eliminarPunto(Double coordX, Double coordY);
	
	Retorno mapaEstado();
	
	Retorno procesarInformacion (Double coordX, Double coordY, int
			esfuerzoCPUrequeridoEnHoras);
	
	Retorno listadoRedMinima();
	
	Retorno listadoEmpresas();

    public Object magico();

    public Ciudad darCiudad(double x, double y);

    public Punto[] getCantPuntos();

    public String listadoComboBoxEmpresa();

    public DataCenter darDataCenter(double x, double y);

    public Retorno registrarTramo(int origen, int destino, int parseInt);
	
	
	
}
