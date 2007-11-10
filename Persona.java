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
	
	private int id = 0;
	private String nombre = "";
	private LinkedList<PersonaAtributo> atributos = new LinkedList<PersonaAtributo>();
	private LinkedList<Persona> blacklist = new LinkedList<Persona>();
	private LinkedList<Persona> conexiones = new LinkedList<Persona>();
	
	public Persona(String nombre){
		this.nombre = nombre;
		Persona.personas.addLast(this);
		this.id = Persona.personas.indexOf(this);
		Agente.ag.graph.g.addVertex(this);
	}
	
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
/**
	public void cargaDatos(String nombre) throws Exception {//abre metodo

		BufferedReader ent = new BufferedReader (new FileReader(nombre));
//	 BufferedReader def = new BufferedReader (new FileReader("def.txt"));	
//		BufferedReader atr = new BufferedReader (new FileReader("attr.txt"));
		
		
		String x=ent.readLine();
				while(x!=null){//cierra while
				  
				  switch(countLines){//abre switch			  
				   case 1: 
				   BufferedReader def = new BufferedReader (new FileReader("nodos.txt"));	
					String y=def.readLine();
						while(y!=null) { //abre while
						
							if(x.charAt(0)==y.charAt(0)){
								setNombre(y.substring(2, y.length()));
								y=def.readLine();
								countLines++;					
														}
							else{
							  	y=def.readLine();
							}
							
				  
				  } //cierra while
				  def.close();
				  	
				  
				  break;
				  case 2:
				  
				  StringTokenizer st=new StringTokenizer(x,"|");
				         int i=0;
				  			while(st.hasMoreTokens()){
					     	
							if(i==0){
							  cDirecta.addFirst(st.nextToken());
							  i++;
							}
						   else{
							  cDirecta.add(i, st.nextToken());
							  i++;
							  }
								 
						}
						countLines++;
						
						break;
				case 3:
				 StringTokenizer st2=new StringTokenizer(x,"|");
				         int z=0;
				  			while(st2.hasMoreTokens()){
					     	
							if(z==0){
							  blacklist.addFirst(st2.nextToken());
							  z++;
							}
						   else{
							  blacklist.add(z, st2.nextToken());
							  z++;
							  }
								 
						}
						countLines++;
						x=ent.readLine();
						break;

			   } //cierra switch
				x=ent.readLine();   
			}//cierra while
	      ent.close();
		}//cierra metodo
	**/
	
	/***
	 * Clase del Atributo de una persona
	 */
	public class PersonaAtributo extends Atributo {
		private float weight = 0;
		
		public PersonaAtributo(String name, float weight){
			this.name = name;
			this.weight = weight;
			if(!super.exists(name)){
				new Atributo(name);
			}
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
			return "Atributo: "+this.name+", peso: "+this.weight+"\n";
		}
	}
	
}
