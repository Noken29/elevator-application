package com.company;

import java.util.ArrayList;

public class PassengerList {
    private final int floor;
    private final ArrayList<Integer> passengersUp, passengersDown;

    public PassengerList(int floor) {
        this.floor = floor;
        passengersUp = new ArrayList<>();
        passengersDown = new ArrayList<>();
    }

    public void add(int passenger) {
        if (passenger > floor)
            passengersUp.add(passenger);
        else passengersDown.add(passenger);
    }

    public void remove(int passenger) {
        if (passenger > floor)
            passengersUp.remove(passengersUp.indexOf(passenger));
        else passengersDown.remove(passengersDown.indexOf(passenger));
    }

    public boolean firstIsEmpty() {
        return passengersUp.isEmpty();
    }

    public boolean secondIsEmpty() {
        return passengersDown.isEmpty();
    }

    public int size() {
        return passengersUp.size() + passengersDown.size();
    }

    public int size(boolean isRise) {
        return isRise ? passengersUp.size() : passengersDown.size();
    }

    public ArrayList<Integer> getAttractive() {
        return (passengersUp.size() > passengersDown.size()) ? passengersUp : passengersDown;
    }

    public ArrayList<Integer> getAttractive(boolean isRise) {
        return isRise ? passengersUp : passengersDown;
    }

    public boolean compare()
    {
        return passengersUp.size() > passengersDown.size();
    }

    @Override
    public String toString() {
        StringBuilder array = new StringBuilder();
        for (int v : passengersUp) {
            array.append(v);
            array.append(" ");
        }
        for (int v : passengersDown) {
            array.append(v);
            array.append(" ");
        }
        return array.toString();
    }
}
