package com.iishanto.kikhabo.infrastructure.services.scheduler;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps country names (as stored in UserEntity.country) to IANA timezone IDs and languages.
 * Matching is case-insensitive. Falls back to UTC / English when unknown.
 */
public final class CountryTimeZoneHelper {

    private static final Map<String, String> TIMEZONE = new HashMap<>();
    private static final Map<String, String> LANGUAGE = new HashMap<>();

    static {
        // ── Asia ────────────────────────────────────────────────────────────────
        put("Afghanistan",             "Asia/Kabul",                "Dari");
        put("Armenia",                 "Asia/Yerevan",              "Armenian");
        put("Azerbaijan",              "Asia/Baku",                 "Azerbaijani");
        put("Bahrain",                 "Asia/Bahrain",              "Arabic");
        put("Bangladesh",              "Asia/Dhaka",                "Bengali");
        put("Bhutan",                  "Asia/Thimphu",              "Dzongkha");
        put("Brunei",                  "Asia/Brunei",               "Malay");
        put("Cambodia",                "Asia/Phnom_Penh",           "Khmer");
        put("China",                   "Asia/Shanghai",             "Chinese");
        put("Cyprus",                  "Asia/Nicosia",              "Greek");
        put("Georgia",                 "Asia/Tbilisi",              "Georgian");
        put("India",                   "Asia/Kolkata",              "Hindi");
        put("Indonesia",               "Asia/Jakarta",              "Indonesian");
        put("Iran",                    "Asia/Tehran",               "Persian");
        put("Iraq",                    "Asia/Baghdad",              "Arabic");
        put("Israel",                  "Asia/Jerusalem",            "Hebrew");
        put("Japan",                   "Asia/Tokyo",                "Japanese");
        put("Jordan",                  "Asia/Amman",                "Arabic");
        put("Kazakhstan",              "Asia/Almaty",               "Kazakh");
        put("Kuwait",                  "Asia/Kuwait",               "Arabic");
        put("Kyrgyzstan",              "Asia/Bishkek",              "Kyrgyz");
        put("Laos",                    "Asia/Vientiane",            "Lao");
        put("Lebanon",                 "Asia/Beirut",               "Arabic");
        put("Malaysia",                "Asia/Kuala_Lumpur",         "Malay");
        put("Maldives",                "Indian/Maldives",           "Dhivehi");
        put("Mongolia",                "Asia/Ulaanbaatar",          "Mongolian");
        put("Myanmar",                 "Asia/Rangoon",              "Burmese");
        put("Nepal",                   "Asia/Kathmandu",            "Nepali");
        put("North Korea",             "Asia/Pyongyang",            "Korean");
        put("Oman",                    "Asia/Muscat",               "Arabic");
        put("Pakistan",                "Asia/Karachi",              "Urdu");
        put("Palestine",               "Asia/Gaza",                 "Arabic");
        put("Philippines",             "Asia/Manila",               "Filipino");
        put("Qatar",                   "Asia/Qatar",                "Arabic");
        put("Saudi Arabia",            "Asia/Riyadh",               "Arabic");
        put("Singapore",               "Asia/Singapore",            "English");
        put("South Korea",             "Asia/Seoul",                "Korean");
        put("Sri Lanka",               "Asia/Colombo",              "Sinhala");
        put("Syria",                   "Asia/Damascus",             "Arabic");
        put("Taiwan",                  "Asia/Taipei",               "Chinese");
        put("Tajikistan",              "Asia/Dushanbe",             "Tajik");
        put("Thailand",                "Asia/Bangkok",              "Thai");
        put("Timor-Leste",             "Asia/Dili",                 "Tetum");
        put("Turkmenistan",            "Asia/Ashgabat",             "Turkmen");
        put("United Arab Emirates",    "Asia/Dubai",                "Arabic");
        put("UAE",                     "Asia/Dubai",                "Arabic");
        put("Uzbekistan",              "Asia/Tashkent",             "Uzbek");
        put("Vietnam",                 "Asia/Ho_Chi_Minh",          "Vietnamese");
        put("Yemen",                   "Asia/Aden",                 "Arabic");

        // ── Europe ──────────────────────────────────────────────────────────────
        put("Albania",                 "Europe/Tirane",             "Albanian");
        put("Andorra",                 "Europe/Andorra",            "Catalan");
        put("Austria",                 "Europe/Vienna",             "German");
        put("Belarus",                 "Europe/Minsk",              "Belarusian");
        put("Belgium",                 "Europe/Brussels",           "Dutch");
        put("Bosnia and Herzegovina",  "Europe/Sarajevo",           "Bosnian");
        put("Bulgaria",                "Europe/Sofia",              "Bulgarian");
        put("Croatia",                 "Europe/Zagreb",             "Croatian");
        put("Czech Republic",          "Europe/Prague",             "Czech");
        put("Czechia",                 "Europe/Prague",             "Czech");
        put("Denmark",                 "Europe/Copenhagen",         "Danish");
        put("Estonia",                 "Europe/Tallinn",            "Estonian");
        put("Finland",                 "Europe/Helsinki",           "Finnish");
        put("France",                  "Europe/Paris",              "French");
        put("Germany",                 "Europe/Berlin",             "German");
        put("Greece",                  "Europe/Athens",             "Greek");
        put("Hungary",                 "Europe/Budapest",           "Hungarian");
        put("Iceland",                 "Atlantic/Reykjavik",        "Icelandic");
        put("Ireland",                 "Europe/Dublin",             "English");
        put("Italy",                   "Europe/Rome",               "Italian");
        put("Kosovo",                  "Europe/Belgrade",           "Albanian");
        put("Latvia",                  "Europe/Riga",               "Latvian");
        put("Liechtenstein",           "Europe/Vaduz",              "German");
        put("Lithuania",               "Europe/Vilnius",            "Lithuanian");
        put("Luxembourg",              "Europe/Luxembourg",         "Luxembourgish");
        put("Malta",                   "Europe/Malta",              "Maltese");
        put("Moldova",                 "Europe/Chisinau",           "Romanian");
        put("Monaco",                  "Europe/Monaco",             "French");
        put("Montenegro",              "Europe/Podgorica",          "Montenegrin");
        put("Netherlands",             "Europe/Amsterdam",          "Dutch");
        put("North Macedonia",         "Europe/Skopje",             "Macedonian");
        put("Norway",                  "Europe/Oslo",               "Norwegian");
        put("Poland",                  "Europe/Warsaw",             "Polish");
        put("Portugal",                "Europe/Lisbon",             "Portuguese");
        put("Romania",                 "Europe/Bucharest",          "Romanian");
        put("Russia",                  "Europe/Moscow",             "Russian");
        put("San Marino",              "Europe/San_Marino",         "Italian");
        put("Serbia",                  "Europe/Belgrade",           "Serbian");
        put("Slovakia",                "Europe/Bratislava",         "Slovak");
        put("Slovenia",                "Europe/Ljubljana",          "Slovenian");
        put("Spain",                   "Europe/Madrid",             "Spanish");
        put("Sweden",                  "Europe/Stockholm",          "Swedish");
        put("Switzerland",             "Europe/Zurich",             "German");
        put("Turkey",                  "Europe/Istanbul",           "Turkish");
        put("Ukraine",                 "Europe/Kiev",               "Ukrainian");
        put("United Kingdom",          "Europe/London",             "English");
        put("UK",                      "Europe/London",             "English");
        put("Vatican",                 "Europe/Vatican",            "Italian");

        // ── Africa ──────────────────────────────────────────────────────────────
        put("Algeria",                 "Africa/Algiers",            "Arabic");
        put("Angola",                  "Africa/Luanda",             "Portuguese");
        put("Benin",                   "Africa/Porto-Novo",         "French");
        put("Botswana",                "Africa/Gaborone",           "English");
        put("Burkina Faso",            "Africa/Ouagadougou",        "French");
        put("Burundi",                 "Africa/Bujumbura",          "Kirundi");
        put("Cameroon",                "Africa/Douala",             "French");
        put("Cape Verde",              "Atlantic/Cape_Verde",       "Portuguese");
        put("Central African Republic","Africa/Bangui",             "French");
        put("Chad",                    "Africa/Ndjamena",           "French");
        put("Comoros",                 "Indian/Comoro",             "Comorian");
        put("Congo",                   "Africa/Brazzaville",        "French");
        put("Djibouti",                "Africa/Djibouti",           "French");
        put("Egypt",                   "Africa/Cairo",              "Arabic");
        put("Equatorial Guinea",       "Africa/Malabo",             "Spanish");
        put("Eritrea",                 "Africa/Asmara",             "Tigrinya");
        put("Eswatini",                "Africa/Mbabane",            "Swati");
        put("Ethiopia",                "Africa/Addis_Ababa",        "Amharic");
        put("Gabon",                   "Africa/Libreville",         "French");
        put("Gambia",                  "Africa/Banjul",             "English");
        put("Ghana",                   "Africa/Accra",              "English");
        put("Guinea",                  "Africa/Conakry",            "French");
        put("Guinea-Bissau",           "Africa/Bissau",             "Portuguese");
        put("Kenya",                   "Africa/Nairobi",            "Swahili");
        put("Lesotho",                 "Africa/Maseru",             "Sesotho");
        put("Liberia",                 "Africa/Monrovia",           "English");
        put("Libya",                   "Africa/Tripoli",            "Arabic");
        put("Madagascar",              "Indian/Antananarivo",       "Malagasy");
        put("Malawi",                  "Africa/Blantyre",           "Chichewa");
        put("Mali",                    "Africa/Bamako",             "French");
        put("Mauritania",              "Africa/Nouakchott",         "Arabic");
        put("Mauritius",               "Indian/Mauritius",          "English");
        put("Morocco",                 "Africa/Casablanca",         "Arabic");
        put("Mozambique",              "Africa/Maputo",             "Portuguese");
        put("Namibia",                 "Africa/Windhoek",           "English");
        put("Niger",                   "Africa/Niamey",             "French");
        put("Nigeria",                 "Africa/Lagos",              "English");
        put("Rwanda",                  "Africa/Kigali",             "Kinyarwanda");
        put("Senegal",                 "Africa/Dakar",              "French");
        put("Seychelles",              "Indian/Mahe",               "English");
        put("Sierra Leone",            "Africa/Freetown",           "English");
        put("Somalia",                 "Africa/Mogadishu",          "Somali");
        put("South Africa",            "Africa/Johannesburg",       "English");
        put("South Sudan",             "Africa/Juba",               "English");
        put("Sudan",                   "Africa/Khartoum",           "Arabic");
        put("Tanzania",                "Africa/Dar_es_Salaam",      "Swahili");
        put("Togo",                    "Africa/Lome",               "French");
        put("Tunisia",                 "Africa/Tunis",              "Arabic");
        put("Uganda",                  "Africa/Kampala",            "English");
        put("Zambia",                  "Africa/Lusaka",             "English");
        put("Zimbabwe",                "Africa/Harare",             "English");

        // ── Americas ────────────────────────────────────────────────────────────
        put("Antigua and Barbuda",     "America/Antigua",           "English");
        put("Argentina",               "America/Argentina/Buenos_Aires", "Spanish");
        put("Bahamas",                 "America/Nassau",            "English");
        put("Barbados",                "America/Barbados",          "English");
        put("Belize",                  "America/Belize",            "English");
        put("Bolivia",                 "America/La_Paz",            "Spanish");
        put("Brazil",                  "America/Sao_Paulo",         "Portuguese");
        put("Canada",                  "America/Toronto",           "English");
        put("Chile",                   "America/Santiago",          "Spanish");
        put("Colombia",                "America/Bogota",            "Spanish");
        put("Costa Rica",              "America/Costa_Rica",        "Spanish");
        put("Cuba",                    "America/Havana",            "Spanish");
        put("Dominica",                "America/Dominica",          "English");
        put("Dominican Republic",      "America/Santo_Domingo",     "Spanish");
        put("Ecuador",                 "America/Guayaquil",         "Spanish");
        put("El Salvador",             "America/El_Salvador",       "Spanish");
        put("Grenada",                 "America/Grenada",           "English");
        put("Guatemala",               "America/Guatemala",         "Spanish");
        put("Guyana",                  "America/Guyana",            "English");
        put("Haiti",                   "America/Port-au-Prince",    "French");
        put("Honduras",                "America/Tegucigalpa",       "Spanish");
        put("Jamaica",                 "America/Jamaica",           "English");
        put("Mexico",                  "America/Mexico_City",       "Spanish");
        put("Nicaragua",               "America/Managua",           "Spanish");
        put("Panama",                  "America/Panama",            "Spanish");
        put("Paraguay",                "America/Asuncion",          "Spanish");
        put("Peru",                    "America/Lima",              "Spanish");
        put("Saint Kitts and Nevis",   "America/St_Kitts",          "English");
        put("Saint Lucia",             "America/St_Lucia",          "English");
        put("Suriname",                "America/Paramaribo",        "Dutch");
        put("Trinidad and Tobago",     "America/Port_of_Spain",     "English");
        put("United States",           "America/New_York",          "English");
        put("USA",                     "America/New_York",          "English");
        put("US",                      "America/New_York",          "English");
        put("Uruguay",                 "America/Montevideo",        "Spanish");
        put("Venezuela",               "America/Caracas",           "Spanish");

        // ── Oceania ─────────────────────────────────────────────────────────────
        put("Australia",               "Australia/Sydney",          "English");
        put("Fiji",                    "Pacific/Fiji",              "English");
        put("Kiribati",                "Pacific/Tarawa",            "English");
        put("Marshall Islands",        "Pacific/Majuro",            "English");
        put("Micronesia",              "Pacific/Pohnpei",           "English");
        put("Nauru",                   "Pacific/Nauru",             "Nauruan");
        put("New Zealand",             "Pacific/Auckland",          "English");
        put("Palau",                   "Pacific/Palau",             "English");
        put("Papua New Guinea",        "Pacific/Port_Moresby",      "English");
        put("Samoa",                   "Pacific/Apia",              "Samoan");
        put("Solomon Islands",         "Pacific/Guadalcanal",       "English");
        put("Tonga",                   "Pacific/Tongatapu",         "Tongan");
        put("Tuvalu",                  "Pacific/Funafuti",          "Tuvaluan");
        put("Vanuatu",                 "Pacific/Efate",             "English");
    }

    private static void put(String country, String tz, String lang) {
        String key = country.toLowerCase();
        TIMEZONE.put(key, tz);
        LANGUAGE.put(key, lang);
    }

    public static ZoneId getZoneId(String country) {
        if (country == null) return ZoneId.of("UTC");
        String tz = TIMEZONE.get(country.trim().toLowerCase());
        if (tz == null) return ZoneId.of("UTC");
        try {
            return ZoneId.of(tz);
        } catch (Exception e) {
            return ZoneId.of("UTC");
        }
    }

    public static String getLanguage(String country) {
        if (country == null) return "English";
        return LANGUAGE.getOrDefault(country.trim().toLowerCase(), "English");
    }

    private CountryTimeZoneHelper() {}
}
