package org.lesson.java.spring_la_mia_pizzeria_crud.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pizzas")

public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "name cannot be null or empty")
    @Size(min = 2, max = 100, message = "must have a minimum of 2 characters and a maximum of 100")
    private String name;

    @Lob
    private String description;
    @Lob
    private String image;

    @NotNull(message = "price cannot be null or empty")
    @Positive
    private BigDecimal price;

    public List<Offer> getOffers() {
        return this.offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    @OneToMany(mappedBy = "pizza", cascade = (CascadeType.REMOVE))
    private List<Offer> offers;

    @ManyToMany
    @JoinTable(name = "ingredient_pizza", joinColumns = @JoinColumn(name = "pizza_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;

    public Pizza(String name, String description, String image, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Pizza() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", this.name, this.description, this.price);
    }
}