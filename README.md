# java
Coursera course

Implement the Comparable interface in EarthquakeMarker:
implement the compareTo(EarthquakeMarker marker) method in the EarthquakeMarker class so that it sorts earthquakes in reverse order of magnitude. 

Add and Implement the private method void sortAndPrint(int numToPrint) in EarthquakeCityMap. This method will create a new array from the list of earthquake markers (hint: there is a method in the List interface named toArray() which returns the elements in the List as an array of Objects). Then it will sort the array of earthquake markers in reverse order of their magnitude (highest to lowest) and then print out the top numToPrint earthquakes. If numToPrint is larger than the number of markers in quakeMarkers, it should print out all of the earthquakes and stop, but it should not crash.

Work with the airport and route data: There are two data files in the data directory: airports.dat and routes.dat which contain information about all of the airports in the world and routes between these airports, respectively. There are also two methods in ParseFeed.java for loading the data in these files, and we provide a starter file AirportMap.java that shows how to use the parser to read in the data from these files. Once you’ve read in the data, what you do with it is up to you. You might:

    Display all the airports in the world as features, and then display additional information about them when the user hovers over them.
    Display only a subset of the airports in the world, based on some criteria.
    Display airports, and when the user clicks on one, display the routes out of that airport.
    
    
