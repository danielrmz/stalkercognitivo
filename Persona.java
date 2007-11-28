import java.awt.Dimension;
import java.io.*;
import java.util.*;

import org.jgrapht.alg.NeighborIndex;

/**
 * Encapsuladora de los datos de una persona
 * @author Equipo 6 Cynthia Treviño, Ricardo Magallanes, Daniel Ramirez
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
	
	public int x = 0;
	public int y = 0;
	
	//-- Constructor
	public Persona(String nombre){
		this.nombre = nombre;
		Persona.personas.addLast(this);
		this.id = Persona.personas.indexOf(this);
		Main.getAgente().getGrafo().addVertex(this);
		Main.getInterfaz().addElementToList(this);
		Dimension available = Main.getInterfaz().getGraphSize();
		int x = 1;
		int y = 1;
		
		while(!pointOk(x,y) || x <= 0 || y >= available.height || x + 100>= available.width){
			x = (int)((float)(Math.random()*available.width-200));
			y = (int)((float)(Math.random()*available.height));
		}
		Main.getAgente()._getGrafo().positionVertexAt(this, x, y);
	}
	
	public static boolean pointOk(int x, int y){
		for(Persona p : Persona.personas){
			if(x >= p.x && x <= p.x + 150 && y >= p.y && y <= p.y+40){
				return false;
			}
		}
		return true;
	}
	
	public static Persona getPersona(int id){
		for(Persona x : personas){
			if(x.getId() == id){
				return x;
			}
		}
		return null;
	}

	public static Persona getPersona(String nombre){
		for(Persona x : personas){
			if(x.getNombre().equals(nombre)){
				return x;
			}
		}
		return null;
	}

	
	public static Persona[] getPersonas(){
		Object[] personasOBJ = Persona.personas.toArray();
		Persona[] y = new Persona[personasOBJ.length];
		int i = 0;
		for(Object z : personasOBJ){
			y[i] = (Persona)z;
			i++;
		}
		return y;
	}
	
	public Object[][] getAtributosTable(){
		String[][] ats = new String[this.atributos.size()][3];
		int i = 0;
		for(PersonaAtributo a : this.atributos){
			ats[i][0] = a.getName();
			ats[i][1] = a.getLastWeight()+"";
			ats[i][2] = a.getWeight()+"";
			i++;
		}
		return ats;
	}
	
	
	//-----
	//-- Funciones de Exists
	//-----
	public boolean esEnemigo(Persona p){
		for(Persona a : this.blacklist){
			if(a!= null && p != null && a.equals(p)){
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
		   elem+=blacklist.get(i)+",";
		 }
		 
		 return elem;
	}
	
	public String wlToString(){
	    String elem="";
	    
		 for(int i=0; i<conexiones.size(); i++){
		   elem+=conexiones.get(i)+",";
		 }
		 
		 return elem;
	}
	
	public String atrToString(){
	    String elem="";
	    
		 for(int i=0; i<atributos.size(); i++){
		   elem+=atributos.get(i)+",";
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
		
		
       
		conexiones.add(amigo);

		Main.getAgente().getGrafo().addEdge(this,amigo);

		
      //  System.out.println("Agregue a "+amigo.getNombre()+" a la lista "+this.getNombre());

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
	
	public Persona[] getEnemigos(){
		Persona[] x = new Persona[this.blacklist.size()];
		int i = 0;
		for(Object p : this.blacklist){
			x[i] = (Persona)p;
			i++;
		}
		return x;
	}
	
	public Persona[] getAmigos(){
		NeighborIndex ni = new NeighborIndex(Main.getAgente().getGrafo());
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
		private float weight 	  = 0;
		private float last_weight = 0;
		private float popularity = 0;
		
		public PersonaAtributo(String name, float weight){
			this.name 		 = name;
			this.weight 	 = weight;
			this.last_weight = weight;
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
		
		public float getLastWeight(){
			return this.last_weight;
		}
		
		public void setWeight(float weight){
			this.last_weight = this.weight;
			this.weight 	 = weight;
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
			return (""+this.name+","+this.weight+","+this.popularity);
		}
		
	
	}
	
}
