package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class TransitApplicationTests {

    @Autowired
    County county;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void fileLoad() {
        Assert.assertFalse("File load failed", county.getCityMap().isEmpty());
    }

    @Test
    public void sameCity() {
        City city = City.build("A");
        Assert.assertTrue(Commuter.commute(city, city));
    }

    @Test
    public void neighbours() {
        City cityA = county.getCity("A");
        City cityB = county.getCity("B");

        Assert.assertNotNull("Invalid test data. City not found: A", cityA);
        Assert.assertNotNull("Invalid test data. City not found: B", cityB);

        Assert.assertTrue(Commuter.commute(cityA, cityB));
    }

    @Test
    public void distantConnected() {
        City cityA = county.getCity("F");
        City cityB = county.getCity("A");

        Assert.assertNotNull("Invalid test data. City not found: F", cityA);
        Assert.assertNotNull("Invalid test data. City not found: A", cityB);

        Assert.assertTrue(Commuter.commute(cityA, cityB));
    }

    @Test
    public void restConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "A");
        params.put("destination", "F");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("true", body);
    }

    @Test
    public void restNotConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "a");
        params.put("destination", "l");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("false", body);
    }

    @Test
    public void badRequestIT() {
        String body = restTemplate.getForObject("/connected", String.class);
        ResponseEntity<String> response = restTemplate.exchange("/connected?origin=none&destination=none", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
