import java.awt.Toolkit;

import javax.swing.JFrame;


/**
 * @author heinecke
 *
 */
public class AlarmMessage extends JFrame
{
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabelhour = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabelminute = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JEditorPane jEditorPane = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JButton jButton = null;
	/**
	 * This is the default constructor
	 */
	public AlarmMessage(int x, int y, int hour,int minute,String text)
	{
		super();
		this.setLocation(400,400);
		this.setAlwaysOnTop(true);
		initialize();
		
		if(hour<10)
		{
			jLabelhour.setText("0"+String.valueOf(hour));
		}
		else
		{
			jLabelhour.setText(String.valueOf(hour));
		}
		
		
		
		if(minute<10){
			jLabelminute.setText("0"+String.valueOf(minute));
		}else
		{
			jLabelminute.setText(String.valueOf(minute));
		}
		
		jEditorPane.setText(text);
		jEditorPane.setCaretPosition(0);
		Toolkit.getDefaultToolkit().beep(); 

	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("ALARM !!!!!!!");
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jLabelhour
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabelhour() {
		if(jLabelhour == null) {
			jLabelhour = new javax.swing.JLabel();
			jLabelhour.setText("JLabel");
			jLabelhour.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}
		return jLabelhour;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.add(getJLabelhour(), null);
			jPanel.add(getJLabel1(), null);
			jPanel.add(getJLabelminute(), null);
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
			jLabel1.setText(":");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}
		return jLabel1;
	}
	/**
	 * This method initializes jLabelminute
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabelminute() {
		if(jLabelminute == null) {
			jLabelminute = new javax.swing.JLabel();
			jLabelminute.setText("JLabel");
			jLabelminute.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}
		return jLabelminute;
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
	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private javax.swing.JEditorPane getJEditorPane() {
		if(jEditorPane == null) {
			jEditorPane = new javax.swing.JEditorPane();
			//jEditorPane.setContentType("text/html");
			jEditorPane.setEditable(false);
			
		}
		return jEditorPane;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.add(getJButton(), null);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setText("close");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					disposeme();
					}
			});
		}
		return jButton;
	}
	
	private void disposeme()
	{
		this.dispose();
	}
}
