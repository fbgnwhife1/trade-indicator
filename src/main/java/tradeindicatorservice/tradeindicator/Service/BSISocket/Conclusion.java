package tradeindicatorservice.tradeindicator.Service.BSISocket;

public enum Conclusion {

    orderbook("orderbook", "호가");

    private String type;
    private String name;

    Conclusion(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }
}
