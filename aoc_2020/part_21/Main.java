package hjul21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		
		String directory = System.getProperty("user.home");
		String fileName = "Hjulfiler/input-21.txt";
		String absolutePath = directory + File.separator + fileName;
		
		
		try {
			File file = new File(absolutePath);
			Scanner scanner = new Scanner(file);
			
			ArrayList<Ingredients> ingredientLists = new ArrayList<Ingredients>();
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				line = line.replace(",", "");
				line = line.replace(")", "");
				String[] parts = line.split("\\s\\(\\w+\\s");
				Ingredients ingredients = new Ingredients(parts[0].split(" "), parts[1].split(" "));
				ingredientLists.add(ingredients);
			}
			
			scanner.close();
			
			
			HashMap<String, ArrayList<String>> possibleAllergens = findPossibleAllergens(ingredientLists);
			ArrayList<String> okIngredients = findNonAllergens(possibleAllergens, ingredientLists);
			
			System.out.println("Part 1:");
			System.out.println(okIngredients.size());
			
			
			System.out.println("Part 2:");
			System.out.println(getScript(possibleAllergens));	
			
		} catch(Exception e) {
			System.out.println("Couldn't find file");
		}
		
	}
	
	public static ArrayList<String> removeNonMatches(ArrayList<String> prevMatches, ArrayList<String> newMatches) {
		ArrayList<String> holder = new ArrayList<String>();
		for (int i = 0; i < newMatches.size(); i++ ) {
			if (prevMatches.contains(newMatches.get(i)))
					holder.add(newMatches.get(i));
		}
		return holder;
	}

	public static HashMap<String, ArrayList<String>> findPossibleAllergens(ArrayList<Ingredients> ingredientsLists) {
		HashMap<String, ArrayList<String>> possibleAllergens = new HashMap<String, ArrayList<String>>();
		for (Ingredients ingredients: ingredientsLists) {
			
			for (String allergen : ingredients.allergens) {
				ArrayList<String> matches = new ArrayList<String>();
				if (possibleAllergens.containsKey(allergen)) continue;
				
				for (Ingredients otherIngredients: ingredientsLists) {
					if (otherIngredients.containsAllergen(allergen)) {
						ArrayList<String> newMatches = ingredients.sameIngredients(otherIngredients);
						if (matches.isEmpty()) matches.addAll(newMatches);
						else {
							matches = removeNonMatches(matches, newMatches);
						}
					}
				}
				possibleAllergens.put(allergen, matches);
			}
		}
		return possibleAllergens;
	}
	
	public static ArrayList<String> findNonAllergens(HashMap<String, ArrayList<String>> possibleAllergens, ArrayList<Ingredients> ingredientsLists) {
	    ArrayList<String> falttenedPA = new ArrayList<>();
	    possibleAllergens.values().forEach(falttenedPA::addAll);
	    List<String> distinctPA = falttenedPA.stream().distinct().collect(Collectors.toList());
	    ArrayList<String> okIngredients = new ArrayList<>();
	    for (Ingredients ingredient : ingredientsLists) {
	    	okIngredients.addAll(ingredient.okIngredients(distinctPA));
	    }
	    return okIngredients;
	}
	
	public static String getScript(HashMap<String, ArrayList<String>> list) {
		int listSize = list.size();
		ArrayList<String> allergens = new ArrayList<String>();
		while (true) {
			final String ingredient = list.values().stream().filter(n -> n.size() == 1 && !allergens.contains(n.get(0))).findFirst().get().get(0);
			allergens.add(ingredient);
			list.forEach((k,v) -> {
				if (v.size() > 1) {
					v.remove(ingredient);
				}
			});
			if (allergens.size() == listSize) {
				break;
			}
		}
		
		final LinkedHashMap<String, String> sortedDictionnary = new LinkedHashMap<>();
		list.entrySet().stream().sorted((k1, k2) -> k1.getKey().compareTo(k2.getKey()))
		.forEach( (n) -> sortedDictionnary.put(n.getKey(), n.getValue().get(0)));

		return sortedDictionnary.values().stream().map(Object::toString).collect(Collectors.joining(","));
	}
	

}
