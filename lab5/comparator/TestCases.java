import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
       System.out.println("\nDEFAULT TEST");
       
       //List <String> sortedByArtist =  new ArrayList<>();

       ArtistComparator comp = new ArtistComparator();
       if (comp.compare(songs[0], songs[2]) < 0) {
           System.out.println(songs[0].getArtist() +" "+songs[2].getArtist());
       }
       else if (comp.compare(songs[0], songs[2]) > 0) {
           System.out.println(songs[2].getArtist()+" "+songs[0].getArtist());
       }

       if (comp.compare(songs[4], songs[7]) < 0) {
           System.out.println(songs[4].getArtist() +" "+songs[7].getArtist());
       }
       else if (comp.compare(songs[4], songs[7]) > 0) {
           System.out.println(songs[7].getArtist()+" "+songs[4].getArtist());
       }
 
       if (comp.compare(songs[3], songs[5]) < 0) {
           System.out.println(songs[3].getArtist() +" "+songs[5].getArtist());
       }
       else if (comp.compare(songs[3], songs[5]) > 0) {
           System.out.println(songs[5].getArtist()+" "+songs[3].getArtist());
       }
       /*
        * int thisSong;

       System.out.println("array length = :" + songs.length);
       for (int i = 0; i < songs.length - 1; i++ ) {
           thisSong = 0;
           for (int j = 0; j < songs.length - 1; j++ ) {
               if (sortedByArtist.contains(songs[j].getArtist()) == false)
               {
                   if (comp.compare( songs[thisSong] , songs[j] ) > 0 ) {
                       thisSong = j;
                       System.out.println(thisSong);
                   }
               }
           }
           sortedByArtist.add(songs[thisSong].getArtist());
       }

       for( String Artist : sortedByArtist) {
           System.out.println(Artist);
       }
       */
   }

   @Test
   public void testLambdaTitleComparator()
   {
       System.out.println("\nLAMBDA TEST");
       Comparator <Song> comp2 = (Song s1, Song s2)-> s1.getTitle().compareTo( s2.getTitle());

       if (comp2.compare(songs[2], songs[4]) < 0) {
           System.out.println(songs[2].getTitle() +" "+songs[4].getTitle());
       }
       else if (comp2.compare(songs[2], songs[4]) > 0) {
           System.out.println(songs[4].getTitle()+" "+songs[2].getTitle());
       }

       if (comp2.compare(songs[4], songs[7]) < 0) {
           System.out.println(songs[4].getTitle() +" "+songs[7].getTitle());
       }
       else if (comp2.compare(songs[4], songs[7]) > 0) {
           System.out.println(songs[7].getTitle()+" "+songs[4].getTitle());
       }

   }

   @Test
   public void testYearExtractorComparator()
   {
       System.out.println("\nEXTRACTOR TEST");
       Comparator <Song> compKey = Comparator.comparing((Song s1)-> s1.getYear());
       
       if ( compKey.compare(songs[0], songs[3]) < 0) {
           System.out.println( songs[3].getYear()+ " "+songs[0].getYear());
       }
       else if ( compKey.compare(songs[0], songs[3]) > 0) {
           System.out.println( songs[0].getYear()+ " "+songs[3].getYear());
       }

   }

   @Test
   public void testComposedComparator()
   {
       System.out.println("\n Composed Test");
       Comparator <Song> compA = Comparator.comparing( Song::getArtist);
       Comparator <Song> compB = compA.thenComparing( Song::getYear);

       System.out.println(compB.compare( songs[3], songs[7]));
       if (compB.compare( songs[3], songs[7] ) > 0) {
           System.out.println( songs[7].toString()+ " " +songs[3].toString());
       }
       
       if (compB.compare( songs[4], songs[5] ) > 0) {
           System.out.println( songs[5].toString()+ " " +songs[4].toString());
       }
       else if (compB.compare( songs[4], songs[5] ) < 0) {
           System.out.println( songs[4].toString()+ " " +songs[5].toString());
       }
   }

   @Test
   public void testThenComparing()
   {
       System.out.println("\n then Comparing Test");
       Comparator <Song> compA = Comparator.comparing( Song::getTitle);
       Comparator <Song> compB = compA.thenComparing( Song::getArtist);

       System.out.println(compB.compare( songs[4], songs[5]));
       if (compB.compare( songs[3], songs[5] ) > 0) {
           System.out.println( songs[5].toString()+ " " +songs[3].toString());
       }
       else if (compB.compare( songs[3], songs[5] ) < 0) {
           System.out.println( songs[3].toString()+ " " +songs[5].toString());
       }

       if (compB.compare( songs[4], songs[5] ) > 0) {
           System.out.println( songs[5].toString()+ " " +songs[4].toString());
       }
       else if (compB.compare( songs[4], songs[5] ) < 0) {
           System.out.println( songs[4].toString()+ " " +songs[5].toString());
       }
   }
}
/*
   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      songList.sort(
         // pass comparator here
      );

      assertEquals(songList, expectedList);
   }
   */
