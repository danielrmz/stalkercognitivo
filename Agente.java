import java.awt.Dimension;
import java.util.StringTokenizer;
import java.io.*;
import java.util.*;
import org.jgraph.JGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.*;


/**
 * Agente Inteligente. 
 * El principal proposito de esta clase es buscar a los amigos
 * cuyo indice de probabilidad entre atributos al compararlos sea similar.
 * Asimismo proveer de una interfaz para la obtencion del grafo que se contiene en una variable
 * de esta clase.
 * 
 * @author Equipo 6 Cynthia Trevi�o, Ricardo Magallanes, Daniel Ramirez 
 */
public class Agente {
	private Grafo graph; //-- Abstraccion de la libreria de jgraph
	
	//-- Constantes que determinan algunos criterios de poda al encontrar usuarios
	public static int 	NIVEL 				 = 3;
	public static int 	MIN_MATCH_ATTRIBUTES = 3;
	public static int 	PERSONS_FOUND_LIMIT	 = 5;
	public static int	ERROR_RANGE			 = 40;
	
	//-- Metodo temporal para agregar datos de prueba
	public static void defaults(Agente x){
		// Personas actuales
		Grafo.cargaGrafo("estadoAct.txt");
		/**Persona ricardo = new Persona("Ricardo");
		Persona angel 	= new Persona("Angel");
		Persona memo 	= new Persona("Guillermo");
		Persona pedro 	= new Persona("Pedro");
		Persona damaris = new Persona("Damaris");
		Persona leo 	= new Persona("Leonidas");
		Persona marin 	= new Persona("Marin");
		Persona daniel = new Persona("Daniel");
		Persona cynthia = new Persona("Cynthia");

		Persona giraf = new Persona("Giraf");
		Persona juane = new Persona("Juan Escutia");
		Persona elle = new Persona("Elle");
		Persona gina = new Persona("Gina");
		Persona nathan = new Persona("Nathan");
		
		for(Persona persona : Persona.personas){
			persona.setAttribute("Perros", (float)(Math.random()*100));
			persona.setAttribute("Comida Spicy", (float)(Math.random()*100));
			persona.setAttribute("Carros de Alta Velocidad", (float)(Math.random()*100));
			persona.setAttribute("Comida China",(float)(Math.random()*100));
			persona.setAttribute("Soccer", (float)(Math.random()*100));
		}
		daniel.setAttribute("Pizza", 55);
		ricardo.setAttribute("Pizza", 70);
		pedro.setAttribute("Pizza", 70);
		
		daniel.agregaAmigo(cynthia);
		cynthia.agregaAmigo(ricardo);
		daniel.agregaAmigo(nathan);
		cynthia.agregaAmigo(elle);
		gina.agregaAmigo(elle);
		juane.agregaAmigo(cynthia);
		giraf.agregaAmigo(angel);
		cynthia.agregaAmigo(angel);
		daniel.agregaAmigo(memo);
		ricardo.agregaAmigo(pedro);
		angel.agregaAmigo(pedro);
		pedro.agregaAmigo(damaris);
		leo.agregaAmigo(marin);
		damaris.agregaAmigo(leo);
		cynthia.agregaEnemigo(angel); 
		for(Persona persona : Persona.personas){
			System.out.println(persona.getNombre()+" :: "+persona.atrToString());
		}**/
		
	}
	
	//-- Constructor
	public Agente(){ 
		this.graph = new Grafo(new Dimension( 530, 320 ));
	}
	
	public ListenableGraph<Persona,DefaultEdge> getGrafo(){
		return this.graph.getGraph();
	}
	
	public JGraph getDisplay(){
		return this.graph.getDisplay();
	}
	
	public Grafo _getGrafo(){
		return this.graph;
	}
	
	public void clear(){
		this.graph = new Grafo(new Dimension( 530, 320 ));
		Main.getInterfaz().setGraphDisplay(this.graph.getDisplay());
		Main.getInterfaz().clear();
		Persona.personas.clear();
		
	}

	public void inicializarMatriz(float[][] promedioAtt){
		for(int i=0; i<promedioAtt.length;i++){
			for(int j=0; j<promedioAtt[0].length;j++){
				promedioAtt[i][j]=0;
			}
		}
	}
	public void buscarPersona(Persona p, Queue<Vecino> pila,LinkedList<String> searched, LinkedList<Persona> found){
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
			}else if(pila.peek().getNivel()!=candidato.getNivel()){
				//esta en el borde, realizar promedios
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
			
			if(result){ //Guardar los que encontro
				found.add(candidato.getP());
			}
			
			if(!result && !p.equals(candidato.getP())){
				//-- Si no hay conexion con el amigo, intentar un nivel mas a partir del amigo, para cersiorarse de 
				//-- que no haya un subgrupo con los mismos gustos.
				for(Persona vecino : vecinos){
					if(!searched.contains(vecino.getNombre())){
						pila.enqueue(new Vecino(vecino,candidato.getNivel()-1));
					}
				}
				
			}
		}
		
	}
	
	
	//-- Metodo que busca amigos con caracteristicas similares
	public LinkedList<Persona> buscarAmigos(Persona p, int nivel, int error_range){
		NeighborIndex<Persona,DefaultEdge> ni = new NeighborIndex<Persona,DefaultEdge>(this.getGrafo());
		Agente.NIVEL = nivel;
		Agente.ERROR_RANGE = 100-error_range;
		
		Object[] vecinos = ni.neighborsOf(p).toArray();
		Queue<Vecino> pila = new Queue<Vecino>();
		LinkedList<String> 	  searched = new LinkedList<String>();
		LinkedList<Persona> 	  found = new LinkedList<Persona>();
		
		searched.add(p.getNombre());
		for(Object vecino: vecinos){
			Object[] candidatos = ni.neighborsOf((Persona)vecino).toArray();
			for(Object candidato: candidatos){
				if(!searched.contains(((Persona)candidato).getNombre())){
					pila.enqueue(new Vecino((Persona)candidato,Agente.NIVEL));
					searched.add(((Persona)candidato).getNombre());
				}
			}
		}
		
		buscarPersona(p, pila,searched,found);
		return found;
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
		//System.out.println("Persona: "+a.toString()+" vs. "+b.toString());
		LinkedList<Integer> attASumar = new LinkedList<Integer>();
		LinkedList<Float> pesosASumar = new LinkedList<Float>();
		for(Atributo base : Atributo.atributos){
			Persona.PersonaAtributo aatrib = a.getAtributo(base.getName());
			Persona.PersonaAtributo batrib = b.getAtributo(base.getName());
			if(aatrib != null && batrib != null){
				//System.out.println("Atributo similar: "+aatrib.toString()+"||||"+batrib.toString());
				//-- Comparar peso de atributos y sacar su diferencia, irla acumulando en la variable promedio
				float aweight = aatrib.getWeight();
				float bweight = batrib.getWeight();
				float dif = Math.abs(bweight - aweight);
				promedio += dif; //acumular la diferencia en la variable de promedio
				common++; //contabilizar atributo comun
				indexAtt=a.getAtributos().indexOf(aatrib);
				pesoAtt=batrib.getWeight();
				attASumar.add(indexAtt);
				pesosASumar.add(new Float(pesoAtt));
				/*if(dif <= Agente.ERROR_RANGE){
				//if((aweight-Agente.ERROR_RANGE <= bweight && aweight+Agente.ERROR_RANGE >= bweight) || 
				//		bweight-Agente.ERROR_RANGE <= aweight && bweight+Agente.ERROR_RANGE >= aweight){
					common++;
					
					//-- Recalcular probabilidad de obtener otro atributo comoe ste
				}*/
			}
		}
		//System.out.println("Promedio 1: "+promedio+", Comunes:"+common);
		promedio /= common; //se promedian las diferencias de los atributos iguales
		//System.out.println("Promedio 2:"+promedio+", Error Range:"+Agente.ERROR_RANGE);
		if(promedio<=Agente.ERROR_RANGE){ //considera el input dado por el usuario
			//Se considera que en ese nivel hay alquien con esos gustos
			int i = 0;
			for(Integer index : attASumar){
				promAtt[index][0]+=pesosASumar.get(i);
				promAtt[index][1]++;
				i++;
			}
			a.agregaAmigo(b);
			b.agregaAmigo(a);
			this.graph.getGraph().addEdge(a,b);
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
		this.graph.getGraph().addVertex(p);
		this.graph.getGraph().addEdge(p,b);	
	}
	
	//-- Quita una persona del grafo
	public void quitarPersona(Persona p){
		this.graph.getGraph().removeVertex(p);
		Persona.personas.remove(p);
	}
	
	//-- Elimina una conexion.
	public void eliminaConexion(Persona a, Persona b){
		a.agregaEnemigo(b);
		a.removeAmigo(b);
		b.removeAmigo(a);
		this.graph.getGraph().removeEdge(this.graph.getGraph().getEdge(a,b));
	}
	
}