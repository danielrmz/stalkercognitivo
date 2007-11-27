import java.awt.Dimension;
import java.util.StringTokenizer;
import java.io.*;
import java.util.*;
import org.jgraph.JGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.*;




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
	public static int	ERROR_RANGE			 = 40;
	
	static Persona daniel ;	
	static Persona cynthia ;
	
	//-- Metodo temporal para agregar datos de prueba
	public static void defaults(Agente x){
		// Personas actuales
		/*Persona ricardo = new Persona("Ricardo");
		Persona angel 	= new Persona("Angel");
		Persona memo 	= new Persona("Guillermo");
		Persona pedro 	= new Persona("Pedro");
		Persona damaris = new Persona("Damaris");
		Persona leo 	= new Persona("Leonidas");
		Persona marin 	= new Persona("Marin");
		Persona daniel = new Persona("Daniel");
		Persona cynthia = new Persona("Cynthia");
		
		for(Persona persona : Persona.personas){
			persona.setAttribute("Comida Spicy", (float)(Math.random()*100));
			persona.setAttribute("Comida China",(float)(Math.random()*100));
			persona.setAttribute("Soccer", (float)(Math.random()*100));
		}
		
		daniel.agregaAmigo(cynthia);
		//x.graph.g.addEdge(daniel, cynthia);
		cynthia.agregaAmigo(ricardo);
		//x.graph.g.addEdge(cynthia, ricardo);
		cynthia.agregaAmigo(angel);
		//x.graph.g.addEdge(cynthia, angel);
		daniel.agregaAmigo(memo);
		//x.graph.g.addEdge(daniel, memo);
		ricardo.agregaAmigo(pedro);
		//x.graph.g.addEdge(ricardo, pedro);
		angel.agregaAmigo(pedro);
		//x.graph.g.addEdge(angel, pedro);
		pedro.agregaAmigo(damaris);
		//x.graph.g.addEdge(pedro, damaris);
		leo.agregaAmigo(marin);
		//x.graph.g.addEdge(leo, marin);
		damaris.agregaAmigo(leo);
		//x.graph.g.addEdge(damaris,leo);
		pedro.agregaAmigo(leo);
		//x.graph.g.addEdge(pedro, leo);
		cynthia.agregaEnemigo(angel);  */
	}
	
	//-- Main
	public static void main(String[] args) {
		Agente.ag = new Agente();
		defaults(Agente.ag);
		try{
			 cargaGrafo();
			 
			}
			catch(Exception io){
			   io.toString();	
			
			}
		System.out.println("Inicial: "+Agente.ag.graph.g.toString());
		System.out.println(Persona.personas.getFirst().atrToString());
		Agente.ag.buscarAmigos(Persona.personas.getFirst());
		System.out.println(Persona.personas.getFirst().atrToString());
		System.out.println("Final: "+Agente.ag.graph.g.toString());
		
		try{
			guardaInformacion();
			
		}
		catch(Exception e){
			e.toString();
		}
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
	
	public void inicializarMatriz(float[][] promedioAtt){
		for(int i=0; i<promedioAtt.length;i++){
			for(int j=0; j<promedioAtt[0].length;j++){
				promedioAtt[i][j]=0;
			}
		}
	}
	public void buscarPersona(Persona p, Queue<Vecino> pila){
		float promedioAtt[][] = new float[p.getAtributos().size()][2]; // 0 - sumatoria , 1 - cantidad de hits
		inicializarMatriz(promedioAtt);
		while(!pila.isEmpty()){
			//System.out.println(pila.toString());
			Vecino candidato = pila.dequeue();
			boolean result = realizaConexion(p,candidato.getP(),promedioAtt);
			if(pila.peek()==null){
				for(int i=0; i<promedioAtt.length;i++){
					if(promedioAtt[i][1]!=0){ //evitar infinitos
						float m = (p.getAtributos().get(i).getWeight()+(promedioAtt[i][0]/promedioAtt[i][1]))/2;
						p.getAtributos().get(i).setWeight(m);
					}
				}
				inicializarMatriz(promedioAtt);
			}else if(pila.peek().getNivel()!=candidato.getNivel()){ //esta en el borde, realizar promedios
				for(int i=0; i<promedioAtt.length;i++){
					if(promedioAtt[i][1]!=0){ //evitar infinitos
						float m = (p.getAtributos().get(i).getWeight()+(promedioAtt[i][0]/promedioAtt[i][1]))/2;
						p.getAtributos().get(i).setWeight(m);
					}
				}
				inicializarMatriz(promedioAtt);
			}
			if(candidato.getNivel()<=0){
				continue;
			}
			Persona[] vecinos = candidato.getP().getAmigos();
			
			if(!result){
				//-- Si no hay conexion con el amigo, intentar un nivel mas a partir del amigo, para cersiorarse de 
				//-- que no haya un subgrupo con los mismos gustos.
				for(Persona vecino : vecinos){
					pila.enqueue(new Vecino(vecino,candidato.getNivel()-1));
				}
			}
		}
		
	}
	
	
	//-- Metodo que busca amigos con caracteristicas similares
	public void buscarAmigos(Persona p){
		NeighborIndex<Persona,DefaultEdge> ni = new NeighborIndex<Persona,DefaultEdge>(this.graph.g);
		Object[] vecinos = ni.neighborsOf(p).toArray();
		Queue<Vecino> pila = new Queue<Vecino>();
		for(Object vecino: vecinos){
			Object[] candidatos = ni.neighborsOf((Persona)vecino).toArray();
			for(Object candidato: candidatos){
				pila.enqueue(new Vecino((Persona)candidato,Agente.NIVEL));
			}
		}
		//System.out.println(pila.toString());
		buscarPersona(p, pila);
	}
	
	/*
	 * 1. Se empieza el recorrido con todos sus amigos a traves de los niveles indicados.
	   2. Con un candidato
		   2.1 Obtener atributos comunes
		   2.2 Decidir si es amigo o no
		     2.2.1 Primero diferencia entre atributos comunes y sacar promedio de eso
		     2.2.2 Comprarar si es menos del 100-target asignado por la persona. = porciento de error
		           Si es menor, hacer conexion
		   2.3 Inicializar el promedio cada uno de los atributos. para posteriormente modificar
		       el peso de los atributos del individo. Teoria: se debe de acentuar uno mas que los demas, en base a
		       que tipo de gente predomina en la red de amistades.
		   2.4 Ir sacando el promedio de cada atributo con las nuevas caracteristicas dadas por la conexion
	  3. Continuar con el siguiente candidato.
	 */
	
	//-- Realiza la conexion en base a las caracteristicasy pesos que tiene cada uno
	//-- funcion core donde se plasma el aumento de experiencia y creacion de relaciones
	//-- entre usuarios
	public boolean realizaConexion(Persona a, Persona b, float[][] promAtt){
		//se comparan los atributos y se modifica el grafo
		if(a.equals(b)) return false;
		int common = 0; //num atributos comunes
		double promedio = 0; //promedio de las diferencias entre los atributos comunes de las personas
		int indexAtt=0;
		double pesoAtt=0;
		//-- Se busca sobre todos los atributos existentes, aquellos que tengan en comun
		//-- si tienen en comun esos entonces calcular probabilidad y hacer la conexion.
		for(Atributo base : Atributo.atributos){
			Persona.PersonaAtributo aatrib = a.getAtributo(base.getName());
			Persona.PersonaAtributo batrib = b.getAtributo(base.getName());
			if(aatrib != null && batrib != null){
				//-- Comparar peso de atributos y sacar su diferencia, irla acumulando en la variable promedio
				float aweight = aatrib.getWeight();
				float bweight = batrib.getWeight();
				float dif = Math.abs(bweight - aweight);
				promedio += dif; //acumular la diferencia en la variable de promedio
				common++; //contabilizar atributo comun
				indexAtt=a.getAtributos().indexOf(aatrib);
				pesoAtt=batrib.getWeight();
				/*if(dif <= Agente.ERROR_RANGE){
				//if((aweight-Agente.ERROR_RANGE <= bweight && aweight+Agente.ERROR_RANGE >= bweight) || 
				//		bweight-Agente.ERROR_RANGE <= aweight && bweight+Agente.ERROR_RANGE >= aweight){
					common++;
					
					//-- Recalcular probabilidad de obtener otro atributo comoe ste
				}*/
			}
		}
		promedio /= common; //se promedian las diferencias de los atributos iguales
		if(promedio<=Agente.ERROR_RANGE){ //considera el input dado por el usuario
			//Se considera que en ese nivel hay alquien con esos gustos  
			promAtt[indexAtt][0]+=pesoAtt;
			promAtt[indexAtt][1]++;
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
		//BufferedReader ent = new BufferedReader(new FileReader("estadoAct.txt"));
		
		
		
		for(int i=0; i<Persona.personas.size(); i++){
		      
		
			 salida.print(Persona.personas.get(i).getNombre()+"/");
			 salida.flush();
			
		}
		salida.println();
		
		for(int i=0; i<Persona.personas.size(); i++){
			 if(Persona.personas.get(i).wlToString().equals("")){
				 salida.print("@/");
			 }
			 else{
		      
		     salida.print(Persona.personas.get(i).wlToString()+"/");
			 }
             salida.flush();
			
		}
		salida.println();
		for(int i=0; i<Persona.personas.size(); i++){
		      
			 if(Persona.personas.get(i).blToString().equals("")){
				 salida.print("@/");
			 }
			 else{
			 salida.print(Persona.personas.get(i).blToString()+"/");
			 }
			 salida.flush();
			
		}
		salida.println();
		for(int i=0; i<Persona.personas.size(); i++){
		      
		
	
			 salida.print(Persona.personas.get(i).atrToString()+"/");
			 salida.flush();
			
		}
		
		
		salida.close();
		
	}
	
	public static Persona encuentraElemento(String nombre){
		
		for(int i=0; i<Persona.personas.size();i++){
			if(Persona.personas.get(i).getNombre().equals(nombre)){
				return Persona.personas.get(i);
			}
				
		}
		return null;
	}
	
	public static void cargaGrafo() throws Exception{
		  
		BufferedReader ent = new BufferedReader(new FileReader("grafo.txt"));
		
		String s = ent.readLine();
		StringTokenizer st= new StringTokenizer(s,"/");
		int para=st.countTokens();
		while(st.hasMoreElements()){
		 String sub=(st.nextToken());	
		    Persona i = new Persona(sub);
		}
		
		s=ent.readLine();
	    StringTokenizer st2=new StringTokenizer(s,"/");
	    
	    int size= st2.countTokens();
	     Object org[][]=new Object[(size*size)-1][2];
	     int u=0;
		for(int i=0; i<9; i++){
			int z=i;
		  StringTokenizer sub2=new StringTokenizer(st2.nextToken(),",");	
		  	 while(sub2.hasMoreTokens()){
		  		 
		  		  org[u][0]=sub2.nextToken();
		  		  org[u][1]=z;
		  		  u++;
		  	 }
		}
		
		/*for(int k1=0; k1<u; k1++){
			System.out.println(""+org[k1][0]+" "+org[k1][1]);
		}*/
		
		
        for(int k2=0; k2<u; k2++){
		
         String k3=(""+org[k2][0]);
         String k4=(""+org[k2][1]);
         int test2=Integer.parseInt(k4);
         Persona test = encuentraElemento(k3);
         //System.out.println(test.getNombre()+" "+test2);
		 Persona.personas.get(test2).agregaAmigo(test);
		
        }
		
	    s=ent.readLine();
	    StringTokenizer st3=new StringTokenizer(s,"/");
	    int i=0;
	    while(st3.hasMoreElements()){
	       String c2=st3.nextToken();
	       
	       if(!(c2.equals("@"))){
	    	   Persona x=encuentraElemento(c2);
	    	   Persona.personas.get(i).agregaEnemigo(x);
	       }
	       i++;
	    }
	    
	    s=ent.readLine();
	    int cont=0;
	    
	    
	    StringTokenizer st4=new StringTokenizer(s,"/");
	    //String sq=st4.nextToken();
	   
	    while(st4.hasMoreElements()){
	    StringTokenizer sub4=new StringTokenizer(st4.nextToken(),",");
	    String a="";
	    
	     while(sub4.hasMoreElements()){
	    	String z=sub4.nextToken();
	    	
	    	if(!(z.equals("0.0"))){
	    		a+=z+"|";
	    	}
	   
	    }
	    //System.out.println(a); 
	    StringTokenizer ultimo=new StringTokenizer(a,"|");
	     
	    for(int ult=1; ult<=3; ult++){
	    	
	    	Persona.personas.get(cont).setAttribute(ultimo.nextToken(), Float.parseFloat(ultimo.nextToken()));
	      	
	    }
	    
	    cont++;
	    }
		ent.close();
	}

}