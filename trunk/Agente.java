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
 * Agente Inteligente. 
 * El principal proposito de esta clase es buscar a los amigos
 * cuyo indice de probabilidad entre atributos al compararlos sea similar.
 * Asimismo proveer de una interfaz para la obtencion del grafo que se contiene en una variable
 * de esta clase.
 * @author Equipo 6 
 */
public class Agente {
	public Grafo graph; //-- Abstraccion de la libreria de jgraph
	public static Agente ag; //-- Singleton del Agente.
	
	//-- Constantes que determinan algunos criterios de poda al encontrar usuarios
	public static int 	NIVEL 				 = 2;
	public static int 	MIN_MATCH_ATTRIBUTES = 3;
	public static int 	PERSONS_FOUND_LIMIT	 = 3;
	public static int	ERROR_RANGE			 = 90;
	
	static Persona daniel ;	
	static Persona cynthia ;
	
	//-- Metodo temporal para agregar datos de prueba
	public static void defaults(Agente x){
		// Personas actuales
		Persona ricardo = new Persona("Ricardo");
		Persona angel 	= new Persona("Angel");
		Persona memo 	= new Persona("Guillermo");
		Persona pedro 	= new Persona("Pedro");
		Persona damaris = new Persona("Damaris");
		Persona leo 	= new Persona("Leonidas");
		Persona marin 	= new Persona("Marin");
		
		for(Persona persona : Persona.personas){
			persona.setAttribute("Comida Spicy", (float)(Math.random()*100));
			persona.setAttribute("Comida China",(float)(Math.random()*100));
			persona.setAttribute("Soccer", (float)(Math.random()*100));
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
	
	//-- Main
	public static void main(String[] args) {
		Agente.ag = new Agente();
		daniel = new Persona("Daniel");
		cynthia = new Persona("Cynthia");
		defaults(Agente.ag);
		System.out.println("Inicial: "+Agente.ag.graph.g.toString());
		//Agente.ag.buscarAmigos(Persona.personas.getFirst());
		Agente.ag.eliminaConexion(daniel, cynthia);
		System.out.println("Final: "+Agente.ag.graph.g.toString());
	}
	
	//-- Constructor
	public Agente(){ 
		this.graph = new Grafo(new Dimension( 530, 320 ));
	}
	
	//-- Guarda un grafo
	public void construyeGrafo(String filename){
		this.graph.construyeGrafo(filename);
	}
	
	//-- Trae la grafica, esto es solo una interfaz para no usar el acceso directo y encapuslar mas cosas aqui probablemente.
	public JGraph getGraph(){ 
	       return this.graph.display;
	}
	
	
	public void buscarPersona(Persona p, Persona v, int nivel){ 
		boolean result = realizaConexion(p,v);
		Persona[] vecinos = v.getAmigos();
		
		if(!result){
			//-- Si no hay conexion con el amigo, intentar un nivel mas a partir del amigo, para cersiorarse de 
			//-- que no haya un subgrupo con los mismos gustos.
			for(Persona vecino : vecinos){
				realizaConexion(p,vecino);
			}
		}
		
		//-- Ultimo nivel, regresar.
		if(nivel==0)
			return;
		
		//-- Buscar sobre los amigos.
		for(Persona vecino: vecinos){
			if(!vecino.esAmigo(p)){ //TODO colocar aqui la probabilidad de que se pueda encontrar algo.
				//Solamente se hace la llamada recursiva, si no hay conexion entre las personas
				buscarPersona(p, (Persona)vecino, nivel-1);
			}
		}
		
	}
	
	
	//-- Metodo que busca amigos con caracteristicas similares
	public void buscarAmigos(Persona p){
		NeighborIndex ni = new NeighborIndex(this.graph.g);
		Object[] vecinos = ni.neighborsOf(p).toArray();
		for(Object vecino: vecinos){
			buscarPersona(p, (Persona)vecino, Agente.NIVEL);
		}
	
	}
	

	//-- Realiza la conexion en base a las caracteristicasy pesos que tiene cada uno
	//-- funcion core donde se plasma el aumento de experiencia y creacion de relaciones
	//-- entre usuarios
	public boolean realizaConexion(Persona a, Persona b){
		//se comparan los atributos y se modifica el grafo
		boolean sw = false;
		if(a.equals(b)) return false;
		int common = 0;
		
		//-- Se busca sobre todos los atributos existentes, aquellos que tengan en comun
		//-- si tienen en comun esos entonces calcular probabilidad y hacer la conexion.
		for(Atributo base : Atributo.atributos){
			Persona.PersonaAtributo aatrib = a.getAtributo(base.getName());
			Persona.PersonaAtributo batrib = b.getAtributo(base.getName());
			if(aatrib != null && batrib != null){
				//-- Comparar peso de atributos con un rango de error.
				float aweight = aatrib.getWeight();
				float bweight = batrib.getWeight();
				float dif = Math.abs(bweight - aweight);
				if(dif <= Agente.ERROR_RANGE){
				//if((aweight-Agente.ERROR_RANGE <= bweight && aweight+Agente.ERROR_RANGE >= bweight) || 
				//		bweight-Agente.ERROR_RANGE <= aweight && bweight+Agente.ERROR_RANGE >= aweight){
					common++;
					
					//-- Recalcular probabilidad de obtener otro atributo comoe ste
				}
			}
		}
		
		if(common >= Agente.MIN_MATCH_ATTRIBUTES){
			a.agregaAmigo(b);
			b.agregaAmigo(a);
			this.graph.g.addEdge(a,b);
			return true;
		}
		return false;
	}
	
	
	//-- Agrega una persona al grafo
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
	
	//-- Quita una persona del grafo
	public void quitarPersona(Persona p){
		Persona.personas.remove(p.getId());
		this.graph.g.removeVertex(p);
	}
	
	//-- Elimina una conexion.
	public void eliminaConexion(Persona a, Persona b){
		a.agregaEnemigo(b);
		a.removeAmigo(b);
		b.removeAmigo(a);
		this.graph.g.removeEdge(this.graph.g.getEdge(a,b));
	}
	
	//-- Guarda la informacion en un archivo
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