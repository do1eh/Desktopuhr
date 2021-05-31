import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;


/**
 * @author heinecke
 *
 */
public class SetAlarm extends JFrame
{
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JSpinner jSpinnerHour = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JSpinner jSpinnerMinute = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JEditorPane jEditorPane = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JCheckBox jCheckBox = null;
	
	
	private Uhr uhr;
	private javax.swing.JScrollPane jScrollPane = null;
	/**
	 * This is the default constructor
	 */
	public SetAlarm(int x,int y, Uhr _uhr)
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
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
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
			jLabel.setText(" Set Alarm");
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
			jLabel1.setText("Alarmtime");
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
			jSpinnerHour = new javax.swing.JSpinner(new SpinnerNumberModel(uhr.getAlarmhour(),0,23,1));
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
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("Message:");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JSpinner getJSpinnerMinute() {
		if(jSpinnerMinute == null) {
			jSpinnerMinute = new javax.swing.JSpinner(new SpinnerNumberModel(uhr.getAlarmminute(),0,60,1));
				
		}
		return jSpinnerMinute;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.add(getJLabel3(), null);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.add(getJCheckBox(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(295,140));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private javax.swing.JEditorPane getJEditorPane() {
		if(jEditorPane == null) {
			jEditorPane = new javax.swing.JEditorPane();
			jEditorPane.setPreferredSize(new java.awt.Dimension(225,80));
			jEditorPane.setText(uhr.getAlarmmessage());
			
		}
		return jEditorPane;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jButton.setText("Save settings");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					setAlarm();
					
					
					}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox() {
		if(jCheckBox == null) {
			jCheckBox = new javax.swing.JCheckBox();
			jCheckBox.setText("activate Alarm");
			jCheckBox.setSelected(uhr.isAlarm());
		}
		return jCheckBox;
	}
	
	
private void setAlarm()
{
	Integer alarmhour=(Integer) jSpinnerHour.getValue();
	Integer alarmminute=(Integer) jSpinnerMinute.getValue();
	uhr.setAlarmhour(alarmhour.intValue());
	uhr.setAlarmminute(alarmminute.intValue());	
	uhr.setAlarm(jCheckBox.isSelected());
	uhr.setAlarmmessage(jEditorPane.getText());
	uhr.setShown(0); 
	
	this.dispose();
}
	
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJEditorPane());
		}
		return jScrollPane;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
