package com.myproject.backendminiproject.constants;

public class ErrorMessage {
    private ErrorMessage(){}

    public static final String HOUSE_NOT_FOUND = "House not found";
    public static final String ROOM_NOT_FOUND = "Room not found";
    public static final String DEVICE_NOT_FOUND = "Device not found";

    public static final String USER_NOT_FOUND = "User not found";

    public static final String ONLY_ADMIN_ALLOWED =
            "Only admin is allowed to perform this action";

    public static final String USER_NOT_MEMBER_OF_HOUSE =
            "User is not a member of this house";

    public static final String INVALID_DEVICE_CREDENTIALS =
            "Invalid device credentials";

    public static final String DEVICE_ALREADY_ASSIGNED =
            "Device is already assigned to a house";

    public static final String ROOM_NOT_IN_HOUSE =
            "Room must belong to the same house";

    public static final String DEVICE_NOT_ASSIGNED =
            "Device is already assigned";
}
