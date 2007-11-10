import java.io.*;
import java.util.*;

import org.jgrapht.alg.NeighborIndex;

/**
 * Encapsuladora de los datos de una persona
 * @author Equipo 6
 */
public class Persona {
	
	//-- Listado de Personas en el sistema
	public static LinkedList<Persona> personas = new LinkedList<Persona>();
	
	//-- Id de la perosna
	private int id = 0;
	
	//-- NOmbre de la persona
	private String nombre = "";
	
	//-- Atributos con los que cuenta
	private LinkedList<PersonaAtributo> atributos = new LinkedList<PersonaAtributo>();
	
	//-- Listas de conexiones personalizadas, aparte de las del grafo. para manipulacion interna.
	private LinkedList<Persona> blacklist = new LinkedList<Persona>();
	private LinkedList<Persona> conexiones = new LinkedList<Persona>();
	
	
	//-- Constructor
	public Persona(String nombre){
		this.nombre = nombre;
		Persona.personas.addLast(this);
		this.id = Persona.personas.indexOf(this);
		Agente.ag.graph.g.addVertex(this);
	}
	
	//-----
	//-- Funciones de Exists
	//-----
	public boolean esEnemigo(Persona p){
		for(Persona a : this.blacklist){
			if(a.equals(p)){
				return true;
			}
		}
		return false;
	}
	
	public boolean esAmigo(Persona p){
		for(Persona a : this.conexiones){
			if(a.equals(p)){
				return true;
			}
		}
		return false;
	}
	
	//---- 
	//-- Funciones de Output
	//----
	public String blToString(){
	    String elem="";
	    
		 for(int i=0; i<blacklist.size(); i++){
		   elem+=blacklist.get(i)+", ";
		 }
		 
		 return elem;
	}
	
	public String wlToString(){
	    String elem="";
	    
		 for(int i=0; i<conexiones.size(); i++){
		   elem+=conexiones.get(i)+", ";
		 }
		 
		 return elem;
	}
	
	public String atrToString(){
	    String elem="";
	    
		 for(int i=0; i<atributos.size(); i++){
		   elem+=atributos.get(i)+", ";
		 }
		 
		 return elem;
	}
	
	//-----
	//-- Funciones de updates
	//-----
	
	public void agregaEnemigo(Persona enemigo){
		if (blacklist.size()==0){
			
			blacklist.addFirst(enemigo);
		}
		else{
		blacklist.add(enemigo);
		}
	}
	
	public void removeEnemigo(Persona enemigo){
		blacklist.remove(enemigo);
	}
	
	public Persona getEnemigo(int id){
		return this.blacklist.get(id);
	}
	
	public void agregaAmigo(Persona amigo){
		if(conexiones.size()==0){
		   conexiones.addFirst(amigo);
		}
		else{
			conexiones.add(amigo);
		}
	}
	
	public void removeAmigo(Persona amigo){
		conexiones.remove(amigo);
	}
	
	
	//----
	//-- Funciones de gets.
	//----
	public Persona getAmigo(int id){
		return this.conexiones.get(id);
	}
	
	public LinkedList<PersonaAtributo> getAtributos(){
		return this.atributos;
	}
	
	public int getId(){
		return id;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public PersonaAtributo getAtributo(String attribute){
		for(PersonaAtributo atributo : this.atributos ){
			if(atributo.getName().equals(attribute)){
				
				return atributo;
			}
		}
		return null;
	}
	
	
	public void setAttribute(String attrib, float weight){
		PersonaAtributo at = new PersonaAtributo(attrib, weight);
		this.atributos.add(at);
	}
	
	public String toString(){
		String attribs = "";
		for(PersonaAtributo atrib : this.atributos){
			attribs+=atrib.toString();
		}
		
		//return this.nombre+"\n"+attribs+"-----------------";
		return this.nombre;
		
	}
	
	public Persona[] getAmigos(){
		NeighborIndex ni = new NeighborIndex(Agente.ag.graph.g);
		Object[] vecinos = ni.neighborsOf(this).toArray();
		int i = 0;
		Persona[] amigos = new Persona[vecinos.length];
		for(Object persona : vecinos){
			amigos[i] = (Persona)persona;
			i++;
		}
		return amigos;
	}
	
	/***
	 * Clase del Atributo de una persona, con detalles mas especificos que el atributo general
	 * tales como la probabilidad de que aparezca, y el peso que tiene el usuario sobre
	 * un determinado atributo
	 */
	public class PersonaAtributo extends Atributo {
		private float weight = 0;
		/**
		 * Probabilidad de popularidad que ha salido entre los amigos.
		 */
		private float popularity = 0;
		
		public PersonaAtributo(String name, float weight){
			this.name = name;
			this.weight = weight;
			if(!super.exists(name)){
				new Atributo(name);
			}
		}
		
		public void setPopularity(float pop){
			this.popularity = pop;
		}
		
		public float getPopularity(){
			return this.popularity;
		}
		
		public float getWeight(){
			return this.weight;
		}
		
		public void setWeight(float weight){
			this.weight = weight;
		}
		
		public Atributo getInformation(){
			for(Atributo atributo :  Atributo.atributos){
				if(atributo.getName().equals(this.getName())){
					return atributo;
				}
			}
			return null;
		}
		
		public String toString(){
			return "Atributo: "+this.name+", peso: "+this.weight+", probabilidad: "+this.popularity+"\n";
		}
	}
	
}
