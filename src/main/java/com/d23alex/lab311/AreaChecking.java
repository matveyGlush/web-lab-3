package com.d23alex.lab311;

import java.sql.Time;
import java.util.Date;
import java.util.function.Predicate;

public class AreaChecking {
    public record Check(
            UserInputs userInputs,
            boolean areaContainsPoint,
            Date requestDate,
            Time calculationTime
    ){}


    public static Check constructCheck(UserInputs inputs, Predicate<UserInputs> areaContainsPoint) {
        return new Check(inputs, areaContainsPoint.test(inputs), new Date(), new Time(0, 0, 1));
    }

    public record Point (double x, double y){
    }

    public record UserInputs(Point point, double r) {
    }
}
