package com.iishanto.kikhabo.infrastructure.services.scheduler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Maps country names (as stored in UserEntity.country) to ISO 3166-1 alpha-2
 * codes required by the Calendarific API.
 *
 * <p>The base map is built automatically from the JVM's built-in
 * {@link Locale#getISOCountries()} (covers all ~249 ISO countries).
 * Manual overrides handle abbreviations and alternate names used in this app.
 */
public final class CountryCodeHelper {

    private static final Map<String, String> CODE = new HashMap<>();

    static {
        // Auto-populate from JVM locale data (all ISO 3166-1 alpha-2 countries)
        for (String isoCode : Locale.getISOCountries()) {
            String displayName = new Locale("", isoCode).getDisplayCountry(Locale.ENGLISH);
            CODE.put(displayName.toLowerCase(), isoCode);
        }

        // Manual overrides: abbreviations and alternate names used in the app
        put("UAE",          "AE");  // United Arab Emirates
        put("USA",          "US");  // United States
        put("US",           "US");
        put("UK",           "GB");  // United Kingdom
        put("Czechia",      "CZ");  // Czech Republic alternate name
        put("North Korea",  "KP");  // Democratic People's Republic of Korea
        put("South Korea",  "KR");  // Republic of Korea
        put("Kosovo",       "XK");  // Exceptional reservation (not in ISO 3166-1 officially)
        put("Palestine",    "PS");  // State of Palestine
        put("Taiwan",       "TW");  // Taiwan (Province of China in ISO)
        put("Congo",        "CG");  // Republic of the Congo (vs CD = DRC)
        put("Laos",         "LA");  // Lao PDR
        put("Brunei",       "BN");  // Brunei Darussalam
        put("Iran",         "IR");  // Islamic Republic of Iran
        put("Syria",        "SY");  // Syrian Arab Republic
        put("Vietnam",      "VN");  // Viet Nam
        put("Timor-Leste",  "TL");  // East Timor
        put("Myanmar",      "MM");  // Burma alternate name
    }

    private static void put(String name, String code) {
        CODE.put(name.toLowerCase(), code);
    }

    /** Returns the ISO alpha-2 code for the given country name, or null if unknown. */
    public static String getCode(String country) {
        if (country == null) return null;
        return CODE.get(country.trim().toLowerCase());
    }

    private CountryCodeHelper() {}
}
