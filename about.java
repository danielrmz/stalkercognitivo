import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class about extends JFrame implements ActionListener{

	private JLabel about,cynthia,angel,ricardo,daniel;
	private ImageIcon tec;
	private JLabel eltec;
	private JButton quit;
	
	public about(){
		this.setBackground(Color.white);
		setVisible(true);
		setTitle("Acerca de");
		setSize(300,300);
		setLayout(null);
		Insets inset=this.getInsets();
		
	
		
		tec=new ImageIcon("tec.gif");
		eltec=new JLabel(tec);
		add(eltec);
		Dimension x=eltec.getPreferredSize();
	    eltec.setBounds(75+inset.left,0+inset.top,x.width,x.height);
		
		
		about=new JLabel("Ciencia Cognitiva: Proyecto Final");
		add(about);
		Dimension desconocida =about.getPreferredSize();
		about.setBounds(50+inset.left,100+inset.top,desconocida.width,desconocida.height);
		
		cynthia=new JLabel("Cynthia Treviño 64221");
		add(cynthia);
		Dimension alterna = cynthia.getPreferredSize();
		cynthia.setBounds(80+inset.left,120+inset.top,alterna.width,alterna.height);
		
		angel=new JLabel("José Angel Romo 997289");
		add(angel);
		Dimension alterna2 = angel.getPreferredSize();
		angel.setBounds(75+inset.left,140+inset.top,alterna2.width,alterna2.height);
		
		ricardo=new JLabel("Ricardo Magallanes 1030090");
		add(ricardo);
		Dimension alterna3 = ricardo.getPreferredSize();
		ricardo.setBounds(60+inset.left,160+inset.top,alterna3.width,alterna3.height);
		
		daniel=new JLabel("Daniel Ramírez 1030632");
		add(daniel);
		Dimension alterna4 = daniel.getPreferredSize();
		daniel.setBounds(75+inset.left,180+inset.top,alterna4.width,alterna4.height);
		
		quit=new JButton("Cerrar Ventana");
		quit.addActionListener(this);
		add(quit);
		Dimension alterna5 = quit.getPreferredSize();
		quit.setBounds(90+inset.left,210+inset.top,alterna5.width,alterna5.height);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(quit)){
			setVisible(false);
		}
	}
	
	
	//"+"\n"+"JosÃ© Angel Romo 997289 \n daniel Magallanes 1030090 \n Daniel Ramirez 1030632"
	
}
