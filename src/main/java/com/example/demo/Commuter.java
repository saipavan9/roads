package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Commuter {

    private static final Log LOG = LogFactory.getLog(Commuter.class);

    private Commuter() {
    }

    public static boolean commute(City origin, City destination) {


        Set<City> visited = new HashSet<>();

        LOG.info("Origin: " + origin.getName() + ", destination: " + destination.getName());

        if (origin.equals(destination)) return true;

        if (origin.getNeighbours().contains(destination)) return true;

        Deque<City> bucketlist = new ArrayDeque<>(origin.getNeighbours());

        while (!bucketlist.isEmpty()) {

            City city = bucketlist.getLast();


            if (city.equals(destination)) return true;

            if (!visited.contains(city)) {
                visited.add(city);
                bucketlist.addAll(city.getNeighbours());
                bucketlist.removeAll(visited);

                LOG.info("Visiting: [" + city.getName() + "] , neighbours: [" + prettyPrint(city.getNeighbours()) + "], bucketlist: [" + prettyPrint(bucketlist) + "]");
            }

            bucketlist.removeAll(Collections.singleton(city));

        }

        return false;
    }

    private static String prettyPrint(Collection<City> c) {
        return c.stream()
                .distinct()
                .map(City::getName)
                .collect(Collectors.joining(","));

    }
}

