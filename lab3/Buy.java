public class Buy {
    private final String tag = "BUY";
    private final String sessionId ;
    private final String productId ; 
    private final int price ;
    private final int quantity ;

    public Buy(String session, String product, int price, int quantity) {
        this.sessionId = session;
        this.productId = product;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProduct() { return this.productId; }
    public int getPrice() { return this.price; };
}
