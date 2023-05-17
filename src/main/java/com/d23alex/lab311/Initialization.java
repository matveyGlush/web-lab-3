package com.d23alex.lab311;

import java.util.function.Predicate;

public class Initialization {
    public static Predicate<AreaChecking.UserInputs> area() {
        final Predicate<AreaChecking.UserInputs> belongsAreaInTopLeftQuarter = inputs ->
                (Math.pow(inputs.point().x(), 2) + Math.pow(inputs.point().y(), 2) <= Math.pow(inputs.r() / 2, 2)
                        && inputs.point().x() <= 0 && inputs.point().y() >= 0);

        final Predicate<AreaChecking.UserInputs> belongsAreaInBottomLeftQuarter = inputs ->
                (-1 * inputs.r() / 2) <= inputs.point().x() && inputs.point().x() <= 0
                        && (-1 * inputs.r()) <= inputs.point().y() && inputs.point().y() <= 0;

        final Predicate<AreaChecking.UserInputs> belongsAreaInBottomRightQuarter = inputs ->
                inputs.point().x() >= 0 && inputs.point().y() <= 0
                        && Math.abs(inputs.point().y()) + inputs.point().x() <= (inputs.r() / 2);

        final Predicate<AreaChecking.UserInputs> belongsAreaInTopRightQuarter = inputs -> false;

        return belongsAreaInTopLeftQuarter.or(
                belongsAreaInBottomLeftQuarter.or(
                        belongsAreaInBottomRightQuarter.or(
                                belongsAreaInTopRightQuarter)));
    }
}
