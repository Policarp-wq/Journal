package com.policarp.journal;

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
