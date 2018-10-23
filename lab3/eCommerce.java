/*
- eCommerce Program
-Take log file as command line argument
-Read file, store entries for processing
-out put statistics
    -sessionsFromCustomer : Map<String, List<String>> // key = customerId value = sessionIds
    -viewsFromSession : Map<String, List<Views>> // key = sessionId value = View 
    -buysFromSession : Map<String, List<Buy>> // key = sessionId value = Buy
    -endsFromSession : Map<String, List<End>> // key = sessionId value = End

-entries types are classes that implement entry interface,
 with following attributes/methods
    -START
        -entryTag : String
        -sessionId : String
        -customerId : String
        +get
    -VIEW
        -entryTag : String
        -sessionId : String
        -productId : String
        -price : int
    -BUY
        -entryTag : String
        -sessionId : String
        -productId : String
        -price : int
        -quantity : int
    -END
        -entryTag : String
        -sessionId : String
*/
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.io.FileNotFoundException;

public class eCommerce {
    public static void main( String [] args) throws FileNotFoundException {

        Map<String, List<String>> sessionsFromCustomer = new HashMap<>();
        Map<String, List<View>> viewsFromSession = new HashMap<>();
        Map<String, List<Buy>> buysFromSession = new HashMap<>();

        LogAnalyzer.populateDataStructures( 
                args,
                sessionsFromCustomer,
                viewsFromSession,
                buysFromSession) ;

        LogAnalyzer.printOutExample(sessionsFromCustomer,
                viewsFromSession,
                buysFromSession);
        
        LogAnalyzer.printSessionPriceDifference(sessionsFromCustomer,
                viewsFromSession,
                buysFromSession);
        
        LogAnalyzer.printCustomerItemViewsNoPurchase(sessionsFromCustomer,
                viewsFromSession,
                buysFromSession);
        
        LogAnalyzer.printCustomerItemViewsForPurchase(sessionsFromCustomer,
                viewsFromSession,
                buysFromSession);
    
    }
}
