import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NullPointerException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LogAnalyzer
{
      //constants to be used when pulling data out of input
      //leave these here and refer to them to pull out values
      //
      // indexes into array of string for each type of field
   private static final String START_TAG = "START";
   private static final int START_NUM_FIELDS = 3;
   private static final int START_SESSION_ID = 1; // index within string array
   private static final int START_CUSTOMER_ID = 2; // "      "
   private static final String BUY_TAG = "BUY";
   private static final int BUY_NUM_FIELDS = 5;
   private static final int BUY_SESSION_ID = 1;
   private static final int BUY_PRODUCT_ID = 2;
   private static final int BUY_PRICE = 3;
   private static final int BUY_QUANTITY = 4;
   private static final String VIEW_TAG = "VIEW";
   private static final int VIEW_NUM_FIELDS = 4;
   private static final int VIEW_SESSION_ID = 1;
   private static final int VIEW_PRODUCT_ID = 2;
   private static final int VIEW_PRICE = 3;
   private static final String END_TAG = "END";
   private static final int END_NUM_FIELDS = 2;
   private static final int END_SESSION_ID = 1;

      //a good example of what you will need to do next
      //creates a map of sessions to customer ids
      //System.out.println("Interface Methods");
   private static void processStartEntry( 
      final String[] words,
      final Map<String, List<String>> sessionsFromCustomer) // key = customerid , value = session arrays
   {
      if (words.length != START_NUM_FIELDS)
      {
         return;
      }

         //check if there already is a list entry in the map
         //for this customer, if not create one
      List<String> sessions = sessionsFromCustomer
         .get(words[START_CUSTOMER_ID]);
      if (sessions == null)
      {
         sessions = new LinkedList<>();
         sessionsFromCustomer.put(words[START_CUSTOMER_ID], sessions);
      }

         //now that we know there is a list, add the current session
      sessions.add(words[START_SESSION_ID]);
   }

      //similar to processStartEntry, should store relevant view
      //data in a map - model on processStartEntry, but store
      //your data to represent a view in the map (not a list of strings)
   private static void processViewEntry(
           final String[] words,
           final Map<String, List<View>> viewsFromCustomer
      /* add parameters as needed */
      )
   {
      if (words.length != VIEW_NUM_FIELDS)
      {
         return;
      }

         //check if there already is a list entry in the map
         //for this session, if not create one
      List<View> views = viewsFromCustomer
         .get(words[VIEW_SESSION_ID]);
      if (views == null)
      {
         views = new LinkedList<>();
         viewsFromCustomer.put(words[VIEW_SESSION_ID], views);
      }

         //now that we know there is a list, add the current session
      views.add(new View(words[VIEW_SESSION_ID],
                  words[VIEW_PRODUCT_ID],
                  Integer.parseInt(words[VIEW_PRICE])
                  ));
   }

      //similar to processStartEntry, should store relevant purchases
      //data in a map - model on processStartEntry, but store
      //your data to represent a purchase in the map (not a list of strings)
   private static void processBuyEntry(
      final String[] words,
      final Map<String, List<Buy>> buysFromCustomer
      )
   {
      if (words.length != BUY_NUM_FIELDS)
      {
         return;
      }

         //check if there already is a list entry in the map
         //for this session, if not create one
      List<Buy> buys = buysFromCustomer
         .get(words[BUY_SESSION_ID]);
      if (buys == null)
      {
         buys = new LinkedList<>();
         buysFromCustomer.put(words[BUY_SESSION_ID], buys);
      }

         //now that we know there is a list, add the current session
      buys.add(new Buy(words[BUY_SESSION_ID],
                  words[BUY_PRODUCT_ID],
                  Integer.parseInt(words[BUY_PRICE]),
                  Integer.parseInt(words[BUY_QUANTITY])
                  ));
   }

   private static void processEndEntry(final String[] words)
   {
      if (words.length != END_NUM_FIELDS)
      {
         return;
      }
   }

      //this is called by processFile below - its main purpose is
      //to process the data using the methods you write above
   private static void processLine(
      final String line,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      )
   {
      final String[] words = line.split("\\h");

      if (words.length == 0)
      {
         return;
      }

      switch (words[0])
      {
         case START_TAG:
            processStartEntry(words, sessionsFromCustomer);
            break;
         case VIEW_TAG:
            processViewEntry(words, viewsFromSession /* add arguments as needed */ );
            break;
         case BUY_TAG:
            processBuyEntry(words, buysFromSession /* add arguments as needed */ );
            break;
         case END_TAG:
            processEndEntry(words/* add arguments as needed */ );
            break;
      }
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   public static void printSessionPriceDifference(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession) 
           throws NullPointerException
   {
      System.out.println("\nPrice Difference for Purchased Product by Session");

        
      //for each customer, get their sessions
      //for each session cycle through views and buys
      for(Map.Entry<String, List<String>> entry: 
         sessionsFromCustomer.entrySet()) 
      {
         List<String> sessions = entry.getValue();
         for(String sessionID : sessions)
         {
             double averageViewPrice = 0.0;
             int numViews = 0;

             try {
                 // for each session, calculate average price of views
                 System.out.println(sessionID);
                 List<View> theViews = viewsFromSession.get(sessionID);
                 for (View thisView: theViews)
                 {
                     numViews += 1;
                     averageViewPrice += thisView.getPrice();
                 }
                 averageViewPrice = averageViewPrice/numViews;

                 // cycle through buys and compute difference here
                 List<Buy> theBuys = buysFromSession.get(sessionID);
                 for (Buy thisBuy: theBuys)
                 {
                     System.out.println("\tBought " + thisBuy.getProduct());
                     System.out.println("\t\tBuy: " + thisBuy.getPrice() +
                             "\n\t\tAVP: " + averageViewPrice);
                     System.out.println("\t\tDif: " +
                             (thisBuy.getPrice() - averageViewPrice) + " cents");
                 }
             }
             catch (NullPointerException ex) {
                 // Check if there are no purchases in this session
                 if (buysFromSession.get(sessionID) != null) {
                     // there aren't any views, so price difference is the amount purchased
                     List<Buy> theBuys = buysFromSession.get(sessionID);
                     for (Buy thisBuy: theBuys)
                     {
                         System.err.println("\tBought " + thisBuy.getProduct());
                         System.err.println("\t\tCustomer purchased all viewed items");
                     }
                 }
                 else if (viewsFromSession.get(sessionID) != null) {
                     List<View> theViews = viewsFromSession.get(sessionID);
                     for (View thisView: theViews)
                     {
                         numViews += 1;
                         averageViewPrice += thisView.getPrice();
                     }
                     averageViewPrice = averageViewPrice/numViews;
                     System.err.println("\tNO SALE\n\t\tAVP: " + averageViewPrice);
                 }
                 else System.err.println("\tNO ACTIVITY");
             }
         }
      }
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printCustomerItemViewsForPurchase(
      /* add parameters as needed */
      )
   {
      System.out.println("Number of Views for Purchased Product by Customer");

      /* add printing */
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
  // private static void printStatistics(
      /* add parameters as needed */
     // )
  /// {
     // printSessionPriceDifference( /*add arguments as needed */);
     // printCustomerItemViewsForPurchase( /*add arguments as needed */);

      /* This is commented out as it will not work until you read
         in your data to appropriate data structures, but is included
         to help guide your work - it is an example of printing the
         data once propogated 
         printOutExample(sessionsFromCustomer, viewsFromSession, buysFromSession);
      */
		
  // }

   /* provided as an example of a method that might traverse your
      collections of data once they are written 
      commented out as the classes do not exist yet - write them! */

   public static void printOutExample(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession) 
       throws NullPointerException
   {
      //for each customer, get their sessions
      //for each session compute views
      for(Map.Entry<String, List<String>> entry: 
         sessionsFromCustomer.entrySet()) 
      {
         System.out.println(entry.getKey());
         List<String> sessions = entry.getValue();
         for(String sessionID : sessions)
         {
             try {
                 System.out.println("\tin " + sessionID);
                 List<View> theViews = viewsFromSession.get(sessionID);
                 for (View thisView: theViews)
                 {
                     System.out.println("\t\tviewed " + thisView.getProduct());
                 }
             }
             catch (NullPointerException ex) {
                 System.err.println("\t\tNo views found...");
             }
         }
      }
   }
      //called in populateDataStructures
   private static void processFile(
      final Scanner input,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
           /* add parameters as needed */
      )
   {
      while (input.hasNextLine())
      {
         processLine(input.nextLine(),
                 sessionsFromCustomer,
                 viewsFromSession,
                 buysFromSession
                 /* add arguments as needed */ );
      }
   }

      //called from main - mostly just pass through important data structures	
   public static void populateDataStructures(
      final String filename,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      ) throws FileNotFoundException
   {
      try (Scanner input = new Scanner(new File(filename)))
      {
         processFile(input,
                 sessionsFromCustomer,
                 viewsFromSession,
                 buysFromSession
            /* add arguments as needed */ );
      }
      catch (FileNotFoundException ex) {
         System.err.println("Log file not found.");
         System.exit(1);
      }
   }


   public static String getFilename(String[] args)
   {
      if (args.length < 1)
      {
         System.err.println("Log file not specified.");
         System.exit(1);
      }
      return args[0];
   }
}
      //write this after you have figured out how to store your data
      //make sure that you understand the problem
  /* private static void printCustomerItemViewsForPurchase(
      // add parameters as needed
      )
   {
      System.out.println("Number of Views for Purchased Product by Customer");

      // add printing 
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printStatistics(
      // add parameters as needed
      );
*/
