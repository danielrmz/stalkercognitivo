import java.util.*;

/**
 * Encapsuladora de los atributos 
 * de las personas y sus pesos
 * @author Equipo 6
 */
public class Atributo {
	//-- Atributos dados de alta en el sistema
	public static LinkedList<Atributo> atributos = new LinkedList<Atributo>();
	
	protected int id = 0;
	protected String name 		= "";
	protected String description 	= "";
	
	public Atributo(){
		
	}
	
	public Atributo(String name){
		this.name 	= name;
	}
	
	public Atributo(String name, int id){
		this.name = name;
		this.id = id;
	}
	
	public Atributo(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	public boolean compareAttributes(Atributo a1, Atributo a2){
		//TBD to be done.
		return true;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getId(){
		return this.id;
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
	
	
}
