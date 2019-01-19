package parking_manager_agent.util;

public final class Constants {

    public static final long REQUEST_INITIATOR_TIMEOUT = 2000;
    //Boot.class
    public static int N_GENERATED_PARKINGS = 10;
    public static int N_GENERATED_DRIVERS = 20;

    public static int SLEEP_BEFORE_PARKINGS_PRODUCTION = 10;
    // ServiceDescription
    public static final String SD_TYPE = "parking";
    public static int SLEEP_BETWEEN_EACH_DRIVER_PRODUCTION = 1000;


    //    52.1915094; 21.0128345
    public static double MAX_LATITUDE = 52.20952742;
    public static double MAX_LONGITUDE = 21.03085252;
    public static int LONGITUDE = 0;
    public static int LATITUDE = 0;
    public static double MIN_LATITUDE = 52.17349138;
    public static double MIN_LONGITUDE = 20.99481648;
    public static double LOCALIZATION_FLOOR_FACTOR = 1e8;

    public static double FLOOR_FACTOR = 1e2;

    public static int MAX_BASE_PRICE = 10;
    public static int MIN_BASE_PRICE = 1;
    public static int BASE_PRICE = 1;
    public static int STARTING_PRICE = 1;

    public static int MIN_CAPACITY = 10;
    public static int MAX_CAPACITY = 1;
    public static int CAPACITY = 3;

    public static int MAX_OCCUPIED_PLACES = 20;
    public static int MIN_OCCUPIED_PLACES = 2;
    public static int OCCUPIED_PLACES = 0;

    // ParkingAgent.class
    public static double MIN_PARKING_PRICE = 0.2;
    public static double STATIC_PARKING_PRICE = 0.2;

    // DriverTestAgent.class
    public static int TIMEOUT_WAITING_FOR_PARKING_REPLY = 5000;
    public static double PRICE_FACTOR = 0.1;
    public static double DISTANCE_FACTOR = 0.01;
    public static final String SD_NAME = "parking";
    public static int SLEEP_BEFORE_DRIVERS_PRODUCTION = 1000;

}
