import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class SCFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar jMenuBar1;
	private JPanel jPanel1;
	private JLabel lblStatus;
	private JLabel jLabel_IL;
	private JPanel pnlGraph;
	private JPanel pnlSearch;
	private JPanel jPanel2;
	private JList lstPersons;
	private JMenu jmAttrib;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem1;
	private JMenu jMenu1;

	
	//-- AGENTE
	private Agente agente;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SCFrame inst = new SCFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public SCFrame() {
		super();
		this.agente = new Agente();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			BorderLayout thisLayout = new BorderLayout();
			this.setTitle("Redes Sociales Cognitivas");
			getContentPane().setLayout(thisLayout);
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setPreferredSize(new java.awt.Dimension(655, 320));
				{
					lblStatus = new JLabel();
					jPanel1.add(lblStatus, BorderLayout.SOUTH);
					lblStatus.setText("Estatus");
				}
				{
					ListModel lstPersonsModel = 
						new DefaultComboBoxModel(
								new String[] { "Item One", "Item Two" });
					lstPersons = new JList();
					jPanel1.add(lstPersons, BorderLayout.WEST);
					lstPersons.setModel(lstPersonsModel);
					lstPersons.setPreferredSize(new java.awt.Dimension(121, 278));
				}
				{
					jPanel2 = new JPanel();
					BorderLayout jPanel2Layout = new BorderLayout();
					jPanel2.setLayout(jPanel2Layout);
					jPanel1.add(jPanel2, BorderLayout.CENTER);
					{
						pnlGraph = new JPanel();
						pnlGraph.add(this.agente.getGraph());
						pnlGraph.setSize(new Dimension( 530, 420 ));
						jPanel2.add(pnlGraph, BorderLayout.CENTER);
						{
							jLabel_IL = new JLabel("x");
							pnlGraph.add(jLabel_IL);
							jLabel_IL.setText("x");
							jLabel_IL.setPreferredSize(new java.awt.Dimension(514, 179));
						}
					}
					{
						pnlSearch = new JPanel();
						jPanel2.add(pnlSearch, BorderLayout.CENTER);
					}
				}
			}
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
				}
			}
			pack();
			this.setSize(629, 374);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
