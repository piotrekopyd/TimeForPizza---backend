package com.backend.timeforpizza.timeforpizzabackend.payload;

public class IngredientResponse {

    private Long ingredientId;

    private String name;

    private Integer amount;

    private String unit;

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public IngredientResponse(Long ingredientId, String name, Integer amount, String unit) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public IngredientResponse() {}
}
