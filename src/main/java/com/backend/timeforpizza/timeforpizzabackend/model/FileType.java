package com.backend.timeforpizza.timeforpizzabackend.model;

public enum FileType {
    RECIPE_IMAGE(1L);

    private Long typeId;

    FileType(Long typeId) {
        this.typeId = typeId;
    }

    public Long getTypeId() {
        return typeId;
    }
}
