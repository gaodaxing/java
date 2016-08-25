package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	private List<Marker> citymarkers=new ArrayList<Marker>();
	List<Marker> routeList;
	CommonMarker lastSelected;
	private String cityFile = "city-data.json";
	List<Feature> cities= GeoJSONReader.loadData(this, cityFile);
	boolean lastclicked=false;
	String button="Show all airports";
	CommonMarker lastClickedairport;
	
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
				
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		
		}

        
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			//System.out.println(sl.getLocations());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		for(Feature city : cities) {
			  citymarkers.add(new CityMarker(city));
			}
		
		hideairport();		
		showbig();
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		//map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		map.addMarkers(citymarkers);
		
	}
	
	public void showbig(){
		hideairport();
		for(Marker amarker:airportList){			
			for (Marker cmarker:citymarkers){
				if(amarker.getDistanceTo(cmarker.getLocation())<1000)amarker.setHidden(false);
			}
		}
	}
	
	public void hideairport(){
		for (Marker marker:airportList){
			marker.setHidden(true);
		}
	}
	
	public void unhideairport(){
		for (Marker marker:airportList){
			marker.setHidden(false);
		}
	}
	
	public void draw() {
		background(0);
		map.draw();
		addkey();
		
	}
	
	public void addkey(){
		fill(250,250,250);
		rect(50,10,200,35);
		fill(0,0,0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text(button, 70, 27);
		
		
	}
	
	public void mouseClicked(){
		if(lastclicked){lastclicked=false;
		showbig();
		button="Show all airports";
		}
		else{
			if(mouseX<250&mouseX>50&mouseY>10&mouseY<45){unhideairport();
			lastclicked=true;
			button="Show airports near big cities";}
			else {
			checkairportclicked(airportList);
			}
		}
	}
	
	public void checkairportclicked(List<Marker> airportList){
		for(Marker marker:airportList){
			if(marker.isInside(map, mouseX, mouseY)){
				lastClickedairport=(CommonMarker)marker;
				hideairport();
				marker.setHidden(false);
				showconnectedairport(marker);
				lastclicked=true;
				button="Show airports near big cities";
				break;				
			}
		}
	}
	int count=0;
	public void showconnectedairport(Marker marker){
		//System.out.println(marker.getId());
		List<Location> allloc=new ArrayList<Location>();
		for(Marker rmarker:routeList){
			List<Location> loc=((SimpleLinesMarker)rmarker).getLocations();
			if(marker.getLocation().equals(loc.get(0))){
				allloc.add(loc.get(1));
				count++;
				}			
			
		}
		System.out.println(count);
		for(Marker airportmarker:airportList){
			for(Location x:allloc){
			if(airportmarker.getLocation().equals(x)){
				airportmarker.setHidden(false);
			}
				
		}
		}
		
	}
	
	
	public void mouseMoved(){
		if(lastSelected!=null){
			lastSelected.setSelected(false);
			lastSelected=null;
			
		}
		else mouseifHover(airportList);
		if(lastSelected==null)mouseifHover(citymarkers);
	}
	
	public void mouseifHover(List<Marker> airportList){
		for(Marker amarker:airportList){
			if(amarker.isInside(map, mouseX, mouseY)){
				lastSelected=((CommonMarker)amarker);
				break;
			}		
		}
		for(Marker amarker:airportList){
			if((CommonMarker)amarker==lastSelected){
				amarker.setSelected(true);
			}
		}
		
	}
	
	

}
