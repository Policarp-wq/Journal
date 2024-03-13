package com.policarp.journal.models;

import java.util.Date;

public class Mark {
    public int Value;
    public Teacher Marker;
    public Date MarkSetDate;
    public Mark(int value, Teacher marker) {
        Value = value;
        Marker = marker;
        MarkSetDate = new Date();
    }
}
