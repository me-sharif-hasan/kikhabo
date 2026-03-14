package com.iishanto.kikhabo.infrastructure.services.recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private JsonNode countryMap;

    @PostConstruct
    void load() throws Exception {
        JsonNode root = new ObjectMapper().readTree(
                new ClassPathResource("countries.json").getInputStream()
        );
        countryMap = root.get("country_to_continent_and_subcontinent");
    }

    /** Returns continent + subcontinent for a country name, or null if not found. */
    public Region resolve(String country) {
        if (country == null || country.isBlank() || countryMap == null) return null;
        JsonNode node = countryMap.get(country);
        if (node == null) return null;
        String continent    = node.path("continent").asText(null);
        String subcontinent = node.path("subcontinent").asText(null);
        if ("null".equals(subcontinent)) subcontinent = null;
        return (continent == null) ? null : new Region(continent, subcontinent);
    }

    @Getter
    public static class Region {
        private final String continent;
        private final String subcontinent;

        public Region(String continent, String subcontinent) {
            this.continent = continent;
            this.subcontinent = subcontinent;
        }
    }
}
