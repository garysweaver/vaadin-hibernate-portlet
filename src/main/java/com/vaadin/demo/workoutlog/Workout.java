package com.vaadin.demo.workoutlog;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Workout implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date = new Date();
    private String title = " -- new workout -- ";
    private float kilometers;

    @ManyToOne
    private Type trainingType;

    @ManyToMany
    private Set<Type> secondaryTypes;

    public Workout() {
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getKilometers() {
        return kilometers;
    }

    public void setKilometers(float kilometers) {
        this.kilometers = kilometers;
    }

    public Type getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(Type trainingType) {
        this.trainingType = trainingType;
    }

    public void setSecondaryTypes(Set<Type> secondaryTypes) {
        this.secondaryTypes = secondaryTypes;
    }

    public Set<Type> getSecondaryTypes() {
        return secondaryTypes;
    }

}
