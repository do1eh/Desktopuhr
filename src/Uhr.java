import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.JDialog;

/**
 * DESKTOP-UHR
 * @author Ralf Heinecke
 * 
 * Eine runde Analoguhr mit Alarmfunktion, die den Widgets von Windows Vista 
 * nachempfungen ist. Die Uhr hat keinen Fensterramen und kein Symbol in der
 * Taskleiste.
 *  
 *  Startkonfiuration:
 *  Um zu skalieren (150% kleiner Monitor) 
 *  "c:\Program Files\Zulu\zulu-11\bin\java" -jar Desktopuhr.jar -Dsun.java2d.win.uiScaleX=150% -Dsun.java2d.win.uiScaleY=150%
 *  Skalieren komplett ausschalten:
 *  -Dsun.java2d.uiScale.enabled=false
 *  
 *  In einer Batch Datei wird immer der zweite Parameter verschluckt wenn er gleich beginnt.
 *  Hier muss das so gemacht werden:
 *  
 *  set xvar=-Dsun.java2d.win.uiScaleX=150%%
 *  set yvar=-Dsun.java2d.win.uiScaleY=150%%
 *  start "" "c:\Program Files\Zulu\zulu11.37.19fx\bin\javaw" %xvar% %yvar% -jar C:\tools\Desktopuhr\Desktopuhr.jar 
 *  
 * Historie:
 * Version V1.0		05.04.2007 Erste Version
 * Version V1.1		02.07.2007 Bugfixes
 * Verison V1.2		09.07.2009 Standardalarme
 * Version V1.3		21.05.2013 Feierabend countdown
 * Version V1.4		29.07.2015 Feierabend als Alarm, Erweiterung und Überarbeitung Standardalarme
 * Version V1.5     	2018 	   Startzeit in Datei Speicher
 * Version V1.6     	2019       Restzeit bis zum Alarm anzeigen
 * Version V1.7     	25.06.2020 Datum + Multiscreen + 150% Skalierung wegen windows10
 */
public class Uhr extends JDialog {

	int[] sx = new int[4];
	int[] sy = new int[4];
	int[] mx = new int[4];
	int[] my = new int[4];

	double x3;
	double y3;
	private int mouseX, mouseY, mousedDX, mouseDX;
	private int dx, dy, x, y;
	private Image image;
	
	private Image bufferimage;
	private Graphics2D g2d;
	private Graphics grafik;
	private boolean drag = false;
	int shown = 0;
	private int SOLLMINUTEN = 438;
	private int alarmrestzeitinminuten=0;

	// Alarm

	private int[] alarmhour = new int[5];
	private int[] alarmminute = new int[5];;
	private boolean alarm = false;
	private String[] alarmmessage = new String[5];;

	private GregorianCalendar startzeit;
	private String datum;
	private GregorianCalendar endzeit;

	public Uhr() {

		// aktuelle Zeit holen
		GregorianCalendar jetzt = new GregorianCalendar();

		//startzeit holen
		startzeit=initStartzeit();
		endzeit=initStartzeit();
		
		// Zeitzone setzen
		jetzt.setTimeZone(TimeZone.getTimeZone("ECT"));

		int wochentag = jetzt.get(GregorianCalendar.DAY_OF_WEEK);

		
		//Initalisieren des Einstellbaren Alarms (Kann auch vorbelegt werden.
		//wenn z.B. der erste Termin des Tages immer gleich ist.)
		alarmhour[0] = 00;
		alarmminute[0] = 00;
		alarmmessage[0] = " ";

		// Standardalarme setzen (max 4 möglich)
		// Mittagspause

		alarmhour[1] = 12;
		alarmminute[1] = 00;
		alarmmessage[1] = "Mittagspause !";

	

		endzeit.add(GregorianCalendar.MINUTE, SOLLMINUTEN);
		// Feierabend
		alarmhour[2] = endzeit.get(GregorianCalendar.HOUR_OF_DAY)+1;
		alarmminute[2] = endzeit.get(GregorianCalendar.MINUTE);
		alarmmessage[2] = "Feierabend !";
		
		// Releasebesprechung
//		 if (wochentag==GregorianCalendar.MONDAY)
//		 {
//		     alarmhour[3]=9;
//		     alarmminute[3]=55;
//		     alarmmessage[3]="Release Besprechung !";
//		 }
		
		//Ein Alarm ist noch frei (Achtung: Relesebesprechung muss dann [4] werden,
		 //da dieser Alarm nicht immer da ist.
		
		//alarmhour[3] = 12;
		//alarmminute[3] = 00;
		//alarmmessage[3] = "Was auch immer !";

		this.setUndecorated(true);

		//Alte Variante
		//Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Bessere Variante
		//GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		//int screenwidth = gd.getDisplayMode().getWidth();
		//int screenheight = gd.getDisplayMode().getHeight();
		
		//Multiscreenvariante:
		int screenwidth=getMaximumScreenBounds().width;
		
		this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		
		this.setSize(200, 200);
		//this.setLocation(screensize.width - 200, 5);
		this.setLocation(screenwidth - 200, 5);
		background(this.getX(), this.getY());
		x = this.getX();
		y = this.getY();

		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent event) {
				moveWindow(dx, dy);
				dx = 0;
				dy = 0;
				mouseX = 0;
				mouseY = 0;
			};

			public void mouseEntered(MouseEvent event) {
			};

			public void mousePressed(MouseEvent event) {
			};

			public void mouseClicked(MouseEvent event) {
				if (event.getButton() == MouseEvent.BUTTON3) {
					showmenue();

				}
			};

			public void mouseExited(MouseEvent event) {
			};

		}

		);

		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {

				drag = true;

				if (event.getX() > mouseX) {
					dx += event.getX() - mouseX;
				} else {
					dx -= mouseX - event.getX();
				}

				if (event.getY() > mouseY) {
					dy += event.getY() - mouseY;
				} else {
					dy -= mouseY - event.getY();
				}

				mouseX = event.getX();
				mouseY = event.getY();

			};

			public void mouseMoved(MouseEvent event) {
			};

		});
	}

	private void moveWindow(int dx, int dy)

	{
		if (drag) {
			this.setVisible(false);

			try {

				Thread.sleep(10);
			} catch (Exception e) {
			}

			background(this.getX() + dx - 100, this.getY() + dy - 100);

			this.setLocation(this.getX() + dx - 100, this.getY() + dy - 100);
			drag = false;
			this.setVisible(true);
			x = this.getX();
			y = this.getY();
			//System.out.println("drag:"+x+":"+y);
		}
		;
 
	}

	public void background(int x, int y)

	{

		try {
		    //System.out.println(x+":"+y);
			image = new Robot().createScreenCapture(new Rectangle(x, y, 200,200));
		    //multiimage = new Robot().createMultiResolutionScreenCapture(new Rectangle(x, y, 200,200));
		    
		} catch (Exception e) {
		}
		;

	}

	public void init() {
		// Zeichne zuerst in einen Puffer um flackern zu verhindern
		bufferimage = createImage(200, 200);
		grafik = bufferimage.getGraphics();
		paintUhr(grafik);

	}

	public void paint(Graphics g) {
		init();
		g.drawImage(bufferimage, 0, 0, this);

	};

	public void paintUhr(Graphics g) {

		sx[0] = 100;
		sy[0] = 100;
		mx[0] = 100;
		my[0] = 100;

		// größe festlegen;
		int faktor = 50;

		// aktuelle Zeit holen
		GregorianCalendar jetzt = new GregorianCalendar();

		// Zeitzone setzen
		jetzt.setTimeZone(TimeZone.getTimeZone("ECT"));

		int stunde = jetzt.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = jetzt.get(GregorianCalendar.MINUTE);
		int sekunde = jetzt.get(GregorianCalendar.SECOND);
		int tag = jetzt.get(GregorianCalendar.DAY_OF_MONTH);
		int monat = jetzt.get(GregorianCalendar.MONTH) + 1;
		int jahr = jetzt.get(GregorianCalendar.YEAR) ;
		String wochentag= Wochentag.getWochentageByID(jetzt.get(GregorianCalendar.DAY_OF_WEEK)).getName();

		String datum = wochentag +" "+ tag + "." + monat + "." + jahr;

		// Winkel berechnen
		double stundenwinkel;
		double minutenwinkel;
		double sekundenwinkel;

		stundenwinkel = (((0.5) * ((stunde * 60) + minute)) - 90) * Math.PI
				/ 180;
		minutenwinkel = ((360 / 60) * minute - 90) * Math.PI / 180;
		sekundenwinkel = ((360 / 60) * sekunde - 90) * Math.PI / 180;

		// Stundenzeiger

		sy[1] = (int) (100 + Math.sin(stundenwinkel - .2) * (faktor - 29));
		sx[1] = (int) (100 + Math.cos(stundenwinkel - .2) * (faktor - 29));

		sy[2] = (int) (100 + Math.sin(stundenwinkel) * (faktor - 20));
		sx[2] = (int) (100 + Math.cos(stundenwinkel) * (faktor - 20));

		sy[3] = (int) (100 + Math.sin(stundenwinkel + .2) * (faktor - 29));
		sx[3] = (int) (100 + Math.cos(stundenwinkel + .2) * (faktor - 29));

		// Minutenzeiger
		my[1] = (int) (100 + Math.sin(minutenwinkel - .1) * (faktor - 10));
		mx[1] = (int) (100 + Math.cos(minutenwinkel - .1) * (faktor - 10));

		my[2] = (int) (100 + Math.sin(minutenwinkel) * (faktor));
		mx[2] = (int) (100 + Math.cos(minutenwinkel) * (faktor));

		my[3] = (int) (100 + Math.sin(minutenwinkel + .1) * (faktor - 10));
		mx[3] = (int) (100 + Math.cos(minutenwinkel + .1) * (faktor - 10));

		// Sekundenzeiger
		y3 = 100 + Math.sin(sekundenwinkel) * faktor;
		x3 = 100 + Math.cos(sekundenwinkel) * faktor;

		int ox = 100;
		int oy = 100;
		int orx = 60;
		int ory = 60;

		int a = ox - orx;
		int b = oy - ory;
		int c = orx + orx;
		int d = ory + ory;

		g2d = (Graphics2D) g;

		RenderingHints renderHints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		renderHints.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(renderHints);

		g.drawImage(image, 0, 0, 200, 200, this);

		// Hintergrund zahlen
		GradientPaint gradient2 = new GradientPaint(35, 65, Color.black, 155,
				145, new Color(40, 37, 137));
		g2d.setPaint(gradient2);
		g2d.fillOval(a - 30, b - 30, c + 60, d + 60);

		// Hintergrund zeiger
		GradientPaint gradient1 = new GradientPaint(35, 65, Color.LIGHT_GRAY,
				155, 145, Color.DARK_GRAY);
		g2d.setPaint(gradient1);
		g2d.fillOval(a, b, c, d);

		// Alarmsymbol

		if (alarm) {
			GradientPaint gradientalarm = new GradientPaint(00, 00,
					Color.LIGHT_GRAY, 40, 40, Color.DARK_GRAY);
			g2d.setPaint(gradientalarm);
			g2d.fillOval(0, 0, 40, 40);

			g2d.setColor(Color.WHITE);
			g2d.fillOval(7, 15, 10, 10);

			g2d.drawArc(2, 10, 20, 20, 30, -60);
			g2d.drawArc(2, 8, 25, 25, 30, -60);
			g2d.drawArc(2, 6, 30, 30, 30, -60);

		}

		// Zeiger (Stunde,minute,sekunde)
		GradientPaint gradient = new GradientPaint(35, 65, Color.white, 155,
				145, Color.blue);
		g2d.setPaint(gradient);
		g2d.fillPolygon(sx, sy, 4);
		g.fillPolygon(mx, my, 4);
		g.setColor(Color.RED);
		g.drawLine(100, 100, (int) x3, (int) y3);

		// Restzeit
		g.setFont(new Font("ARIAL", Font.PLAIN, 20));
		String restzeit = this.getRestzeit(SOLLMINUTEN);
		if (restzeit.startsWith("+")) 
		{
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		g2d.drawString(restzeit, 60, 135);

		//Zeit bis Alarm
		if(alarm)
		{
		    g.setFont(new Font("ARIAL", Font.PLAIN, 18));
			String alarmrestzeit = this.getAlarmRestzeit();
			if (alarmrestzeitinminuten<0) 
			{
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.GREEN);
			}
			g2d.drawString(alarmrestzeit, 45, 85); 
		}
		
		//g.setColor(Color.BLUE);
		g2d.setPaint(gradient);
		g2d.fill(new RoundRectangle2D.Double(135, 168, 50, 20,15,15));
		g2d.fill(new RoundRectangle2D.Double(15, 168, 50, 20,15,15));
		//AffineTransform orig = g2d.getTransform();
		g.setColor(Color.WHITE);
		g.setFont(new Font("ARIAL", Font.BOLD, 16));
		//g2d.rotate(Math.toRadians(45));
		
		g.setColor(Color.WHITE);
		//Start, Endzeit und Datum über die Uhr schreiben
		g2d.drawString(timeToString(endzeit.getTimeInMillis()+(3600*1000),
			"HH:mm"), 138, 185);
		g2d.drawString(timeToString(startzeit.getTimeInMillis(),
			"HH:mm"), 20, 185);
		g.setColor(Color.WHITE);
		g.setFont(new Font("ARIAL", Font.BOLD, 15));
		g2d.drawString(datum, 60, 11);
		
		
		//g2d.setTransform(orig);
		// Zahlen
		GradientPaint gradient3 = new GradientPaint(35, 65, Color.white, 155,
				145, new Color(150, 150, 150));
		g2d.setPaint(gradient3);

		g.drawString("1", 130, 41);
		g.drawString("2", 155, 65);
		g.drawString("3", 166, 105);
		g.drawString("4", 155, 145);
		g.drawString("5", 130, 166);
		g.drawString("6", 95, 178);
		g.drawString("7", 58, 166);
		g.drawString("8", 35, 140);
		g.drawString("9", 28, 102);
		g.drawString("10", 35, 65);
		g.drawString("11", 59, 41);
		g.drawString("12", 92, 33);

		// Alarmauslösung

		for (int i = 0; i < alarmhour.length; i++) {

			if (stunde == alarmhour[i] && minute == alarmminute[i]
					&& shown == 0) {
				shown = i + 1;
				AlarmMessage am = new AlarmMessage(x, y, alarmhour[i],
						alarmminute[i], alarmmessage[i]);
				am.setVisible(true);
			}

			// shown nach einer Minute wieder auf false setzen
			if (shown == i + 1 && minute > alarmminute[i]) {
				shown = 0;
			}
		}

	}

	public void animation() {

		while (true) {

			try {

				Thread.sleep(100);
			} catch (Exception e) {
			}

			this.repaint();
		}

	}

	public static void main(String[] args) {

		Uhr f = new Uhr();

		//f.endzeit.add(GregorianCalendar.MINUTE, f.SOLLMINUTEN);
		f.show();
		f.animation();

	}

	/**
	 * @return
	 */
	public boolean isAlarm() {
		return alarm;
	}

	/**
	 * @return
	 */
	public int getAlarmhour() {
		return alarmhour[0];
	}

	/**
	 * @return
	 */
	public String getAlarmmessage() {
		return alarmmessage[0];
	}

	/**
	 * @return
	 */
	public int getAlarmminute() {
		return alarmminute[0];
	}

	/**
	 * @param b
	 */
	public void setAlarm(boolean b) {
		alarm = b;
	}

	/**
	 * @param i
	 */
	public void setAlarmhour(int i) {
		alarmhour[0] = i;
	}

	/**
	 * @param string
	 */
	public void setAlarmmessage(String string) {
		alarmmessage[0] = string;
	}

	/**
	 * @param i
	 */
	public void setAlarmminute(int i) {
		alarmminute[0] = i;
	}

	private void showmenue() {

		menue menu = new menue(x + 40, y + 80, this);
		menu.show();
	}

	/**
	 * @param b
	 */
	public void setShown(int b) {
		shown = b;
	}

	private GregorianCalendar getStartzeit() {
		return startzeit;
	}

	private String getRestzeit(int sollminuten) {

		long jetzt = new Date().getTime();
		String rueckgabe = "";

		if (endzeit.getTimeInMillis()+(3600*1000) > jetzt) {
			rueckgabe = timeToString(endzeit.getTimeInMillis() - jetzt,
					"HH:mm:ss");
		} else {
			rueckgabe = "+"
					+ timeToString(jetzt - endzeit.getTimeInMillis()-(2*(3600*1000)),
							"HH:mm:ss");
		}
		return rueckgabe;

	}
	
	private String getAlarmRestzeit() {

		GregorianCalendar jetzt=new GregorianCalendar();
		String rueckgabe = "";
		String message="";
		if(getAlarmmessage().length()<8)
		    {
		    message=getAlarmmessage()+" ";
		    }
		else
		{
		    
		    message=getAlarmmessage().substring(0,8)+" ";
		}
		
		rueckgabe+=message;
		
		
		setAlarmrestzeitinminuten(((getAlarmhour()*60)-jetzt.get(Calendar.HOUR_OF_DAY)*60)+(getAlarmminute()-jetzt.get(Calendar.MINUTE)));
		
		int alarmmin=getAlarmminute();
		int alarmstd=getAlarmhour()-jetzt.get(Calendar.HOUR_OF_DAY);
		
		if(getAlarmminute()<jetzt.get(Calendar.MINUTE) && getAlarmrestzeitinminuten()>=0)
		{
		    alarmmin+=60;
		    alarmstd-=1;
		}
		
		rueckgabe+=alarmstd+":";
		
		
		if(alarmmin-jetzt.get(Calendar.MINUTE)<10)
		{
		    rueckgabe+=0;
		}
		rueckgabe+=(alarmmin-jetzt.get(Calendar.MINUTE))+"";
		
		if(getAlarmrestzeitinminuten()<0)
		{
		    
		    rueckgabe= message+"-"+getAlarmrestzeitinminuten()*-1;
		}
		
		return rueckgabe;

	}

	public static String timeToString(long time, String format) {
		String string = "";

		if (format.equals(""))
			format = "dd.MM.yyyy";

		SimpleDateFormat sdfToString = new SimpleDateFormat(format);
		try {
			string = sdfToString.format(new Date(time));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return string;

	}

	public GregorianCalendar initStartzeit()
	{
	   
	    GregorianCalendar startzeit=new GregorianCalendar();
	    BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File("c:/temp/startzeit.txt")));
			String line = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
			String jetztstring = sdf.format(new Date());
			
			
			if((line = br.readLine()) != null)
			{
				//Wenn das Dtaum heute ist, setze startzeit auf den Wert in der Datei
				if(line.startsWith(jetztstring.substring(0,10)))
				{
				   startzeit=toDate(line, "");  
				}
				//ansonsten überschreibe die Datei mit aktuellem wert
				else
				{
				    try
				    {
					br.close();
				    } catch (IOException e)
				    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				    }
				    
				    PrintWriter pWriter = null;
				        try {
				            pWriter = new PrintWriter(new BufferedWriter(new FileWriter("c:/temp/startzeit.txt")));
				            pWriter.println(jetztstring);
				        } catch (IOException ioe) {
				            ioe.printStackTrace();
				        } finally {
				            if (pWriter != null){
				                pWriter.flush();
				                pWriter.close();
				            }
				        }  
				}
				
				
			}
			else //Nur für den Fall dass die Datei leer ist
			{
			    try
			    {
				br.close();
			    } catch (IOException e)
			    {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
			    
			    PrintWriter pWriter = null;
			        try {
			            pWriter = new PrintWriter(new BufferedWriter(new FileWriter("c:/temp/startzeit.txt")));
			            pWriter.println(jetztstring);
			        } catch (IOException ioe) {
			            ioe.printStackTrace();
			        } finally {
			            if (pWriter != null){
			                pWriter.flush();
			                pWriter.close();
			            }
			        }  
			}
			
			
		
		}
		catch (Exception e) {
		    // TODO: handle exception
		    e.printStackTrace();
		}

	    
		return startzeit;

	}
	
	public static GregorianCalendar toDate(String datum, String format) {
		Date date = null;

		if (format.equals(""))
			format = "yyyy.MM.dd-HH:mm:ss";

		SimpleDateFormat sdfToDate = new SimpleDateFormat(format);
		
		//nicht rechnen..also kein 32.01.2006 zu 01.02.2006 umrechnen
		sdfToDate.setLenient(false);
		
		try {
			date = sdfToDate.parse(datum);

		} catch (ParseException e) {
			//e.printStackTrace();
			
		}

		GregorianCalendar rueckgabe=new GregorianCalendar();
		rueckgabe.setTime(date);
		return rueckgabe;

	}

	public int getAlarmrestzeitinminuten()
	{
	    return alarmrestzeitinminuten;
	}

	public void setAlarmrestzeitinminuten(int alarmrestzeitinminuten)
	{
	    this.alarmrestzeitinminuten = alarmrestzeitinminuten;
	}
	
	
	/**
	 * Bei Multiscreen die komplette Höhe und Breite über alle Monitore zurück geben
	 * @return
	 */
	private static Rectangle getMaximumScreenBounds() {
	    int minx=0, miny=0, maxx=0, maxy=0;
	    GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    for(GraphicsDevice device : environment.getScreenDevices()){
	        Rectangle bounds = device.getDefaultConfiguration().getBounds();
	        minx = Math.min(minx, bounds.x);
	        miny = Math.min(miny, bounds.y);
	        maxx = Math.max(maxx,  bounds.x+bounds.width);
	        maxy = Math.max(maxy, bounds.y+bounds.height);
	    }
	    return new Rectangle(minx, miny, maxx-minx, maxy-miny);
	}
	
	
}
