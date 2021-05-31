import javax.swing.JDialog;


/**
 * @author heinecke
 *
 */
public class menue extends JDialog
{
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton3 = null;
	private int x,y;
	private Uhr uhr;
	/**
	 * This is the default constructor
	 */
	public menue(int x,int y,Uhr _uhr)
	{	
		super();
		this.setUndecorated(true);
		this.setLocation(x,y);
		this.x=x;
		this.y=y;
		this.setModal(true);
		uhr=_uhr;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.getContentPane().setLayout(new java.awt.BorderLayout());
		this.getContentPane().add(getJLabel(), java.awt.BorderLayout.NORTH);
		this.getContentPane().add(getJButton(), java.awt.BorderLayout.WEST);
		this.getContentPane().add(getJButton1(), java.awt.BorderLayout.EAST);
		this.getContentPane().add(getJButton3(), java.awt.BorderLayout.SOUTH);
		this.setSize(131, 61);
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Exit Desktopclock ?");
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		}
		return jLabel;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setText("yes");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					System.exit(0);
					
					}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new javax.swing.JButton();
			jButton1.setText("no");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					disposeme();}
			});
		}
		return jButton1;
	}
	
	
	/**
		 * This method initializes jButton3
		 * 
		 * @return javax.swing.JButton
		 */
		private javax.swing.JButton getJButton3() {
			if(jButton3 == null) {
				jButton3 = new javax.swing.JButton();
				jButton3.setText("Set Alarm Clock");
				jButton3.addActionListener(new java.awt.event.ActionListener() { 
					public void actionPerformed(java.awt.event.ActionEvent e) {    
						setAlarm();
						disposeme();}
				});
			}
			return jButton3;
		}
	

private void disposeme()
{
	this.dispose();
}

private void setAlarm()
{
	SetAlarm alarm = new SetAlarm(x,y, uhr);
	alarm.setVisible(true); 
}


}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
