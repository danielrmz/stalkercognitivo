import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**
 * Frame para editar la lista de bloqueados/no bloqueados del sistema
 * 
 * @author Equipo 6 Cynthia Treviño, Ricardo Magallanes, Daniel Ramirez 
 */
public class BlackListFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 2149207796714760514L;

	private Persona base = null;
	
	private JButton agregar = new JButton("Agregar >");
	
	private JButton quitar = new JButton("< Quitar");
	
	private JButton cancelar = new JButton("Cerrar");
	
	private JList aceptados = null;
	
	private JList blacklist = null;
	
	private DefaultListModel dlmAceptados = new DefaultListModel();
	
	private DefaultListModel dlmBlacklist = new DefaultListModel();
	
	public BlackListFrame(Persona p){
		super("Lista Negra de: "+p.getNombre());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(400,500));
		this.setLayout(new BorderLayout());
		JPanel top = new JPanel(new GridLayout(2,1));
		top.add(new JLabel("Lista negra de: "+p.getNombre()));
		top.add(new JLabel("Mueva las personas como desee establecer la relacion"));
		this.add(top,BorderLayout.NORTH);
		
		this.base = p;

		JPanel table = new JPanel(new GridLayout(1,3));
		
		aceptados = new JList(dlmAceptados);
		
		blacklist = new JList(dlmBlacklist);
		
		aceptados.setPreferredSize(new Dimension(120, 600));
		blacklist.setPreferredSize(new Dimension(120, 600));
		aceptados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		blacklist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		aceptados.setFixedCellHeight(20);
		blacklist.setFixedCellHeight(20);
		
		table.add(aceptados);
		JPanel intermedio = new JPanel(new BorderLayout());
		JPanel intermedio2 = new JPanel(new GridLayout(6,1));
		intermedio2.add(new JLabel(" "));
		intermedio2.add(new JLabel(" "));
		intermedio2.add(agregar);
		intermedio2.add(quitar);
		intermedio2.add(new JLabel(" "));
		intermedio2.add(new JLabel(" "));
		
		intermedio.add(intermedio2,BorderLayout.CENTER);
		table.add(intermedio);
		table.add(blacklist);
		
		this.add(table);
		
		agregar.addActionListener(this);
		quitar.addActionListener(this);
		cancelar.addActionListener(this);
		
		JPanel bot = new JPanel(new FlowLayout());
		bot.add(cancelar);
			
		this.add(bot,BorderLayout.SOUTH);
		
		this.cargaAmigos();
		this.cargaEnemigos();
	}

	public void cargaAmigos(){
		for(Persona p : this.base.getAmigos()){
			if(p!=null && !this.base.esEnemigo(p) && !p.getNombre().equals(""))
			this.dlmAceptados.addElement(p);
		}
	}

	public void cargaEnemigos(){
		for(Persona p : this.base.getEnemigos()){
			if(p!=null && !p.getNombre().equals("")) {
				this.dlmBlacklist.addElement(p);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(agregar)){
			Persona seleccionado = (Persona)this.aceptados.getSelectedValue();
			this.base.agregaEnemigo(seleccionado);
			this.dlmAceptados.removeElement(seleccionado);
			this.dlmBlacklist.addElement(seleccionado);
		} else if(arg0.getSource().equals(quitar)){
			Persona seleccionado = (Persona)this.blacklist.getSelectedValue();
			this.base.removeEnemigo(seleccionado);
			this.dlmAceptados.addElement(seleccionado);
			this.dlmBlacklist.removeElement(seleccionado);
		} else if(arg0.getSource().equals(cancelar)){
			this.dispose();
		}
	}
}
