import java.awt.Dimension;
import java.util.LinkedList;
import java.io.*;

import org.jgraph.JGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.*;


/**
 * Agente Inteligente
 * @author Equipo 6 ee
 */
public class Agente {
	protected Grafo graph;
	
	public static void main(String[] args) {
		Agente x = new Agente();
		
		Persona daniel = new Persona("Daniel");
		Persona cynthia = new Persona("Cynthia");
		Persona ricardo = new Persona("Ricardo");
		Persona angel = new Persona("Angel");
		Persona memo = new Persona ("Guillermo");
     	x.graph.g.addVertex(daniel);
		x.graph.g.addVertex(cynthia);
		x.graph.g.addVertex(ricardo);
		x.graph.g.addVertex(angel);
		x.graph.g.addEdge(daniel, cynthia);
		x.graph.g.addEdge(cynthia, ricardo);
		x.graph.g.addEdge(cynthia, angel);
		x.agregarPersona(memo);
		x.quitarPersona(daniel);
		x.eliminaConexion(cynthia,ricardo);
		x.graph.g.addEdge(cynthia, memo);
		System.out.println(cynthia.blToString());
		System.out.println(x.graph.g.toString());
		System.out.println(daniel.wlToString());
		
		try{
			guardaInformacion();
			}
			catch(IOException io){
				io.toString();
			}
	}
	public Agente(){ 
		this.graph = new Grafo();
		this.graph.display.setSize(new Dimension( 530, 320 ));
		
	}
	
	public void construyeGrafo(String filename){
		this.graph.construyeGrafo(filename);
	}
	
	public JGraph getGraph(){ //Esto no iria en grafo?
	       return this.graph.display;
	}
	
	public void getAttributePersons(){ //Que es esto?
		
	}
	
	public void buscarPersona(Persona p, Persona v, int nivel){ 
		realizaConexion(p,v);
		if(nivel==0)
			return;
		NeighborIndex ni = new NeighborIndex(this.graph.g);
		Object[] vecinos = ni.neighborsOf(p).toArray();
		for(Object vecino: vecinos){
			if(!((Persona)vecino).esAmigo(p)){ 
				//Solamente se hace la llamada recursiva, si no hay conexion entre las personas
				buscarPersona(p, (Persona)vecino, nivel-1);
			}
		}
		
	}
	
	public void buscarAmigos(Persona p){
		NeighborIndex ni = new NeighborIndex(this.graph.g);
		Object[] vecinos = ni.neighborsOf(p).toArray();
		for(Object vecino: vecinos){
			buscarPersona(p, (Persona)vecino, 3);
		}
	}
	
	public void agregarPersona(Persona p){ //Conecta a una nueva persona al resto del grafo
		int x;
		do{
			x = (int)(Math.random()*Persona.personas.size());
		}while(x==p.getId());
		Persona b = Persona.personas.get(x);
		p.agregaAmigo(b);
		b.agregaAmigo(p);
		this.graph.g.addVertex(p);
		this.graph.g.addEdge(p,b);	
	}
	
	public void quitarPersona(Persona p){
		Persona.personas.remove(p.getId());
		this.graph.g.removeVertex(p);
	}
	
	public void eliminaConexion(Persona a, Persona b){
		a.agregaEnemigo(b);
		a.removeAmigo(b);
		b.removeAmigo(a);
		this.graph.g.removeEdge(this.graph.g.getEdge(a,b));
	}
	
	public boolean realizaConexion(Persona a, Persona b){
		//se comparan los atributos y se modifica el grafo
		boolean sw = true;
		LinkedList<Persona.PersonaAtributo> aatrib = a.getAtributos();
		LinkedList<Persona.PersonaAtributo> batrib = b.getAtributos();
		int asize = aatrib.size();
		int bsize = batrib.size();
		for(int i=0; i<=asize && i <=bsize; i++){ //-- TODO Para todos los atributos en comun
			if(aatrib.get(i).getName().equals(batrib.get(i).getName())){
				sw = sw && true;
			}else
				sw = sw &&false;
		}
		if (sw && !a.esEnemigo(b) && !b.esEnemigo(a)){
			a.agregaAmigo(b);
			b.agregaAmigo(a);
			this.graph.g.addEdge(a,b);
			return true;
		}else
			return false;
	}
	
	public static void guardaInformacion() throws IOException{
		
		PrintWriter salida = new PrintWriter(new FileWriter("estadoAct.txt"));
		
		salida.println("Estado de la red");
		salida.print("\n");
		salida.println("Numero de nodos: "+Persona.personas.size()+"\n");
		
		for(int i=0; i<Persona.personas.size(); i++){
		      
			 salida.println("Informacion del nodo "+i);
			 salida.println("Nombre: "+Persona.personas.get(i).getNombre());
			 salida.println("Conexiones directas con "+Persona.personas.get(i).wlToString());
		     salida.println("Lista Negra: "+Persona.personas.get(i).blToString());
		     salida.println("Atributos:"+Persona.personas.get(i).atrToString());
		     salida.println("\n");
		}
		
		salida.close();
		
	}

}