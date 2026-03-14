package com.iishanto.kikhabo.infrastructure.services.recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RecommendationService {

    private JsonNode cuisineTags;

    @PostConstruct
    void load() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new ClassPathResource("recomendation.json").getInputStream());
        cuisineTags = root.get("cuisine_tags_by_continent_and_subcontinent");
    }

    /**
     * Returns search terms for a given continent + optional subcontinent.
     * Used only for recommendation (browse) mode — ignored when user provides a search query.
     */
    public List<String> getTerms(String continent, String subcontinent) {
        if (continent == null || cuisineTags == null) return Collections.emptyList();

        JsonNode continentNode = cuisineTags.get(continent);
        if (continentNode == null) return Collections.emptyList();

        List<String> terms = new ArrayList<>();

        if (subcontinent != null) {
            JsonNode sub = continentNode.get(subcontinent);
            if (sub != null && sub.isArray()) {
                sub.forEach(t -> terms.add(t.asText()));
            }
        } else {
            // no subcontinent — collect all terms across the whole continent
            continentNode.fields().forEachRemaining(entry -> {
                if (entry.getValue().isArray()) {
                    entry.getValue().forEach(t -> terms.add(t.asText()));
                }
            });
        }

        return terms;
    }
}
