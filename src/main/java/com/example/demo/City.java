package com.example.demo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class represent
 */
public class City {
    private String name;

    private Set<City> neighbours = new HashSet<>();

    private City(String name) {
        Objects.requireNonNull(name);
        this.name = name.toUpperCase();
    }

    private City() {
    }

    public static City build(String name) {
        return new City(name);
    }

    @Override
    public String toString() {

        String s = neighbours
                .stream()
                .map(City::getName)
                .collect(Collectors.joining(","));

        return "City{" +
                "name='" + name + "'" +
                ", neighbours='" + s +
                "'}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public City addNeighbour(City city) {
        neighbours.add(city);
        return this;
    }

    public Set<City> getNeighbours() {
        return neighbours;
    }
}
