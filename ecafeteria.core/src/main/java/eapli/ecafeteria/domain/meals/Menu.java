/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.ecafeteria.domain.meals;

import eapli.framework.domain.AggregateRoot;
import eapli.framework.domain.range.TimePeriod;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 *
 * @author zero_
 */
@Entity
public class Menu implements AggregateRoot<String>, Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;

    // business ID
    @Column(unique = true)
    private String name;
    private TimePeriod timePeriod;
    private boolean isPublished;

    protected Menu() {
        // ORM
    }

    public Menu(String name, Calendar beginningDate, Calendar endingDate) {
        this(name, new TimePeriod(beginningDate, endingDate));
    }

    public Menu(String name, TimePeriod timePeriod) {
        if (name == null || timePeriod == null) {
            throw new IllegalStateException();
        }

        this.name = name;
        this.timePeriod = timePeriod;
        this.isPublished = false;
    }

    public String name() {
        return name;
    }

    public boolean isPublished() {
        return this.isPublished;
    }

    public void publishMenu() {
        this.isPublished = true;
    }

    public boolean isInBetween(Calendar date) {
        return this.timePeriod.includes(date);
    }

    @Override
    public String id() {
        return this.name;
    }

    @Override
    public boolean sameAs(Object other) {
        final Menu menu = (Menu) other;
        return this.equals(menu) && name().equals(menu.name()) && isPublished() == menu.isPublished();
    }

    @Override
    public boolean is(String id) {
        return id.equalsIgnoreCase(this.name);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }

        final Menu other = (Menu) o;
        return id().equals(other.id());
    }
}