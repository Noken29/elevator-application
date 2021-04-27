package com.company;

import com.company.util.RandomPassenger;

import java.util.ArrayList;

public class LiftCore {
    public final int NUMBER_OF_FLOORS;
    public final int LIFTING_CAPACITY = 5;
    public final int MIN_PASSENGERS_COUNT = 0;
    public final int MAX_PASSENGERS_COUNT = 10;
    public final int MIN_COUNT_OF_FLOORS = 5;
    public final int MAX_COUNT_OF_FLOORS = 15;
    private final ArrayList <PassengerList> passengers;
    private final ArrayList<Integer> passengersInLift;
    private int liftState;
    private boolean isRise;
    private int priority;

    LiftCore()
    {
        NUMBER_OF_FLOORS = MIN_COUNT_OF_FLOORS + (int)Math.round(Math.random() * MAX_COUNT_OF_FLOORS);
        passengers = new ArrayList<>();
        passengersInLift = new ArrayList<>();

        passengers.add(null);
        liftState = 1;
        isRise = true;
        for (int i = 1; i <= NUMBER_OF_FLOORS; i++) {
            int passengersPerFloor = RandomPassenger.getRandomNumber(MIN_PASSENGERS_COUNT, MAX_PASSENGERS_COUNT);

            passengers.add(new PassengerList(i));

            for (int j = 0; j < passengersPerFloor; j++) {
                passengers.get(i).add(RandomPassenger.getRandomPassenger(i, NUMBER_OF_FLOORS));
            }
        }
    }

    @Override
    public String toString() {
        final String DEFAULT_ALIGNMENT = "                    ";

        StringBuilder alignment = new StringBuilder(DEFAULT_ALIGNMENT);
        StringBuilder picture = new StringBuilder();
        for (int i = NUMBER_OF_FLOORS; i >= 1; i--) {
            picture.append(i);
            picture.append(" |");
            if(liftState == i)
            {
                String marker = (isRise)?"^":"v";
                picture.append(marker);

                if(!passengersInLift.isEmpty())
                    for (int v : passengersInLift) {
                        String tmp = v + " ";
                        picture.append(tmp);
                    }

                alignment.append(" ".repeat(2 * (MAX_PASSENGERS_COUNT - passengersInLift.size()) - 1));
            }
            else
                alignment.append(DEFAULT_ALIGNMENT);

            alignment.append("| ");

            picture.append(alignment);
            picture.append(passengers.get(i).toString());
            picture.append("\n");

            alignment = new StringBuilder(DEFAULT_ALIGNMENT);
        }
        return picture.toString();
    }

    public PassengerList getPassengersOnFloor(int floor) {
        return passengers.get(floor);
    }

    public ArrayList<Integer> getPassengersInLift() {
        return passengersInLift;
    }

    public int getLiftState() {
        return liftState;
    }

    public void increaseLiftState() {
        this.liftState++;
    }

    public void decreaseLiftState() {
        this.liftState--;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isRise() {
        return isRise;
    }

    public void setRise(boolean rise) {
        isRise = rise;
    }

}
