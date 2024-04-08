package com.policarp.journal.models;

import java.util.Date;

public class Mark {
    public int Value;
    public String Marker;
    public Date MarkSetDate;
    public Mark() {
    }

    public Mark(int value, Teacher marker) {
        Value = value;
        Marker = marker.FullName;
        MarkSetDate = new Date();
    }
}
