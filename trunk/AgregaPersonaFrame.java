import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import org.jgraph.*;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;

public class AgregaPersonaFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 9057251280536386344L;
	private JLabel nombreL;
	private JTextField nombre;
	private JButton guardar,limpiar,cancelar;
	private JComboBox[] atributos;
	private JLabel[] atributosL;
	private JTextField[] pesos;
	private String[] attNames;
	
	public AgregaPersonaFrame(){
		//-- Propiedades de la Interfaz
		this.setSize(400,300);
		this.setTitle("Administrador de Atributos");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		
		nombreL = new JLabel("Nombre");
		nombreL.setBounds(10, 10, 50, 20);
		nombre = new JTextField();
		nombre.setBounds(70, 10, 300, 20);
		
		int x1 = 50;
		
		this.add(nombreL);
		this.add(nombre);
		
		atributos = new JComboBox[Atributo.atributos.size()];
		atributosL = new JLabel[Atributo.atributos.size()];
		pesos = new JTextField[Atributo.atributos.size()];
		attNames = new String[Atributo.atributos.size()+1];
		
		attNames[0]=" ";
		for(int j=0; j<Atributo.atributos.size(); j++){
			attNames[j+1] = Atributo.atributos.get(j).toString();
		}
		
		for(int i=0; i<Atributo.atributos.size();i++){
			atributosL[i] = new JLabel("Atributo "+(i+1));
			atributosL[i].setBounds(10, x1, 70, 20);
			atributos[i] = new JComboBox(attNames);
			//atributos[i].setBackground(Color.white);
			atributos[i].setBounds(80, x1, 200, 20);
			atributos[i].setEditable(false);
			atributos[i].addActionListener(this);
			pesos[i]= new JTextField();
			pesos[i].setBounds(290, x1, 60, 20);
			this.add(atributosL[i]);
			this.add(atributos[i]);
			this.add(pesos[i]);
			x1 +=30;
		}
		
		
		guardar = new JButton("Guardar");
		guardar.addActionListener(this);
		
		limpiar = new JButton("Limpiar");
		limpiar.addActionListener(this);
		
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(this);
		
		JPanel botones = new JPanel(new GridLayout(1,3,10,10));
		botones.add(limpiar);
		botones.add(guardar);
		botones.add(cancelar);
		botones.setBounds(40, x1, 300, 20);
		
		this.add(botones);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
