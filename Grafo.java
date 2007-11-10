import java.awt.Dimension;

import org.jgraph.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

/**
 * Encapsulador del objeto de grafo y su grafica
 * @author daniel
 *
 */
public class Grafo {
	public ListenableGraph<Persona,DefaultEdge> g;
	public JGraphModelAdapter adapter = null;
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
		//-- Leer archivo
		
		//-- Agregar Personas
		//-- p.addPerson();
		//-- Agregar atributos a personas
		//-- p.addAttribute();
		
		//-- Agregar Relaciones
		//-- this.g.addEdge(p1, p2);
		
	}
	
}
