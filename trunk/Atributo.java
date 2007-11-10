import java.util.*;

/**
 * Encapsuladora de los atributos 
 * de las personas y sus pesos
 * @author Equipo 6
 */
public class Atributo {
	//-- Atributos dados de alta en el sistema
	public static LinkedList<Atributo> atributos = new LinkedList<Atributo>();
	
	protected String name 		= "";
	protected String description 	= "";
	
	public Atributo(){
		
	}
	
	public boolean exists(String name){
		for(Atributo a : Atributo.atributos){
			if(a.name.equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public Atributo(String name){
		this.name 	= name;
		Atributo.atributos.addLast(this);
	}
	
	
	public Atributo(String name, String description){
		this.name = name;
		this.description = description;
		Atributo.atributos.addLast(this);
	}
	
	public boolean compareAttributes(Atributo a1, Atributo a2){
		//TBD to be done.
		return true;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){ 
		return this.description;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDescription(String desc){
		this.description = desc;
	}
	
	public String toString(){
		return this.name;
	}
	
	public static String allToString(){
		String all = "";
		for(Atributo a : Atributo.atributos){
			all+=a.toString()+",";
		}
		return all;
	}
	
	
}
