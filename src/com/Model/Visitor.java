package com.Model;

public class Visitor extends User{
    private String reference;

    public Visitor() {
        super(id, name, role);
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
