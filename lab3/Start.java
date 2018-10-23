public class Start {
    private final String tag ;
    private final String sessionId ;
    private final String customerId ; 

    public Start(String entryTag, String session, String customer) {
        this.tag = entryTag;
        this.sessionId = session;
        this.customerId = customer;
    }
}
