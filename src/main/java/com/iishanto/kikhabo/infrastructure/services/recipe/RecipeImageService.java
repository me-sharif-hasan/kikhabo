package com.iishanto.kikhabo.infrastructure.services.recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.services.storage.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RecipeImageService {

    private static final Logger log = LoggerFactory.getLogger(RecipeImageService.class);
    private static final String PIXABAY_SEARCH = "https://pixabay.com/api/";

    private final RestClient restClient;
    private final S3Service s3Service;
    private final ObjectMapper objectMapper;
    private final String pixabayApiKey;

    public RecipeImageService(RestClient restClient,
                               S3Service s3Service,
                               ObjectMapper objectMapper,
                               @Value("${pixabay.apikey}") String pixabayApiKey) {
        this.restClient = restClient;
        this.s3Service = s3Service;
        this.objectMapper = objectMapper;
        this.pixabayApiKey = pixabayApiKey;
    }

    /**
     * Resolves the best available image for a cached recipe:
     *   1. If the recipe has a MongoDB image URL: download it, verify it is a real image,
     *      upload to S3 (so we own the copy), and return the S3 URL.
     *   2. If the MongoDB image is missing, unreachable, or not an image: fall back to Pexels.
     * Returns null on total failure — image is optional for the caller.
     */
    public String resolveImage(RecipeCacheEntity recipe) {
        String mongoImageUrl = recipe.getImage();

        if (mongoImageUrl != null && !mongoImageUrl.isBlank()) {
            log.info("[RecipeImage] Trying MongoDB image for recipe='{}' url='{}'", recipe.getName(), mongoImageUrl);
            String s3Url = downloadAndUpload(mongoImageUrl, recipe.getName());
            if (s3Url != null) return s3Url;
            log.warn("[RecipeImage] MongoDB image invalid or unreachable for recipe='{}', falling back to Pexels", recipe.getName());
        } else {
            log.info("[RecipeImage] No MongoDB image for recipe='{}', going to Pexels", recipe.getName());
        }

        return fetchFromPixabay(recipe);
    }

    // ── Step 1: download MongoDB image → verify → upload to S3 ───────────────

    private String downloadAndUpload(String url, String recipeName) {
        try {
            ResponseEntity<byte[]> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .toEntity(byte[].class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.warn("[RecipeImage] Non-2xx ({}) downloading image for recipe='{}'",
                        response.getStatusCode(), recipeName);
                return null;
            }

            MediaType contentType = response.getHeaders().getContentType();
            if (contentType == null || !contentType.getType().equals("image")) {
                log.warn("[RecipeImage] Content-Type '{}' is not an image for recipe='{}'",
                        contentType, recipeName);
                return null;
            }

            byte[] bytes = response.getBody();
            if (bytes == null || bytes.length == 0) {
                log.warn("[RecipeImage] Empty body downloading image for recipe='{}'", recipeName);
                return null;
            }

            String extension = "." + contentType.getSubtype().split(";")[0]; // e.g. ".jpeg"
            String s3Url = s3Service.uploadBytes(bytes, contentType.toString(), "recipe-images", extension);
            log.info("[RecipeImage] MongoDB image uploaded to S3 for recipe='{}' → {}", recipeName, s3Url);
            return s3Url;

        } catch (Exception e) {
            log.warn("[RecipeImage] Failed to download/upload MongoDB image for recipe='{}': {}", recipeName, e.getMessage());
            return null;
        }
    }

    // ── Step 2: Pixabay fallback (direct URL, no S3) ─────────────────────────

    private String fetchFromPixabay(RecipeCacheEntity recipe) {
        String query = recipe.getNameNormalized();
        if (query == null || query.isBlank()) {
            log.warn("[RecipeImage] Skipping Pixabay — no normalized name for recipe='{}'", recipe.getName());
            return null;
        }

        log.info("[RecipeImage] Searching Pixabay for query='{}'", query);
        try {
            String encodedQuery = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
            String response = restClient.get()
                    .uri(PIXABAY_SEARCH + "?key=" + pixabayApiKey
                            + "&q=" + encodedQuery
                            + "&image_type=photo"
                            + "&category=food"
                            + "&orientation=horizontal"
                            + "&safesearch=true"
                            + "&per_page=3"
                            + "&order=popular")
                    .retrieve()
                    .body(String.class);

            if (response == null) {
                log.warn("[RecipeImage] Empty response from Pixabay for query='{}'", query);
                return null;
            }

            JsonNode root = objectMapper.readTree(response);
            long totalHits = root.path("totalHits").asLong(0);
            log.debug("[RecipeImage] Pixabay returned {} hits for query='{}'", totalHits, query);

            String url = root.path("hits").path(0).path("webformatURL").asText(null);
            if (url == null || url.isBlank()) {
                log.warn("[RecipeImage] No hits in Pixabay response for query='{}' (totalHits={})", query, totalHits);
                return null;
            }

            log.info("[RecipeImage] Pixabay image resolved for recipe='{}' query='{}' → {}", recipe.getName(), query, url);
            return url;

        } catch (Exception e) {
            log.error("[RecipeImage] Pixabay request failed for query='{}': {}", query, e.getMessage());
            return null;
        }
    }
}
