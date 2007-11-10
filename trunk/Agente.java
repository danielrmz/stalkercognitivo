import java.awt.Dimension;
import java.util.LinkedList;
import java.io.*;

import org.jgraph.JGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.*;

import sun.misc.Queue;


/**
 * Agente Inteligente
 * @author Equipo 6 
 */
public class Agente {
	public Grafo graph;
	public static Agente ag;
	public static int 	NIVEL 				 = 2;
	public static int 	MIN_MATCH_ATTRIBUTES = 4;
	public static LinkedList<Persona> buscados_actualmente = new LinkedList<Persona>();
	
	public static void defaults(Agente x){
		// Personas actuales
		Persona daniel 	= new Persona("Daniel");
		Persona cynthia = new Persona("Cynthia");
		Persona ricardo = new Persona("Ricardo");
		Persona angel 	= new Persona("Angel");
		Persona memo 	= new Persona("Guillermo");
		Persona pedro 	= new Persona("Pedro");
		Persona damaris = new Persona("Damaris");
		Persona leo 	= new Persona("Leonidas");
		Persona marin 	= new Persona("Marin");
		
		for(Persona persona : Persona.personas){
			persona.setAttribute("Comida Spicy", 20);
			persona.setAttribute("Comida China", 20);
			persona.setAttribute("Soccer", 10);
		}
		
		x.graph.g.addEdge(daniel, cynthia);
		x.graph.g.addEdge(cynthia, ricardo);
		x.graph.g.addEdge(cynthia, angel);
		x.graph.g.addEdge(daniel, memo);
		x.graph.g.addEdge(ricardo, pedro);
		x.graph.g.addEdge(angel, pedro);
		x.graph.g.addEdge(pedro, damaris);
		x.graph.g.addEdge(leo, marin);
		x.graph.g.addEdge(damaris,leo);
		x.graph.g.addEdge(pedro, leo);
		
	}
	
	public static void main(String[] args) {
		Agente.ag = new Agente();
		defaults(Agente.ag);
		System.out.println("Inicial: "+Agente.ag.graph.g.toString());
		//System.out.println(Persona.personas.getFirst().toString());
		Agente.ag.buscarAmigos(Persona.personas.getFirst());
		//Agente.ag.buscarAmigos(Persona.personas.getFirst());
		System.out.println("Final: "+Agente.ag.graph.g.toString());
	}
	
	public Agente(){ 
		this.graph = new Grafo(new Dimension( 530, 320 ));
	}
	
	public void construyeGrafo(String filename){
		this.graph.construyeGrafo(filename);
	}
	
	public JGraph getGraph(){ //Esto no iria en grafo?
	       return this.graph.display;
	}
	
	
	public void buscarPersona(Persona p, Persona v, int nivel){ 
		realizaConexion(p,v);
		if(nivel==0)
			return;
		NeighborIndex ni = new NeighborIndex(this.graph.g);
		Object[] vecinos = ni.neighborsOf(v).toArray();
		System.out.println("Vecinos de: \n"+v.toString());
		
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
			buscarPersona(p, (Persona)vecino, Agente.NIVEL);
		}
		Agente.buscados_actualmente = new LinkedList<Persona>(); //-- Borrarlos.
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
		boolean sw = false;
		if(a.equals(b)) return false;
		int common = 0;
		for(Atributo base : Atributo.atributos){
			Persona.PersonaAtributo aatrib = a.getAtributo(base.getName());
			Persona.PersonaAtributo batrib = b.getAtributo(base.getName());
			if(aatrib != null && batrib != null){
				common++;
				System.out.println(a.toString()+":"+b.toString()+"-"+common);
				//-- Comparar peso de atributos
			}
		}
		
		if(common >= Agente.MIN_MATCH_ATTRIBUTES){
			a.agregaAmigo(b);
			b.agregaAmigo(a);
			this.graph.g.addEdge(a,b);
			return true;
		}
		return false;
		/*
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
			*/
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