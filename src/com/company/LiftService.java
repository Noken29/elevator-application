package com.company;

import com.company.util.RandomPassenger;

import java.util.ArrayList;
import java.util.Collections;

public class LiftService {
    private final LiftCore liftCore;
    private int frames = Integer.MAX_VALUE;
    private int step;
    private int timeout = 500;
    private final boolean isGenerateNewPassengers;

    LiftService(LiftCore liftCore, boolean isGenerateNewPassengers) {
        this.liftCore = liftCore;
        this.isGenerateNewPassengers = isGenerateNewPassengers;
    }

    LiftService(LiftCore liftCore, boolean isGenerateNewPassengers, int frames) {
        this.liftCore = liftCore;
        this.isGenerateNewPassengers = isGenerateNewPassengers;
        this.frames = frames;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void startWorking() {
        print();
        initiate();
        System.out.println(liftCore.getPriority());
        while(step < frames)
        {
             if(!liftCore.getPassengersInLift().isEmpty()) {
                 print();
                 if (liftCore.isRise()) {
                     while (liftCore.getLiftState() < liftCore.getPriority()){
                         liftCore.increaseLiftState();
                         removePassengersFromLift(liftCore.getLiftState());
                         uploadPassengers(liftCore.getLiftState());
                         print();

                     }
                 } else {
                     while (liftCore.getLiftState() > liftCore.getPriority()){
                         liftCore.decreaseLiftState();
                         removePassengersFromLift(liftCore.getLiftState());
                         uploadPassengers(liftCore.getLiftState());
                         print();

                     }
                 }
                 if(liftCore.getLiftState() == liftCore.getPriority() && !liftCore.getPassengersInLift().isEmpty())
                 {
                     liftCore.setRise(!liftCore.isRise());
                     adjustPriority();
                 }
            }
            else
                if(!initiate()) break;
        }
    }

    private boolean initiate() {
        ArrayList<Integer> passengersInLift = liftCore.getPassengersInLift(), passengers;
        if(findFirstFloorWithPassengers()) {
            if (liftCore.isRise())
                for (int i = liftCore.getLiftState(); i < liftCore.getPriority(); i++) {
                    print();
                    liftCore.increaseLiftState();
                }
            else
                for (int i = liftCore.getLiftState(); i > liftCore.getPriority(); i--) {
                    print();
                    liftCore.decreaseLiftState();
                }

            passengers = liftCore.getPassengersOnFloor(liftCore.getLiftState()).getAttractive();
            liftCore.setRise(liftCore.getPassengersOnFloor(liftCore.getLiftState()).compare());
            if(passengers.size() <= liftCore.LIFTING_CAPACITY) {
                passengersInLift.addAll(passengers);
                passengers.removeAll(passengersInLift);
            } else {
                while(passengersInLift.size() < liftCore.LIFTING_CAPACITY){
                    passengersInLift.add(passengers.get(0));
                    passengers.remove(0);
                }
            }
            adjustPriority();
            return true;
        }
        return false;
    }

    private boolean findFirstFloorWithPassengers()
    {
        int floor = liftCore.getLiftState(), floorUnderLift = floor, floorUpLift = floor;
        for (int i = floor; i <= liftCore.NUMBER_OF_FLOORS; i++) {
            if(!liftCore.getPassengersOnFloor(i).getAttractive().isEmpty())
            {
                floorUpLift = i;
                break;
            }
        }
        for (int i = floor; i >= 1; i--) {
            if(!liftCore.getPassengersOnFloor(i).getAttractive().isEmpty())
            {
                floorUnderLift = i;
                break;
            }
        }
        if(floorUpLift == floorUnderLift && liftCore.getPassengersOnFloor(floor).getAttractive().isEmpty())
            return false;
        liftCore.setRise(floorUpLift - floor >= floor - floorUnderLift);
        floor = (floorUpLift - floor >= floor - floorUnderLift) ? floorUpLift : floorUnderLift;
        liftCore.setPriority(floor);
        return true;
    }

    private void removePassengersFromLift(int floor)
    {
        int count = Collections.frequency(liftCore.getPassengersInLift(), floor);
        if(liftCore.getPassengersInLift().removeIf(Integer.valueOf(floor)::equals) && isGenerateNewPassengers)
        {
            for (int i = 0; i < count && liftCore.getPassengersOnFloor(floor).size() < liftCore.MAX_PASSENGERS_COUNT; i++) {
                liftCore.getPassengersOnFloor(floor).add(RandomPassenger.getRandomPassenger(floor, liftCore.NUMBER_OF_FLOORS));
            }
        }

    }

    private void uploadPassengers(int floor) {
        ArrayList<Integer> passengersInLift = liftCore.getPassengersInLift(), passengersOnFloor = liftCore.getPassengersOnFloor(floor).getAttractive(liftCore.isRise());
        if (passengersInLift.size() < liftCore.LIFTING_CAPACITY) {
            while (passengersInLift.size() < liftCore.LIFTING_CAPACITY && !passengersOnFloor.isEmpty()) {
                passengersInLift.add(passengersOnFloor.get(0));
                passengersOnFloor.remove(0);
            }
            if(!passengersInLift.isEmpty())
                adjustPriority();
        }
    }

    private void adjustPriority()
    {
        int m = (liftCore.isRise()) ? Collections.max(liftCore.getPassengersInLift()) : Collections.min(liftCore.getPassengersInLift());
        liftCore.setPriority(m);
    }

    private void print()
    {
        step++;
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("***** Step "+ step + " *****");
        System.out.println(liftCore.toString());
    }
}
