package hjul24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
	
	static HashMap<String, int[]> coordinates = new HashMap<String, int[]>() {
		private static final long serialVersionUID = 1L;
	{
		put("ne", new int[]{1,3});
		put("e", new int[]{2,0});
		put("se", new int[]{1,-3});
		put("sw", new int[]{-1,-3});
		put("w", new int[]{-2, 0});
		put("nw", new int[]{-1,3});
	}};

	public static void main(String[] args) throws FileNotFoundException {
		
		String directory = System.getProperty( "user.home" );
		String fileName = "Hjulfiler/input-24.txt";
		String absolutePath = directory + File.separator + fileName;
		File file = new File(absolutePath);
		Scanner scanner = new Scanner( file );
		
		ArrayList<ArrayList<String>> strs = new ArrayList<>();
		Pattern pattern = Pattern.compile( "[sn]?[we]" );

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			ArrayList<String> parts = new ArrayList<>();
	        Matcher matcher = pattern.matcher( line );
	        while ( matcher.find() ) {
	        	parts.add( matcher.group() );
	        	
	        }
	        strs.add(parts);
		}
		scanner.close();
				
		System.out.println("PART 1");
		HashMap<String, Boolean> tiles = flipTiles( strs );
		System.out.println(tiles.values().stream().filter( n -> n == true ).count());
		
		System.out.println("PART 2");
		tiles = makeArt( tiles );
		System.out.println(tiles.values().stream().filter( n -> n == true ).count());
		
	}
	
	static String getTile( ArrayList<String> directions) {
		return getTile( directions, 0,0 );
	}
	
	static String getTile( String direction, int x, int y ) {
		return getTile( new ArrayList<>( Arrays.asList( direction ) ), x, y );
	}
	
	static String getTile( ArrayList<String> directions, int x, int y ) {
		for ( String str : directions ) {
			int[] x2y2 = coordinates.get( str );
			x += x2y2[0];
			y += x2y2[1];
		}
		return x + "," + y;
	}
	
	
	//PART 1
	static HashMap<String, Boolean> flipTiles( ArrayList<ArrayList <String>> path ) {
		HashMap<String, Boolean> tiles = new HashMap<>();
		for ( ArrayList<String> line: path ) {
			String key = getTile( line );
			Boolean isBlack = !tiles.getOrDefault( key, false ) ? true : false;
			tiles.put( key, isBlack );
		}
		return tiles;
	}
	
	//PART 2
	static HashMap<String, Boolean> makeArt( HashMap<String, Boolean> tiles ) {
		int day = 1;
		
		while ( day <= 100 ) {
			HashMap<String, Boolean> newTiles = new HashMap<>();
			for ( String key: tiles.keySet() ) {
				String[] parts = key.split( "," );
				int x = Integer.parseInt( parts[0] );
				int y = Integer.parseInt( parts[1] );
				int blackTiles = getNeighbours( x, y, tiles, newTiles );
				Boolean isBlack = tiles.get( x + "," + y );
				if ( !isBlack && blackTiles == 2 ) {
					newTiles.put( x + "," + y, true);
				}
				if ( isBlack && ( blackTiles > 0 && blackTiles < 3 ) ) {
					newTiles.put( x + "," + y, true );
				}
			}
			tiles = newTiles;
			day++;
		}
		return tiles;
	}
	
	//not beautiful but newList in getNeighbours method so that neighbours not in tiles array can be checked and added to the updated tilesArray
	
	static int getNeighbours( int x, int y, HashMap<String, Boolean> oldList, HashMap<String, Boolean> newList ) {
		ArrayList<String> adjacent = new ArrayList<>( coordinates.keySet() );
		int black = 0;
		for ( String next: adjacent ) {
			String key = getTile( next, x, y );
			if ( !oldList.containsKey( key ) ) {
				String[] parts = key.split(",");
				int newX = Integer.parseInt( parts[0] );
				int newY = Integer.parseInt( parts[1] );
				int blackTiles = 0;
				for ( String each: adjacent ) {
					String neighbour = getTile( each, newX, newY );
					if ( oldList.containsKey( neighbour ) ) {
						blackTiles += oldList.get( neighbour ) ? 1 : 0;
					}
				}
				if ( blackTiles == 2 ) {
					newList.put( key, true );
				}
			}
			Boolean isBlack = oldList.getOrDefault( key, false );
			black += isBlack ? 1 : 0;
		}
		return black;
	}
}
