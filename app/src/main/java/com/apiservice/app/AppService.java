package com.apiservice.app;

import com.apiservice.lib.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AppService {
    private final List<String> events = new ArrayList<>();

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello, world";
        }
        return "Hello, " + StringUtils.capitalize(name);
    }

    public int trackEvent(String event) {
        if (event == null) {
            return -1;
        }
        events.add(event);
        return events.size();
    }

    public List<String> getEvents() {
        return events;
    }
}
