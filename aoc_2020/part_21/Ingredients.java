package hjul21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ingredients {
	
	ArrayList<String> ingredients;
	ArrayList<String> allergens;
	
	public Ingredients(String[] ingredients, String[] allergens) {
		this.ingredients = new ArrayList<>(Arrays.asList(ingredients));
		this.allergens = new ArrayList<>(Arrays.asList(allergens));
	}

	public boolean containsAllergen(String allergen) {
		return allergens.contains(allergen);
	}
	
	public boolean containsIngredient(String ingredient) {
		return ingredients.contains(ingredient);
	}
	
	public ArrayList<String> sameIngredients(Ingredients otherIngredients) {
		ArrayList<String> same = new ArrayList<String>();
		for (String ingredient : ingredients) {
			if (otherIngredients.containsIngredient(ingredient)) {
				same.add(ingredient);
			}
		}
		return same;
	}
	
	public ArrayList<String> okIngredients(List<String> allergens) {
		ArrayList<String> oks = new ArrayList<String>();
		for (String ingredient: ingredients) {
			if (!allergens.contains(ingredient)) {
				oks.add(ingredient);
			}
		}
		return oks;
	}
	
}
