import java.util.GregorianCalendar;

public enum Wochentag
{

   
	MONTAG ("Mo", GregorianCalendar.MONDAY),
	DIENSTAG ("Di", GregorianCalendar.TUESDAY),
	MITTWOCH ("Mi", GregorianCalendar.WEDNESDAY),
	DONNERSTAG ("Do", GregorianCalendar.THURSDAY),
	FREITAG ("Fr", GregorianCalendar.FRIDAY),
	SAMSTAG ("Sa", GregorianCalendar.SATURDAY),
	SONNTAG ("So", GregorianCalendar.SUNDAY);
	
	
	private final String name;
	private final int wert;
	
	private Wochentag(String name, int wert) {
		this.name = name;
		this.wert = wert;
	}

	public String getName() {
		return name;
	}

	public int getWert() {
		return wert;
	}
	
	public static Wochentag getWochentageByID(int wert) {
		Wochentag wochentag = null;
		
		switch (wert) {
		case GregorianCalendar.MONDAY:
		    wochentag = MONTAG;
			break;
		case GregorianCalendar.TUESDAY:
		    wochentag = DIENSTAG;
			break;
		case GregorianCalendar.WEDNESDAY:
		    wochentag = MITTWOCH;
			break;
		case GregorianCalendar.THURSDAY:
		    wochentag = DONNERSTAG;
			break;
		case GregorianCalendar.FRIDAY:
		    wochentag = FREITAG;
			break;
		case GregorianCalendar.SATURDAY:
		    wochentag = SAMSTAG;
			break;
		case GregorianCalendar.SUNDAY:
		    wochentag = SONNTAG;
			break;
		
		}
		
		return wochentag;
	}
    
}
