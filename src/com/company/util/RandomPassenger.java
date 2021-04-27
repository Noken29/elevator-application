package com.company.util;

public class RandomPassenger {

    private RandomPassenger() { }

    public static int getRandomNumber(int from, int to)
    {
        return from + (int)Math.round(Math.random() * to);
    }


    public static int getRandomPassenger(int floor, int numberOfFloors)
    {
        int passenger = 1 + (int)Math.round(Math.random() * (numberOfFloors - 1));
        if(passenger == floor)
        {
            int shift = (int)Math.round(Math.random());
            if(floor > 1 && floor < numberOfFloors)
                passenger += ((shift == 0) ? -1 : 1);
            else passenger += ((floor == numberOfFloors) ? -1 : 1);
        }
        return passenger;
    }
}
