package com.iishanto.kikhabo.infrastructure.services.recipe;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Defines food keywords that must be excluded from ALL recipe results
 * (both search and recommendation) based on religious or cultural prohibitions.
 *
 * Each category documents which religion/culture prohibits those foods.
 * The compiled regex is cached at startup and reused for every MongoDB query.
 */
@Component
public class FoodExclusionConfig {

    // ── Category definitions ──────────────────────────────────────────────────

    /**
     * PORK & PORK DERIVATIVES
     * Prohibited in: Islam (Haram), Judaism (Treif), Seventh-day Adventism
     */
    private static final List<String> PORK = Arrays.asList(
            "pork", "bacon", "prosciutto", "pancetta", "pepperoni",
            "salami", "chorizo", "guanciale", "mortadella", "lard",
            "pork belly", "pork chop", "pork ribs", "pork loin",
            "pork shoulder", "pulled pork", "pork rind", "crackling",
            "pig", "piglet", "suckling pig", "boar", "swine",
            "spam", "andouille"
    );

    /**
     * BEEF & COW PRODUCTS
     * Prohibited in: Hinduism, some Buddhist sects, Jainism
     */
    private static final List<String> BEEF = Arrays.asList(
            "beef", "veal", "brisket", "ribeye", "sirloin",
            "t-bone", "chuck roast", "ground beef", "minced beef",
            "beef steak", "beef stock", "beef broth", "corned beef",
            "roast beef", "beef tenderloin", "oxtail", "cow",
            "hamburger patty", "beef burger", "beef tallow"
    );

    /**
     * BLOOD & BLOOD PRODUCTS
     * Prohibited in: Islam (Haram), Judaism (Treif) — consuming blood is forbidden
     */
    private static final List<String> BLOOD = Arrays.asList(
            "blood sausage", "blood pudding", "black pudding",
            "blutwurst", "morcilla", "boudin noir", "blood cake"
    );

    /**
     * SHELLFISH & CRUSTACEANS
     * Prohibited in: Judaism (Treif — not kosher), some Christian denominations (Levitical law)
     * Note: Shellfish are generally considered halal in most Islamic schools
     */
    private static final List<String> SHELLFISH = Arrays.asList(
            "lobster", "crab", "crayfish", "craw fish", "crawdad",
            "scallop", "oyster", "clam", "mussel", "abalone",
            "barnacle", "conch", "whelk", "periwinkle"
    );

    /**
     * EXOTIC / CONTROVERSIAL MEATS
     * Controversial or prohibited across multiple cultures/countries
     */
    private static final List<String> EXOTIC_MEATS = Arrays.asList(
            "dog meat", "cat meat", "donkey meat",
            "bushmeat", "bat meat", "rat meat", "snake meat",
            "crocodile meat", "monkey meat"
    );

    /**
     * All categories combined — used to build the single cached exclusion regex.
     * Each category is a list of keyword strings.
     */
    public static final Map<String, List<String>> ALL_CATEGORIES = new LinkedHashMap<>();

    static {
        ALL_CATEGORIES.put("Pork & Derivatives (Islam/Judaism/Adventism)", PORK);
        ALL_CATEGORIES.put("Beef & Cow (Hinduism/Jainism/Buddhism)", BEEF);
        ALL_CATEGORIES.put("Blood Products (Islam/Judaism)", BLOOD);
        ALL_CATEGORIES.put("Shellfish (Judaism/some Christianity)", SHELLFISH);
        ALL_CATEGORIES.put("Exotic/Controversial Meats", EXOTIC_MEATS);
    }

    // ── Compiled regex ────────────────────────────────────────────────────────

    private final String exclusionPattern;

    public FoodExclusionConfig() {
        // Build one combined word-boundary regex from all categories
        // Example result: \bpork\b|\bbacon\b|\bbeef\b|...
        exclusionPattern = ALL_CATEGORIES.values().stream()
                .flatMap(List::stream)
                .map(term -> "\\b" + term.toLowerCase() + "\\b")
                .collect(Collectors.joining("|"));
    }

    /**
     * Returns the compiled regex pattern ready to use in a MongoDB {@code $regex} query.
     * Matches any document whose field contains at least one excluded term (case-insensitive).
     */
    public String getExclusionPattern() {
        return exclusionPattern;
    }

    /**
     * Returns all excluded terms split into batches of 10.
     * Used by MongoConfig to run smaller regex queries instead of one huge pattern
     * that would exceed Atlas M0 operation timeout.
     */
    public List<List<String>> getTermBatches() {
        List<String> allTerms = ALL_CATEGORIES.values().stream()
                .flatMap(List::stream)
                .toList();

        List<List<String>> batches = new java.util.ArrayList<>();
        int batchSize = 10;
        for (int i = 0; i < allTerms.size(); i += batchSize) {
            batches.add(allTerms.subList(i, Math.min(i + batchSize, allTerms.size())));
        }
        return batches;
    }
}
