package org.lesson.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lesson.java.spring_la_mia_pizzeria_crud.model.Offer;
import org.lesson.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lesson.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.lesson.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    private PizzaRepository repository;

    PizzaController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // @Autowired
    // private OfferRepository offerRepository;

    @GetMapping
    public String home(Model model) {
        List<Pizza> pizzas = repository.findAll();
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Pizza pizza = repository.findById(id).get();
        model.addAttribute("pizza", pizza);
        return "pizzas/show";
    }

    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(name = "name") String name, Model model) {
        List<Pizza> pizzas = repository.findByNameContaining(name);
        model.addAttribute("name", name);
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "pizzas/create";
        }
        repository.save(formPizza);

        return "redirect:/pizzas";
    }

    // edit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("pizza", repository.findById(id).get());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "pizzas/edit";
        }
        repository.save(formPizza);

        return "redirect:/pizzas";
    }

    // delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        Pizza pizza = repository.findById(id).get();

        // for (Offer offerToDelete : pizza.getOffers()){
        // offerRepository.delete(offerToDelete);
        // }

        repository.delete(pizza);

        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/offer")
    public String offer(@PathVariable Integer id, Model model) {

        Offer offer = new Offer();
        offer.setPizza(repository.findById(id).get());
        model.addAttribute("offer", offer);

        return "offer/create-or-edit";
    }
}
