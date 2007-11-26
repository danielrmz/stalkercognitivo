import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class Interfaz extends JFrame implements ActionListener {
	private JMenuBar menuPrincipal;
	private JMenu archivo, simulacion, ayuda;
	private JMenuItem archivoAbrir, archivoGuardar, archivoCerrar, simulacionAgregar,
					  simulacionBorrar, simulacionBuscar, simulacionAtributos,
					  ayudaInfo;
	private JList amigos;
	private JPanel grafo,busqueda,panelIzq,panelDer;
	
	public Interfaz(){
		this.setSize(800,600);
		this.setTitle("StalkerCognitivo 127.0.0.1");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		menuPrincipal = new JMenuBar();
		
		archivo = new JMenu("Archivo");
		archivoAbrir = new JMenuItem ("Abrir Red");
		archivoGuardar = new JMenuItem ("Guardar Red");
		archivoCerrar = new JMenuItem ("Cerrar Simulador");
		archivo.add(archivoAbrir);
		archivo.add(archivoGuardar);
		archivo.add(archivoCerrar);
		
		simulacion = new JMenu("Simulación");
		simulacionAgregar = new JMenuItem ("Agregar Persona");
		simulacionBorrar = new JMenuItem ("Borrar Persona");
		simulacionBuscar = new JMenuItem ("Buscar Amigos");
		simulacionAtributos = new JMenuItem ("Administrar Atributos");
		simulacion.add(simulacionAgregar);
		simulacion.add(simulacionBorrar);
		simulacion.add(simulacionBuscar);
		simulacion.add(simulacionAtributos);
		
		ayuda = new JMenu("Ayuda");
		ayudaInfo = new JMenuItem("Acerca De");
		ayuda.add(ayudaInfo);
		
		menuPrincipal.add(archivo);
		menuPrincipal.add(simulacion);
		menuPrincipal.add(ayuda);
		
		
		panelIzq= new JPanel();
		//Cargar el listado de personas a la lista
		String amigoz [] = new String[100];
		for(int i=0; i<100; i++){
			amigoz[i] = "Amigo "+i;
		}
		amigos = new JList(amigoz);
		amigos.setPreferredSize(new java.awt.Dimension(200, 600));
		amigos.setVisibleRowCount(30);
		amigos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelIzq.add(new JScrollPane(amigos));
		
		
		panelDer = new JPanel();
		//panelDer.setPreferredSize(new java.awt.Dimension(600, 800));
		panelDer.setLayout(new BorderLayout());
		grafo=new JPanel();
		JTextArea p = new JTextArea("Adiosss");
		p.setPreferredSize(new java.awt.Dimension(555, 335));
		grafo.add(p);
		//grafo.setPreferredSize(new java.awt.Dimension(550, 500));
		busqueda=new JPanel();
		JTextArea p1 = new JTextArea("Holaaaaaa");
		p1.setPreferredSize(new java.awt.Dimension(555, 200));
		busqueda.add(p1);
		//busqueda.setPreferredSize(new java.awt.Dimension(550, 300));
		panelDer.add(grafo,BorderLayout.NORTH);
		panelDer.add(busqueda,BorderLayout.SOUTH);
		
		this.setJMenuBar(menuPrincipal);
		this.add(panelIzq,BorderLayout.WEST);
		this.add(panelDer,BorderLayout.CENTER);
		
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		Interfaz inter = new Interfaz();
		inter.setVisible(true);
	}
	
}
