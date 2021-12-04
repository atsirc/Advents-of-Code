package hjul20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-20.txt";
		String absolutePath = directory + File.separator + fileName;
		ArrayList<Image> images = new ArrayList<Image>();
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);

			int currentImageId = 0 ;
			ArrayList<String> currentImage = new ArrayList<String>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					images.add(new Image(currentImage, currentImageId));
					currentImage = new ArrayList<String>();
					continue;
				} if (line.contains("Tile")) {
					line = line.replace(":", "");
					currentImageId = Integer.parseInt(line.replace("Tile ", ""));
				} else {
					currentImage.add(line);
				}
			}
			scanner.close();
			
			int iterations = 0;
			
			while (iterations != 200) {
				for (Image image : images) {
					images.forEach(n -> n.sidesMatch(image));

				}
				
				for (Image image: images) {
					int currentSize = image.sidesMatched();
					if (currentSize < 4) {
						image.flip();
						images.forEach(n -> n.sidesMatch(image));
						
						if (image.sidesMatched() != currentSize) {
							break;
						}
						image.flip();
						image.flipUpSideDown();
						images.forEach(n -> n.sidesMatch(image));
						
						if (image.sidesMatched() != currentSize) {
							break;
						}
						image.flipUpSideDown();
						
						image.turn();
						images.forEach(n -> n.sidesMatch(image));
						
						if (image.sidesMatched() != currentSize) {
							break;
						}
						image.turn();
						images.forEach(n -> n.sidesMatch(image));
						
						if (image.sidesMatched() != currentSize) {
							break;
						}
						image.turn();
						images.forEach(n -> n.sidesMatch(image));
						
						if (image.sidesMatched() != currentSize) {
							break;
						}
						image.turn();	
					}
				}
				
				iterations++;
			}
			
			
			//PART 1:
			
			ArrayList<Image> cornerPieces = findCornerPieces(images);
			System.out.println();
			long answer = cornerPieces.stream().mapToLong(n -> (long)n.id).reduce(1, (a,b) -> a*b);
			System.out.println("Answer PART 1: " + answer);
			
			makePuzzle(images);
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	static ArrayList<Image> findCornerPieces(ArrayList<Image> images) {
		ArrayList<Image> cornerPieces = new ArrayList<Image>();
		for (Image image: images) {
			if (image.sidesMatched() < 4) {
				if (image.sidesMatched() == 2) {
					cornerPieces.add(image);
					continue;
				}
				long distinct = image.matches.values().stream().map(n -> n).distinct().count();
				if (distinct == 2) cornerPieces.add(image);
			}
		}
		return cornerPieces;
	}
	
	static void  makePuzzle(ArrayList<Image> images) {
		ArrayList<Image> cornerPieces = findCornerPieces(images);
		Image currentTile = cornerPieces.stream().filter(n -> n.sidesMatched() == 2).findFirst().get();
		
		int under = currentTile.getMatchForSide(2);

		Image underImg = images.stream().filter(n -> n.id == under).findFirst().get();


		int i = 0;
		while (i <10) {
			int left = currentTile.getMatchForSide(1);
			Image nextImg = images.stream().filter(n -> n.id == left).findFirst().get();
			String mustMatch = currentTile.getSides().get(1);
		//	if ( mustMatch.equals()
			if (!nextImg.sideIsEmpty(3)) {
				ArrayList<String>sides = nextImg.getSides();
				
				//leta efter nästa, kolla om dens övre sida är tom, sen kolla när föregående bita passar ihop mellan deno första biten
				// gå igenom matecherna o hitta den som har 3 sidor, kolla omd den har 0an tom etc
				
			}

		}
		
		
	/*	while (true) {
			ArrayList<Integer> sides = currentTile.getNeighbours();
			int distinct = (int)sides.stream().distinct().count();
			if (distinct < sides.size()) {
				//kolla denn sidas matchar o jmfr med följande om följandes också ser bra ut lämna som det är, annars testa o snurra etc på vardera av dessa
			}
		}*/
		
	}

}
