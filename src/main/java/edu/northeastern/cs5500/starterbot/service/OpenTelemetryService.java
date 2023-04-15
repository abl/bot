package edu.northeastern.cs5500.starterbot.service;

import lombok.Getter;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Singleton;

@Singleton
@Slf4j
public class OpenTelemetryService implements Service {

    @Getter Tracer tracer;

    @Inject
    public OpenTelemetryService() {
        tracer = openTelemetry.getTracer("cs5500-s23-starterbot", "1.0.0");
    }

    @Override
    public void register() {
        log.info("OpenTelemetryService > register");
    }
}
