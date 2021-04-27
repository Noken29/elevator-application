package com.company;

public class Main {

    public static void main(String[] args) {
        LiftCore liftCore = new LiftCore();
        LiftService liftService = new LiftService(liftCore, false);
        liftService.setTimeout(520);
        liftService.startWorking();
    }
}
