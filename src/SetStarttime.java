import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;


/**
 * @author heinecke
 *
 */
public class SetStarttime extends JFrame
{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JSpinner jSpinnerHour = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JSpinner jSpinnerMinute = null;
	private javax.swing.JPanel jPanel1 = null;
	
	private javax.swing.JButton jButton = null;
	
	
	
	private Uhr uhr;
	private javax.swing.JScrollPane jScrollPane = null;
	/**
	 * This is the default constructor
	 */
	public SetStarttime(int x,int y, Uhr _uhr)
	{
		super();
		uhr=_uhr;
		this.setLocation(x-400,y);
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 223);
		this.setContentPane(getJContentPane());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJLabel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJButton(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText(" Set Starttime");
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		}
		return jLabel;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.setLayout(new java.awt.FlowLayout());
			jPanel.add(getJLabel1(), null);
			jPanel.add(getJSpinnerhour(), null);
			jPanel.add(getJLabel2(), null);
			jPanel.add(getJSpinnerMinute(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(55,29));
		}
		return jPanel;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Starttime");
		}
		return jLabel1;
	}
	/**
	 * This method initializes jSpinnerhour
	 * 
	 * @return javax.swing.JSpinner
	 */
	private javax.swing.JSpinner getJSpinnerhour() {
		if(jSpinnerHour == null) {
			jSpinnerHour = new javax.swing.JSpinner(new SpinnerNumberModel(uhr.getStarthour(),0,23,1));
		}
		return jSpinnerHour;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText(":");
		}
		return jLabel2;
	}
	
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JSpinner getJSpinnerMinute() {
		if(jSpinnerMinute == null) {
			jSpinnerMinute = new javax.swing.JSpinner(new SpinnerNumberModel(uhr.getStartminute(),0,60,1));
				
		}
		return jSpinnerMinute;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jButton.setText("Save settings");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					setStart();
					
					
					}
			});
		}
		return jButton;
	}
	
	
	
private void setStart()
{
	Integer starthour=(Integer) jSpinnerHour.getValue();
	Integer startminute=(Integer) jSpinnerMinute.getValue();
	uhr.setStarthour(starthour.intValue());
	uhr.setStartminute(startminute.intValue());
	uhr.updateEndzeit();
	uhr.setShown(0); 
	
	this.dispose();
}
	
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
