package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		if(getCode().replace("\"","").equals(""))setHidden(true);
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(150,150,250);
		pg.strokeWeight(0);
		pg.ellipse(x, y, 5, 5);
				
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
        pg.pushStyle();		
		pg.fill(255, 255, 255);
		pg.rect(x-350, y-20, 350, 20);
		pg.fill(250,0,0);
		String displaytext,code,city,name,country;
		code=getCode().replace("\"","");
		name=getName().replace("\"","");		
		city=getCity().replace("\"","");
		country=getCountry().replace("\"","");
		displaytext=code+' '+'('+name+')'+','+city+','+country;
		//System.out.println(name);
		pg.text(displaytext, x-300, y-5);
		// show routes
		
		
	}
	
	public void showmenu(PGraphics pg, float x, float y){
		
	}
	
	private String getCountry(){
		return getStringProperty("country");
	}
	
	private String getCode(){
		return getStringProperty("code");
		}
	
	private String getName(){
		return getStringProperty("name");
		}
	
	private String getCity(){
		return getStringProperty("city");
		}
	
	private float getAltitude(){
		return Float.parseFloat(getProperty("altitude").toString());
		}
	
}
