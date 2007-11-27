import java.awt.Dimension;

import org.jgraph.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

/**
 * Encapsulador del objeto de grafo y su grafica, 
 * TODO: Establecer un poco mas de abstraccion de datos en esta parte.
 * @author Equipo 6
 *
 */
public class Grafo {
	///-- Grafo que puede ser manipulado
	public ListenableGraph<Persona,DefaultEdge> g;
	
	//-- Adaptador de datos
	public JGraphModelAdapter<Persona,DefaultEdge> adapter = null;
	
	//-- Adaptador de display.
	public JGraph display;
	
	public Grafo(Dimension graphSize){
		this.g = new ListenableUndirectedWeightedGraph<Persona , DefaultEdge>(DefaultEdge.class);
		this.adapter = new JGraphModelAdapter<Persona,DefaultEdge>(this.g);
		this.display = new JGraph( this.adapter );
		this.display.setSize(graphSize);
	}
	
	public void exportaGrafo(){
		
	}
	
	public void construyeGrafo(String filename){
	
	}
	
}
