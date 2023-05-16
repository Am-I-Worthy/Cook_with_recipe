package com.cognizant.repository;

import org.springframework.data.repository.CrudRepository;

import com.cognizant.model.Recipe;



public interface RecipeRepository extends CrudRepository<Recipe, Integer>{

}