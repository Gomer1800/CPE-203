public class View {
    private final String tag = "VIEW";
    private final String sessionId ;
    private final String productId ; 
    private final int price ;

    public View(String session, String product, int price) {
        this.sessionId = session;
        this.productId = product;
        this.price = price;
    }

    public String getProduct() { return this.productId; }
    public int getPrice() { return this.price; }
}

