package com.iit.caiguohui.model;

public enum Role {
    admin(1), customer(2);

    private int id;

    public int getId() {
        return id;
    }

    private Role(int id) {
        this.id = id;
    }

}
