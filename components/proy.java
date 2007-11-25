/* las variables ta1 es donde se va a desplegar la info del contacto, carge una lista
 *tentativa, si seleccionas un elemento obtiene el valor en la linea 128, el password es cognity*/
package components;
import java.awt.event.*;

import java.awt.*;                                                           
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;

import javax.swing.*;


import java.*;
import java.util.Random;
import java.util.*;

public class proy extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar jMenuBar1;
	private JPanel jPanel1;
	private JLabel lblStatus;
	private JLabel jLabel_IL;
	private JPanel pnlGraph;
	private JPanel pnlSearch;
	private JPanel jPanel2,jPanel4;
	private JList lstPersons;
	private JMenu jmAttrib;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem1;
	private JMenuItem jMenuItem3;
		private JDesktopPane theDesktop;
	private JMenu jMenu1;
	private final String data[]= { "	Cafu 	"	,"	Cicinho 	"	,"	Lúcio 	"	,"	Juan	"	,"	Luisão 	"	,"	Alex 	"	,"	Roberto Carlos 	"	,"	Gustavo Nery 	"	,"	Emerson 	"	,"	Zé Roberto 	"	,"	Kaká 	"	,"	Ricardinho	"	,"	Gilberto Silva 	"	,"	Renato 	"	,"	Júlio Baptista 	"	,"	Juninho Pernambucano 	"	,"	Alicia Keys	"	,"	Alicia Silverstone	"	,"	Alicia Witt	"	,"	Alizee	"	,"	Allison Mack	"	,"	Alyson Hannigan	"	,"	Alyssa Milano	"	,"	Julia Stiles	"	,"	Julianne Moore	"	,"	Julie Benz	"	,"	Kaley Cuoco	"	,"	Kate Beckinsale	"	,"	Kate Bosworth	"	,"	Kate Hudson	"	,"	Kate Mara	"	,"	Kate Winslet	"	,"	Katharine McPhee	"	,"	Katherine Heigl	"	,"	Kathleen Robertson	"	};
	private JButton acepto,cancel;
private static String doit = "Hacer amigos";
private static String cancell = "Cancel";
private JTextArea ta1;



	
	//-- AGENTE
//	private Agente agente;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				proy inst = new proy();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public proy() {
		super();
	//	this.agente = new Agente();
		initGUI();
	}
	
	private void initGUI() {
		try {
				theDesktop= new JDesktopPane();
		add(theDesktop);
		acepto=new JButton(doit);
cancel=new JButton(cancell);

		
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			BorderLayout thisLayout = new BorderLayout();
			this.setTitle("Redes Sociales Cognitivas");
		//	getContentPane().setLayout(thisLayout);
		    theDesktop.setLayout(thisLayout);
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();
				theDesktop.add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setPreferredSize(new java.awt.Dimension(655, 320));
				{
					lblStatus = new JLabel();
					jPanel1.add(lblStatus, BorderLayout.SOUTH);
					lblStatus.setText("Estatus");
				}
				{

///////////////////////////////////////////////////////////////////////////////////////////////////				
					 // AQUI SE OBTIENE LOS NOMBRES DE LAS PERSONAS QUE FUERON SELECCIONADAS DE LA LISTA
					lstPersons = new JList(data);
					lstPersons.setVisibleRowCount(5);
					lstPersons.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					
														
					
					jPanel1.add((new JScrollPane(lstPersons)), BorderLayout.WEST);
					
						
					lstPersons.addListSelectionListener(
						new ListSelectionListener()
						{
							public void valueChanged (ListSelectionEvent event)
							{
							acepto.setVisible(true);
						cancel.setVisible(true);
								System.out.println(lstPersons.getSelectedValue());
							}
						});
					}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
				{
					jPanel2 = new JPanel();
					BorderLayout jPanel2Layout = new BorderLayout();
					jPanel2.setLayout(jPanel2Layout);
					jPanel1.add(jPanel2, BorderLayout.CENTER);
					{
						pnlGraph = new JPanel();
						pnlGraph.setSize(new Dimension( 530, 620 ));//530, 420
						jPanel2.add(pnlGraph/*, BorderLayout.NORTH*/);//antes center
						{
							jLabel_IL = new JLabel("x");
							pnlGraph.add(jLabel_IL);
							jLabel_IL.setText("x");
							jLabel_IL.setPreferredSize(new java.awt.Dimension(514, 179));//514, 179
						}
					}
					{   ta1= new JTextArea(5,100);
						pnlSearch = new JPanel();
					
						jPanel2.add(pnlSearch, BorderLayout.SOUTH);//antes center
					
					acepto.setVisible(false);
						cancel.setVisible(false);
						
				
						
						
						pnlSearch.add(acepto,BorderLayout.NORTH);
						pnlSearch.add(cancel,BorderLayout.SOUTH);
						pnlSearch.add(ta1);
					
						
					}
				}
			}//cierre de the desktop
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
					jMenu1 = new JMenu();
					jMenuBar1.add(jMenu1);
					jMenu1.setText("Archivo");
					{
						jMenuItem1 = new JMenuItem();
						jMenu1.add(jMenuItem1);
						jMenuItem1.setText("Cargar Datos");
					}
					{
						jMenuItem2 = new JMenuItem();
						jMenu1.add(jMenuItem2);
						jMenuItem2.setText("Salir");
						jMenuItem2.addActionListener(new ActionListener(){

							public void actionPerformed(ActionEvent arg0) {
								System.exit(0);
							}});
					}
				}
			
		
			
			
				{
					jmAttrib = new JMenu();
					jMenuBar1.add(jmAttrib);
					jmAttrib.setText("Atributos");
					{
						jMenuItem3 = new JMenuItem();
						jmAttrib.add(jMenuItem3);
						jMenuItem3.setText("Control Panel");
						
						jMenuItem3.addActionListener(
			
			new ActionListener()
			{
				
				public void actionPerformed(ActionEvent event)
				{
					
					
					System.out.println("Internal Frame" );
					JInternalFrame frame= new JInternalFrame(
						"Control Panel",true,true,true,true);

						MyJPanel panel= new MyJPanel();
						frame.add(panel,BorderLayout.CENTER);
						frame.pack();
						
						theDesktop.add(frame);
						frame.setVisible(true);
						
						
				}//action
			}//inner
			);//call action
						
						
						
						
						
					}//cierre de atr
					
				}
			}
			pack();
			this.setSize(629, 374);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////
/*


DE AQUI EN ADELANTE se encuentran con las class de los atributos hasta la linea 428
esta la modificacion de los check box.
y no se siquieran ahi los botones o los dejemos de adorno.
lo demas son para hacer display y cosas.



*/
///////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

class MyJPanel extends JPanel
{
	private static Random generator=new Random();
	private ImageIcon picture;
	private String[] images={ "1.jpg","2.jpg","3.jpg","4.jpg","5.jpg"};
private JCheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
private JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9;
private JTextField t1;
private JButton b1,b2,b3,b4,b5,b6;
private JTextArea ta1;
private String a,b,c,d;
private JPanel p1,p2,p3,p4,p5,p6;
private static String OK = "ok";
    private static String HELP = "help";

    private JFrame controllingFrame; //needed for dialogs
    private JPasswordField passwordField;

 private boolean sign= false;

	
	public MyJPanel()
	{   
		int randomNumber =generator.nextInt(5);
		picture = new ImageIcon(images[randomNumber]);
     // integral gama
     
     
     passwordField = new JPasswordField(10);
        passwordField.setActionCommand(OK);
        passwordField.addActionListener(	
			new ActionListener()
			{
				
				public void actionPerformed(ActionEvent event)
				{
        	      char[] input = passwordField.getPassword();
            if (isPasswordCorrect(input)) {
                JOptionPane.showMessageDialog(controllingFrame,
                    "     Access Granted"); sign=true;p1.setVisible(sign);
					p2.setVisible(sign);p3.setVisible(sign);p4.setVisible(sign);p5.setVisible(sign);
					p6.setVisible(sign); repaint();
            } else {
                JOptionPane.showMessageDialog(controllingFrame,
                    "Invalid password. Try again.",
                    "Cognitive Error Message",
                    JOptionPane.ERROR_MESSAGE);
            }

            //Zero out the possible password, for security.
            Arrays.fill(input, '0');

            passwordField.selectAll();
            resetFocus();
        	
        	     }
        	     
        	     }
        	     );
        	
        	
        	
        	
        	
        	

        JLabel label = new JLabel("Enter the password: ");
        label.setLabelFor(passwordField);

       JComponent buttonPane = createButtonPanel();

        //Lay out everything.
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(passwordField);

        add(textPane);
        add(buttonPane);

Image ball ;
	ball = new ImageIcon("pass.jpeg").getImage();
              repaint(); //call paint
//setDefaultCloseOperation(EXIT_ON_CLOSE);
setLayout(new FlowLayout());
	
l1=new JLabel ("Atirbutos");
t1=new JTextField(20);



b1=new JButton("Buscar en Amigos");
b2=new JButton("Buscar en Red");
b4=new JButton("Panel de Control");
b5=new JButton("Log out");

c1=new JCheckBox("Musica");
c2=new JCheckBox("Comida");
c3=new JCheckBox("Moda");
c4=new JCheckBox("Fitness");
c5=new JCheckBox("juegos");
c6=new JCheckBox("Deportes");
c7=new JCheckBox("Cultura");
c8=new JCheckBox("Arte");
c9=new JCheckBox("Ciencia Cognitiva");
c10=new JCheckBox("Otros");
ta1= new JTextArea(20,30);
ta1.setEditable(false);

p1=new JPanel();
	p1.setPreferredSize(new Dimension(500,50));
//	p1.setBackground(Color.white);
	p1.add(c1);
	p1.add(c2);
	p1.add(c3);
	p1.add(c4);
	p1.add(c5);
	
	
	p2=new JPanel();
	p2.setPreferredSize(new Dimension(500,50));
//	p2.setBackground(Color.white);
	p2.add(c6);
	p2.add(c7);
	p2.add(c8);
	p2.add(c9);
	p2.add(c10);
	
	
	p3=new JPanel();
	p3.setPreferredSize(new Dimension(500,50));
//	p3.setBackground(Color.white);
    p3.add(l1);
    
    p4=new JPanel();
	p4.setPreferredSize(new Dimension(500,50));
//	p4.setBackground(Color.white);
    p4.add(t1);
    
    p5=new JPanel();
	p5.setPreferredSize(new Dimension(500,50));
//	p5.setBackground(Color.white);
    p5.add(b1);
	p5.add(b2);

	p5.add(b4);
	p5.add(b5);

    p6=new JPanel();
	p6.setPreferredSize(new Dimension(500,50));
//	p6.setBackground(Color.white);
    p6.add(ta1);




add(p3);
add(p1);
	add(p2);
	add(p5);
add(p6);

ta1.setText("Aqui va tentativamente el Graph");



p1.setVisible(sign);
p2.setVisible(sign);
p3.setVisible(sign);

p5.setVisible(sign);
p6.setVisible(sign);


	
	}
	protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0,1));
        JButton okButton = new JButton("Log In");
        JButton helpButton = new JButton("Help");

        okButton.setActionCommand(OK);
        helpButton.setActionCommand(HELP);
        okButton.addActionListener(	new ActionListener()
			{
				
				public void actionPerformed(ActionEvent event)
				{
        	      char[] input = passwordField.getPassword();
            if (isPasswordCorrect(input)) {
                JOptionPane.showMessageDialog(controllingFrame,
                    "     Access Granted"); sign=true;p1.setVisible(sign);
					p2.setVisible(sign);p3.setVisible(sign);p4.setVisible(sign);p5.setVisible(sign);
					p6.setVisible(sign); repaint();
            } else {
                JOptionPane.showMessageDialog(controllingFrame,
                    "Invalid password. Try again.",
                    "Cognitive Error Message",
                    JOptionPane.ERROR_MESSAGE);
            }

            //Zero out the possible password, for security.
            Arrays.fill(input, '0');

            passwordField.selectAll();
            resetFocus();
        	
        	     }
        	     
        	     });
       
       
        helpButton.addActionListener(	new ActionListener()
			{
				
				public void actionPerformed(ActionEvent event)
				{
					
					 //The user has asked for help.
            JOptionPane.showMessageDialog(controllingFrame,
                "Si usted a olvidado su password envie un MSG\n"
              + "Recuerde el uso de minusculas y Mayusculas\n"
              + "No proporcione su Password a otro usuario del Sistema\n"
              + "No trate de acceder a otras cuentas, recuerde... el sistema es para hacer amigos");
        
					
					
					}
					}
					);
					
			
        p.add(okButton);
        p.add(helpButton);

        return p;
    }
    protected void resetFocus() {
        passwordField.requestFocusInWindow();
    }
    
private static boolean isPasswordCorrect(char[] input) {
        boolean isCorrect = true;
        char[] correctPassword = { 'c', 'o', 'g', 'n', 'i', 't', 'y' };

        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals (input, correctPassword);
        }

        //Zero out the password.
        Arrays.fill(correctPassword,'0');

        return isCorrect;
    }
	public void paintComponent (Graphics g)
	{Toolkit toolkit = Toolkit.getDefaultToolkit() ;
     Image image1 = toolkit.getImage("pass.JPG") ;
     
		super.paintComponent(g);
		picture.paintIcon(this,g,0,0);
		g.drawImage(image1,0,0, this);
	}
	public Dimension getPreferredSize()
	{
		return new Dimension(picture.getIconWidth(),
		picture.getIconHeight());
	}
	}