package com.devt.metrics.domain.outbound;

import java.util.Map;

public interface PdfGenerator {

    byte[] generate(String templateName, Map<String, Object> data);

}
