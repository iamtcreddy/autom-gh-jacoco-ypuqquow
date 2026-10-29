package com.apiservice.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppServiceTest {

    @Test
    void greetCapitalizesName() {
        AppService svc = new AppService();
        assertEquals("Hello, Alice", svc.greet("alice"));
    }

    @Test
    void trackEventAppends() {
        AppService svc = new AppService();
        assertEquals(1, svc.trackEvent("login"));
        assertEquals(2, svc.trackEvent("logout"));
        assertEquals(2, svc.getEvents().size());
    }
}
