package com.mycompany.myapp.constant;

import java.util.List;

public class DailyHealthStatus {

    public static final String LIGHT = "Light";
    public static final String NORMAL = "Normal";
    public static final String EMERGENCY = "Emergency";
    public static final String CRITICAL = "Critical";
    public static final List<String> listDailyHealthStatus = List.of(LIGHT, NORMAL, EMERGENCY, CRITICAL);
}
