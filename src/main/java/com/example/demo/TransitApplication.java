package com.example.demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.Objects;


/**
 * SpringApplication along with a simple REST controller
 * and a custom exception handler
 */
@SpringBootApplication
@RestController
@EnableSwagger2
@Api(value = "Transit demo application", description = "Find if a path exists between two cities")
public class TransitApplication {

    private final Log LOG = LogFactory.getLog(getClass());

    @Autowired
    County county;

    public static void main(String[] args) {
        SpringApplication.run(TransitApplication.class, args);
    }

    @ApiOperation(value = "Find if a path exists between two cities",
            notes = "Returns true if cites connected and false otherwise ",
            response = String.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Either destination or origin city does not exist or invalid", response = NullPointerException.class),
            @ApiResponse(code = 500, message = "Generic error", response = Exception.class)
    })
    @GetMapping(value = "/connected", produces = "text/plain")
    public String isConnected(
            @ApiParam(name = "origin", value = "Origin City name", required = true) @RequestParam String origin,
            @ApiParam(name = "destination", value = "Destination City name", required = true) @RequestParam String destination) {

        City originCity = county.getCity(origin.toUpperCase());
        City destCity = county.getCity(destination.toUpperCase());

        Objects.requireNonNull(originCity);
        Objects.requireNonNull(destCity);

        return String.valueOf(Commuter.commute(originCity, destCity));
    }

    @GetMapping(value = "/", produces = "text/html")
    public String info() {

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><head><meta charset=\"utf-8\"><title>City Data</title></head><body>")
                .append("<h2>City List</h2>")
                .append("<ul>");

        Collection<City> cities = county.getCityMap().values();
        for (City city : cities) {
            html.append("<li>")
                    .append(city.getName())
                    .append(" &rarr; ")
                    .append(city.prettyPrint())
                    .append("</li>");
        }
        html.append("</ul>");
        html.append("</body></html>");
        return html.toString();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String cityError() {
        return "Either destination or origin city does not exist or invalid";
    }

}
