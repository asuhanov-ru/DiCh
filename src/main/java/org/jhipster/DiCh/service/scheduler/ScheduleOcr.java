package org.jhipster.dich.service.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.jhipster.dich.service.OCRService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleOcr {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final OCRService ocrService;
    private final Logger log = LoggerFactory.getLogger(ScheduleOcr.class);

    public ScheduleOcr(OCRService ocrService) {
        this.ocrService = ocrService;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws Exception {
        log.debug("The time is now {}", dateFormat.format(new Date()));
        ocrService.processOCRTasks();
    }
}
