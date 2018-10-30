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
       List <String> sortedByArtist =  new ArrayList<>();

       ArtistComparator comp = new ArtistComparator();
       for (int i = 0; i < songs.length - 1; i++ ) {
           int thisSong = i;
           for (int j = 0; j < songs.length - 1; j++ ) {
               if (!sortedByArtist.contains(songs[j].getArtist())) 
               {
                   if (comp.compare(songs[thisSong] , songs[j] ) > 0 ) { thisSong = j; }
               }
           }
           if (!sortedByArtist.contains(songs[i].getArtist())) {
               sortedByArtist.add( songs[thisSong].getArtist() );
           }
       }

       for( String Artist : sortedByArtist) {
           System.out.println(Artist);
       }
   }

   @Test
   public void testLambdaTitleComparator()
   {
   }

   @Test
   public void testYearExtractorComparator()
   {
   }

   @Test
   public void testComposedComparator()
   {
   }

   @Test
   public void testThenComparing()
   {
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
}
