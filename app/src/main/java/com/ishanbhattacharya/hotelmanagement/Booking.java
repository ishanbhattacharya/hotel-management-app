package com.ishanbhattacharya.hotelmanagement;

public class Booking{
    public String nameC;
    public String bookingDateC;
    public String inDateC;
    public String outDateC;
    public int numberOfDaysC;
    public int numberOfGuestsC;
    public String cityC;
    public String planC;
    public String priceC;

    public Booking(String name, String bookingDate, String inDate, String outDate, int numberOfDays, int numberOfGuests, String city, String plan, String price){
        this.nameC = name;
        this.bookingDateC = bookingDate;
        this.inDateC = inDate;
        this.outDateC = outDate;
        this.numberOfDaysC = numberOfDays;
        this.numberOfGuestsC = numberOfGuests;
        this.planC = plan;
        this.cityC = city;
        this.priceC = price;
    }

    public Booking(){

    }
}
