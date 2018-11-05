import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Blacksmith extends Entity
{
   private static final String SMITH_KEY = "blacksmith";
   private static final int SMITH_NUM_PROPERTIES = 4;
   private static final int SMITH_ID = 1;
   private static final int SMITH_COL = 2;
   private static final int SMITH_ROW = 3;

   public Blacksmith( 
           String id, 
           Point position,
           List<PImage> images)
   {
       super( EntityKind.BLACKSMITH, 
               id,
               position,
               images);
   }
}
