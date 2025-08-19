package org.lesson.java.spring_la_mia_pizzeria_crud.controller;

import org.lesson.java.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lesson.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lesson.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredient", ingredientRepository.findAll());
        return "ingredients/index";
    }
    // ---------------------------------------------------

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "ingredients/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "ingredients/create-or-edit";
        }

        ingredientRepository.save(formIngredient);

        return "redirect:/ingredient";

        // -----+ formIngredient.getPizza().getId()
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Ingredient ingredient = ingredientRepository.findById(id).get();
        model.addAttribute("ingredient", ingredient);
        return "ingredients/show";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredient", ingredientRepository.findById(id).get());
        model.addAttribute("edit", true);
        return "ingredients/create-or-edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "ingredients/create-or-edit";
        }
        ingredientRepository.save(formIngredient);
        return "redirect:/ingredient";

        // + formIngredient.getPizza().getId()
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {

        Ingredient ingredient = ingredientRepository.findById(id).get();
        for (Pizza ingredientPizza : ingredient.getPizzas()) {
            ingredientPizza.getIngredients().remove(ingredient);
        }

        ingredientRepository.delete(ingredient);

        return "redirect:/ingredient";
    }
}
