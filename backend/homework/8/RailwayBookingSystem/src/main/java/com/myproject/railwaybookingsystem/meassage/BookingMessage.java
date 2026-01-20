package com.myproject.railwaybookingsystem.meassage;

public class BookingMessage {

    private final String bookingId;
    private final int age;
    private int retryCount;

    public BookingMessage(String bookingId, int age) {
        this.bookingId = bookingId;
        this.age = age;
        this.retryCount = 0;
    }

    public String getBookingId() { return bookingId; }
    public int getAge() { return age; }
    public int getRetryCount() { return retryCount; }

    public void incrementRetry() {
        this.retryCount++;
    }
}

