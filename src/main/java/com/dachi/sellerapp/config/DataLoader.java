package com.dachi.sellerapp.config;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class DataLoader {

    private final JdbcTemplate jdbc;

    public DataLoader(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @PostConstruct
    public void load() throws Exception {
        run("schema.sql");   // <-- run schema FIRST
        run("data.sql");     // <-- then load data
    }

    private void run(String file) throws Exception {
        ClassPathResource res = new ClassPathResource(file);

        if (!res.exists()) {
            System.out.println("Skipping " + file + " (not found)");
            return;
        }

        String sql = new String(res.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // H2 cannot execute multi-statement SQL unless split manually
        for (String stmt : sql.split(";")) {
            String s = stmt.trim();
            if (!s.isEmpty()) jdbc.execute(s);
        }

        System.out.println("Executed: " + file);
    }
}
