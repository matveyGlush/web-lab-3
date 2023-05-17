package com.d23alex.lab311;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Model;

@Model
@SessionScoped
public class ResultDisplay implements Serializable {
    private static final long serialVersionUID = 1L;

    private int checksDisplayed = 1000;
    private double x = 1;
    private double y = 1;
    private double r = 5;

    public void performAreaCheck() {
        final AreaChecking.UserInputs inputs = new AreaChecking.UserInputs(new AreaChecking.Point(x, y), r);
        Database.save(AreaChecking.constructCheck(inputs, Initialization.area()));
    }

    public String checksRecalculatedForCurrentRAsJSON() {
        final Function<AreaChecking.Check, AreaChecking.Check> recalculateForCurrentR =
                check -> AreaChecking.constructCheck(new AreaChecking.UserInputs(check.userInputs().point(), r),
                        Initialization.area());
        return "[" +
                toJSON(Database.getNLast(checksDisplayed).stream().map(recalculateForCurrentR).toList())
                + "]";
    }

    public String checkHistoryAsJSON() {
        return "[" + toJSON(Database.getNLast(checksDisplayed)) + "]";
    }

    private static String toJSON(List<AreaChecking.Check> checks) {
        return String.join(", ", checks.stream().map(ResultDisplay::toJSON).toList());
    }

    private static String toJSON(AreaChecking.Check check) {
        return "{"
                + "\"x\": " + check.userInputs().point().x()
                + ", \"y\": " + check.userInputs().point().y()
                + ", \"r\": " + check.userInputs().r()
                + ", \"areaContainsPoint\": " + "\"" + check.areaContainsPoint() + "\""
                + ", \"requestDate\": " + "\"" +  check.requestDate() + "\""
                + ", \"calculationTime\": " + "\"" + check.calculationTime() + "\""
                + "}";
    }


    public int getChecksDisplayed() {
        return checksDisplayed;
    }

    public void setChecksDisplayed(int checksDisplayed) {
        this.checksDisplayed = checksDisplayed;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
}
