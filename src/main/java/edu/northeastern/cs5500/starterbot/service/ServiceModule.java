package edu.northeastern.cs5500.starterbot.service;

import dagger.Binds;
import dagger.Module;

@Module
public interface ServiceModule {
    @Binds
    OpenTelemetry bindOpenTelemetry(OpenTelemetryService service);
}
