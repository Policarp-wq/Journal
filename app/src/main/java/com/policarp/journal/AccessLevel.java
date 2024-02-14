package com.policarp.journal;

public class AccessLevel {
    public enum Levels{
        Zero,
        Low,
        Medium,
        High
    }
    public static Levels getLevel(School.Position position){
        if(position == School.Position.Principal)
            return Levels.High;
        if(position == School.Position.Teacher)
            return Levels.Medium;
        if(position == School.Position.Student || position == School.Position.SecurityGuard)
            return Levels.Low;
        return Levels.Zero;
    }
}
