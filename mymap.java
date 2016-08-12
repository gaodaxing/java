package module1;

import java.awt.Color;
import java.util.*;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.*;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.*;
import parsing.ParseFeed;
import processing.core.*;

public class mymap extends PApplet {
	private UnfoldingMap map;
	private String earthquakeurl="http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	public void setup(){
		size(800,600,OPENGL);
		map=new UnfoldingMap(this,50,50,700,500,new Google.GoogleMapProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this,map);
		
		List<SimplePointMarker> markers=new ArrayList<SimplePointMarker>();
		List<PointFeature> earthquakes=ParseFeed.parseEarthquake(this, earthquakeurl);
		
		for( int i=0; i < earthquakes.size();i++){
			PointFeature pf=earthquakes.get(i);
			float mag=Float.parseFloat(pf.getProperty("magnitude").toString());
			System.out.println(mag);
			System.out.println(pf.getLocation());
			markers.add(createMarker(pf));
			int yellow=color(255,255,0);
			int blue=color(0,0,255);
			int red=color(255,0,0);
			if(mag>5.0){
				markers.get(i).setColor(red);
				markers.get(i).setRadius(15);
				
			}
			else if(mag>4.0){
				markers.get(i).setColor(yellow);
				markers.get(i).setRadius(10);
			}
			else {
				markers.get(i).setColor(blue);
				markers.get(i).setRadius(5);
				
			}
			map.addMarker(markers.get(i));
			
		}
		
		
	}
	
	public void draw(){
		background(10);
		map.draw();
		addKey();
	}
	
	private SimplePointMarker createMarker(PointFeature feature){
		return new SimplePointMarker(feature.getLocation(),feature.getProperties());
	}
	
	public void addKey(){
		fill(250,250,250);
		rect(60,60,150,100);
		textAlign(LEFT,CENTER);
		textSize(12);
		fill(0,0,0);
		text("Earthquake Key",70,70);
		fill(250,0,0);
		ellipse(90,90,15,15);
		fill(0,0,0);
		text("Mag>5",100,90);
		fill(250,250,0);
		ellipse(90,115,10,10);
		fill(0,0,0);
		text("Mag>4 <5",100,115);
		fill(0,0,250);
		ellipse(90,140,5,5);
		fill(0,0,0);
		text("Mag<4",100,140);
		
		
			
		
	}

}
