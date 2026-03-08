package com.devt.metrics.infrastructure.outbound.pdf;

import com.devt.metrics.domain.outbound.PdfGenerator;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

@OutboundAdapter
public class PdfGeneratorAdapter implements PdfGenerator {

    private final TemplateEngine templateEngine;

    public PdfGeneratorAdapter(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public byte[] generate(String templateName, Map<String, Object> data) {
        String html = renderTemplate(templateName, data);
        return renderPdf(html);
    }

    private String renderTemplate(String templateName, Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        return templateEngine.process(templateName, context);
    }

    private byte[] renderPdf(String html) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
