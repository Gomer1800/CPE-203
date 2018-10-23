import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NullPointerException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

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
                     System.out.println("\t" + thisBuy.getProduct() + " " +
                             (thisBuy.getPrice() - averageViewPrice));
                 }
             }
             catch (NullPointerException ex) {
                 // Check if there are no purchases in this session
                 if (buysFromSession.get(sessionID) != null) {
                     // there aren't any views, so price difference is the amount purchased
                     List<Buy> theBuys = buysFromSession.get(sessionID);
                     for (Buy thisBuy: theBuys)
                     {
                         System.err.println("\t" + thisBuy.getProduct() +
                                 " ONLY PURCHASE");
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
                     System.err.println("\tNO PURCHASE");
                 }
             }
         }
      }
   }

   public static void printCustomerItemViewsNoPurchase(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession) 
           throws NullPointerException
   {
      System.out.print("\nAverage Number of Views for Customer without a Purchase : ");

      int numViewsNoPurchase = 0 ;
      int numVisitorsNoPurchase = 0 ;
      //for each customer, get their sessions
      //for each session cycle through views and buys
      for(Map.Entry<String, List<String>> entry: 
         sessionsFromCustomer.entrySet()) 
      {
          List<String> sessions = entry.getValue();
          // for each session, cycle through views and buys
          // if a sessionID resulted in no purchases, +1 numVisitorsNoPurchase , +1 numViewsNoPurchase
          for(String sessionID : sessions) {
              boolean purchase = false;
             try {
                 if (buysFromSession.get(sessionID) == null) {
                     // for each session with no purchases, add up number of views 
                     List<View> theViews = viewsFromSession.get(sessionID);
                     for (View thisView: theViews) {
                         numViewsNoPurchase += 1 ;
                     }
                 }
                 // there was a purchase matching this sessionID
                 else purchase = true;
             }
             catch (NullPointerException ex) {}
             if (purchase == false) { numVisitorsNoPurchase += 1; }
          }
      }
      // After cycling through all customerIDs, comput result
      System.out.println(((double)numViewsNoPurchase)/numVisitorsNoPurchase);
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
      )
   {
      while (input.hasNextLine())
      {
         processLine(input.nextLine(),
                 sessionsFromCustomer,
                 viewsFromSession,
                 buysFromSession
                 );
      }
   }

      //called from main - mostly just pass through important data structures	
   public static void populateDataStructures(
      final String [] fileNameArgs,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      ) throws FileNotFoundException
   {
      try (Scanner input = new Scanner(new File(getFileName(fileNameArgs))))
      {
         processFile(input,
                 sessionsFromCustomer,
                 viewsFromSession,
                 buysFromSession
                 );
      }
      catch (FileNotFoundException ex) {
         System.err.println("Log file not found.");
         System.exit(1);
      }
   }

   private static String getFileName(String[] args)
   {
      if (args.length < 1)
      {
         System.err.println("Log file not specified.");
         System.exit(1);
      }
      return args[0];
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   public static void printCustomerItemViewsForPurchase(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession) 
       throws NullPointerException
   {
      System.out.println("\nNumber of Views for Purchased Product by Customer");
      boolean productPurchased = false;
      boolean viewedBeforePurchase = false;

      //for each customer, get their sessions
      //for each session check if purchases were made
      //if a purchase was made, then cycle through views
      for(Map.Entry<String, List<String>> entry: 
         sessionsFromCustomer.entrySet()) 
      {
          productPurchased = false;
          viewedBeforePurchase = false;
          List<String> purchasedProducts = new ArrayList<>(); // for each customer
          List<String> sessions = entry.getValue();
          //for each sessionID, check for purchases
          //if a purchase was made, gather list of purchased items for that customer
          for(String sessionID : sessions)
          {
              // for each sessionID, check it there were purchases made
              // if a purchase was made, gather list of purchased items
              if(buysFromSession.get(sessionID) != null) { 
                  productPurchased = true;
                  System.out.println("triggered");
                  
                  List<Buy> theBuys = buysFromSession.get(sessionID);
                  // cycle through purchases made in sessionID
                  // add each product to list yesPurchase
                  for (Buy thisBuy: theBuys) {
                      System.out.println("list empty? " +purchasedProducts.isEmpty());
                      System.out.println("this buy? " + thisBuy.getProduct());
                      if(purchasedProducts.isEmpty())  {
                          purchasedProducts.add(thisBuy.getProduct()) ; 
                          System.out.println("product added 1");
                      }
                      else { 
                          List<String> purchases = purchasedProducts;
                          for (String thisPurchase : purchases) {
                              if (thisPurchase.compareTo(thisBuy.getProduct()) != 0) {
                                  purchasedProducts.add(thisBuy.getProduct());
                                  System.out.println("product added 2");
                              }
                          }
                      }
                  }
              }
          }
          // for customers who made a purchase
          System.out.println(productPurchased);
          if (productPurchased == true) {
              System.out.println(entry.getKey());
              // cycle through purchased products
              for (String thisProduct : purchasedProducts) {
                  int numViews = 1;
                  System.out.print("\t" + thisProduct);
                  // for each purchased product cycle through sessions
                  for(String sessionID : sessions) {
                      try {
                          List<View> theViews = viewsFromSession.get(sessionID);
                          // check if customer viewed purchased product
                          // if so, set viewedBeforePurchase = true
                          for (View thisView: theViews) {
                              if (thisView.getProduct().compareTo(thisProduct) == 0 ) {
                                  viewedBeforePurchase = true;
                                  // System.out.println("\t\t" + sessionID);
                              }
                          }
                      }
                      catch (NullPointerException ex2) { System.err.println("error"); }
                      if (viewedBeforePurchase == true) { numViews += 1; }
                  }
                  if (productPurchased == true) { System.out.println(" " + numViews);}
              }
          }
      }
   }
}
      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   //private static void printStatistics(
      // add parameters as needed
      //);
