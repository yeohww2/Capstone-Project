package com.parking.model;

public class CarSlotRentalDetails {
    private String slotNumber;
    private String carPlateNumber;
    private String startDate;
    private String endDate;

    // Constructor
    public CarSlotRentalDetails(String slotNumber, String carPlateNumber, String startDate, String endDate) {
        this.slotNumber = slotNumber;
        this.carPlateNumber = carPlateNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters
    public String getSlotNumber() {
        return slotNumber;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
