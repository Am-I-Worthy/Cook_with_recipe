package com.cognizant.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.cognizant.model.Recipe;
import com.cognizant.repository.RecipeRepository;

@Controller
@RequestMapping(path="/recipe")
public class RecipeController {
	
	@Autowired 
	private RecipeRepository recipeRepository;
	Recipe s = new Recipe();

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path="/create")
	public @ResponseBody String createRecipe(
			 @RequestBody Recipe r) {
		
		recipeRepository.save(r);

		return "1";
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path="/get")
	public @ResponseBody Iterable<Recipe> getAllRecipe() {
		
		return recipeRepository.findAll();
		
	};
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path="/getUnique")
	public @ResponseBody Optional<Recipe> getRecipe(@RequestParam Integer id) {
		
		return recipeRepository.findById(id);
	};
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping(path="/update")
	public @ResponseBody String updateRecipe(
			 @RequestBody Recipe r) {
		recipeRepository.save(r);
		return "1";
	}
}
