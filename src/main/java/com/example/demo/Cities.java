package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class Cities {

    private final Log LOG = LogFactory.getLog(getClass());

    public Map<String, City> land = new HashMap<>();

    @Value("${data.file:classpath:cities.txt}")
    private String CITIES;

    @PostConstruct
    public void read() throws IOException {

        LOG.info("Reading data");

        File file = ResourceUtils.getFile(CITIES);
        FileReader reader = new FileReader(file);
        LineNumberReader lineNumberReader = new LineNumberReader(reader);

        String line = lineNumberReader.readLine();
        while (line != null) {
            LOG.info(line);

            String[] split = line.split(",");
            String Akey = split[0];
            String Bkey = split[1];


            City A = land.getOrDefault(Akey, City.build(Akey));
            City B = land.getOrDefault(Bkey, City.build(Bkey));

            A.addNeighbour(B);
            B.addNeighbour(A);

            land.put(A.getName(), A);
            land.put(B.getName(), B);

            line = lineNumberReader.readLine();
        }

        LOG.info("Map: " + land);
    }
}
