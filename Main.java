import org.jgraph.JGraph;

/**
 * Clase principal de la cual se va a ejecutar el programa
 * 
 * @author Equipo 6 Cynthia Treviño, Ricardo Magallanes, Daniel Ramirez
 */
public class Main {
	/**
	 * Singleton de la Interfaz
	 */
	private static Interfaz ie; //-- Singleton de la Interfaz
	
	/**
	 * Singleton del Agente
	 */
	private static Agente 	ae; //-- Singleton del Agente
	
	
	public static Agente getAgente(){
		return Main.ae;
	}
	
	public static Interfaz getInterfaz(){
		return Main.ie;
	}
	
	public static void main(String[] args) {
		ie = new Interfaz();
		ae = new Agente();
		
		Agente.defaults(ae);
		
		ie.setVisible(true);
		ie.setGraphDisplay(ae.getDisplay());
	

		
	}

}
