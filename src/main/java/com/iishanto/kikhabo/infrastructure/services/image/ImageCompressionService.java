package com.iishanto.kikhabo.infrastructure.services.image;

import com.iishanto.kikhabo.infrastructure.config.GeminiVisionProperties;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Resizes and compresses images before they are uploaded to external AI APIs.
 * Always outputs JPEG to guarantee consistent encoding and predictable file size.
 */
@Service
@AllArgsConstructor
public class ImageCompressionService {

    private final GeminiVisionProperties visionProperties;
    private final Logger logger;

    /**
     * Compresses and/or resizes the image if it exceeds the configured thresholds.
     *
     * @param imageBytes raw image bytes (any format supported by Java ImageIO)
     * @return JPEG-encoded bytes, sized within the configured limit
     */
    public byte[] compressIfNeeded(byte[] imageBytes) throws IOException {
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (original == null) {
            logger.warn("[ImageCompression] Could not decode image — passing raw bytes to API as-is");
            return imageBytes;
        }

        logger.info("[ImageCompression] Original image: {}x{} px", original.getWidth(), original.getHeight());
        BufferedImage resized = resizeIfNeeded(original);

        byte[] jpegBytes = encodeAsJpeg(resized, 0.85f);
        logger.info("[ImageCompression] JPEG encoded at quality=0.85 -> {} bytes (limit: {} bytes)",
                jpegBytes.length, visionProperties.getMaxImageSizeBytes());

        if (jpegBytes.length > visionProperties.getMaxImageSizeBytes()) {
            logger.info("[ImageCompression] Still over limit — re-encoding at quality=0.60 to reduce size");
            jpegBytes = encodeAsJpeg(resized, 0.60f);
            logger.info("[ImageCompression] Re-encoded at quality=0.60 -> {} bytes", jpegBytes.length);
        }

        return jpegBytes;
    }

    private BufferedImage resizeIfNeeded(BufferedImage src) {
        int maxDim = visionProperties.getMaxImageDimension();
        int w = src.getWidth();
        int h = src.getHeight();
        if (w <= maxDim && h <= maxDim) {
            logger.info("[ImageCompression] No resize needed ({}x{} within {}px limit)", w, h, maxDim);
            return src;
        }

        float scale = Math.min((float) maxDim / w, (float) maxDim / h);
        int newW = Math.max(1, (int) (w * scale));
        int newH = Math.max(1, (int) (h * scale));
        logger.info("[ImageCompression] Resizing {}x{} -> {}x{} (scale: {}, max dim: {}px)", w, h, newW, newH, String.format("%.2f", scale), maxDim);

        BufferedImage out = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, newW, newH);
        g.drawImage(src, 0, 0, newW, newH, null);
        g.dispose();
        return out;
    }

    private byte[] encodeAsJpeg(BufferedImage image, float quality) throws IOException {
        // Flatten alpha channel onto white background — JPEG does not support transparency
        BufferedImage rgb = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rgb.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.drawImage(image, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageWriteParam params = writer.getDefaultWriteParam();
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionQuality(quality);
        writer.setOutput(new MemoryCacheImageOutputStream(baos));
        writer.write(null, new IIOImage(rgb, null, null), params);
        writer.dispose();
        return baos.toByteArray();
    }
}
