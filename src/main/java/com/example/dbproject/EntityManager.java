package com.example.dbproject;

import java.util.ArrayList;

public class EntityManager {
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    void addEntity(Entity entity) {
        entities.add(entity);
    }
}