import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

final class Obstacle extends Entity
{
   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;
   
   public Obstacle(String id, Point position,
      List<PImage> images)
   {
       super( EntityKind.OBSTACLE,
               id,
               position,
               images);
   }
}
