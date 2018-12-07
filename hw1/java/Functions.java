import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

final class Functions
{
   public static final Random rand = new Random();

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_ID = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   private static final int COLOR_MASK = 0xffffff;
   private static final int KEYED_IMAGE_MIN = 5;
   private static final int KEYED_RED_IDX = 2;
   private static final int KEYED_GREEN_IDX = 3;
   private static final int KEYED_BLUE_IDX = 4;

   private static final int PROPERTY_KEY = 0;
   
   private static final String ORE_ID_PREFIX = "ore -- ";
   private static final int ORE_CORRUPT_MIN = 20000;
   private static final int ORE_CORRUPT_MAX = 30000;
   private static final int ORE_REACH = 1;

   private static final String BLOB_KEY = "blob";
   private static final String BLOB_ID_SUFFIX = " -- blob";
   private static final int BLOB_PERIOD_SCALE = 4;
   private static final int BLOB_ANIMATION_MIN = 50;
   private static final int BLOB_ANIMATION_MAX = 150;

   private static final String QUAKE_KEY = "quake";
   private static final String QUAKE_ID = "quake";
   private static final int QUAKE_ACTION_PERIOD = 1100;
   private static final int QUAKE_ANIMATION_PERIOD = 100;
   private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

   private static final String MINER_KEY = "miner";
   private static final int MINER_NUM_PROPERTIES = 7;
   private static final int MINER_ID = 1;
   private static final int MINER_COL = 2;
   private static final int MINER_ROW = 3;
   private static final int MINER_LIMIT = 4;
   private static final int MINER_ACTION_PERIOD = 5;
   private static final int MINER_ANIMATION_PERIOD = 6;

   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   private static final String ORE_KEY = "ore";
   private static final int ORE_NUM_PROPERTIES = 5;
   private static final int ORE_ID = 1;
   private static final int ORE_COL = 2;
   private static final int ORE_ROW = 3;
   private static final int ORE_ACTION_PERIOD = 4;

   private static final String SMITH_KEY = "blacksmith";
   private static final int SMITH_NUM_PROPERTIES = 4;
   private static final int SMITH_ID = 1;
   private static final int SMITH_COL = 2;
   private static final int SMITH_ROW = 3;

   private static final String VEIN_KEY = "vein";
   private static final int VEIN_NUM_PROPERTIES = 5;
   private static final int VEIN_ID = 1;
   private static final int VEIN_COL = 2;
   private static final int VEIN_ROW = 3;
   private static final int VEIN_ACTION_PERIOD = 4;

   private static final String ZOMBIE_KEY = "zombie";
   private static final String ZOMBIE_ID_SUFFIX = " -- zombie";
   private static final int ZOMBIE_PERIOD_SCALE = 8;
   private static final int ZOMBIE_ANIMATION_MIN = 50;
   private static final int ZOMBIE_ANIMATION_MAX = 150;
 
   private static final String BONES_KEY = "bones";
   private static final int BONES_NUM_PROPERTIES = 5;
   private static final int BONES_ID = 1;
   private static final int BONES_COL = 2;
   private static final int BONES_ROW = 3;
   private static final int BONES_ACTION_PERIOD = 4;
   
   private static final String BONES_ID_PREFIX = "bones -- ";
   private static final int BONES_CORRUPT_MIN = 20000;
   private static final int BONES_CORRUPT_MAX = 30000;
   private static final int BONES_REACH = 1;

   private static final String PLAGUE_CLOUD_KEY = "plague";
   private static final String PLAGUE_CLOUD_ID = "plague";
   private static final int PLAGUE_CLOUD_ACTION_PERIOD = 4;
   private static final int PLAGUE_CLOUD_ANIMATION_PERIOD = 5;
   private static final int PLAGUE_CLOUD_ANIMATION_REPEAT_COUNT = 10;
   
   private static final String TOMB_KEY = "tomb";
   private static final int TOMB_NUM_PROPERTIES = 5;
   private static final int TOMB_ID = 1;
   private static final int TOMB_COL = 2;
   private static final int TOMB_ROW = 3;
   private static final int TOMB_ACTION_PERIOD = 4;

   public static PImage getCurrentImage(Object entity)
   {
      if (entity instanceof Background)
      {
         return ((Background)entity).getImages()
            .get(((Background)entity).getImageIndex());
      }
      else if (entity instanceof Entity)
      {
         return ((Entity)entity).getImages().get(((Entity)entity).getImageIndex());
      }
      else
      {
         throw new UnsupportedOperationException(
            String.format("getCurrentImage not supported for %s",
            entity));
      }
   }

   public static void loadImages(Scanner in, ImageStore imageStore,
      PApplet screen)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            processImageLine(imageStore.getImages(), in.nextLine(), screen);
         }
         catch (NumberFormatException e)
         {
            System.out.println(String.format("Image format error on line %d",
               lineNumber));
         }
         lineNumber++;
      }
   }

   public static void processImageLine(Map<String, List<PImage>> images,
      String line, PApplet screen)
   {
      String[] attrs = line.split("\\s");
      if (attrs.length >= 2)
      {
         String key = attrs[0];
         PImage img = screen.loadImage(attrs[1]);
         if (img != null && img.width != -1)
         {
            List<PImage> imgs = getImages(images, key);
            imgs.add(img);

            if (attrs.length >= KEYED_IMAGE_MIN)
            {
               int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
               int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
               int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
               setAlpha(img, screen.color(r, g, b), 0);
            }
         }
      }
   }

   public static List<PImage> getImages(Map<String, List<PImage>> images,
      String key)
   {
      List<PImage> imgs = images.get(key);
      if (imgs == null)
      {
         imgs = new LinkedList<>();
         images.put(key, imgs);
      }
      return imgs;
   }

   /*
     Called with color for which alpha should be set and alpha value.
     setAlpha(img, color(255, 255, 255), 0));
   */
   public static void setAlpha(PImage img, int maskColor, int alpha)
   {
      int alphaValue = alpha << 24;
      int nonAlpha = maskColor & COLOR_MASK;
      img.format = PApplet.ARGB;
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
         {
            img.pixels[i] = alphaValue | nonAlpha;
         }
      }
      img.updatePixels();
   }

   public static void load(Scanner in, WorldModel world, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), world, imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                  lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
               lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
               lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }

   public static boolean processLine(String line, WorldModel world,
      ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
         case BGND_KEY:
            return parseBackground(properties, world, imageStore);
         case MINER_KEY:
            return parseMiner(properties, world, imageStore);
         case OBSTACLE_KEY:
            return parseObstacle(properties, world, imageStore);
         case ORE_KEY:
            return parseOre(properties, world, imageStore);
         case SMITH_KEY:
            return parseSmith(properties, world, imageStore);
         case VEIN_KEY:
            return parseVein(properties, world, imageStore);
         case TOMB_KEY:
            return parseTomb(properties, world, imageStore);
         case BONES_KEY:
            return parseBones(properties, world, imageStore);

         }
      }

      return false;
   }

   public static boolean parseBackground(String [] properties,
      WorldModel world, ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
            Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         world.setBackground(pt,
            new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public static boolean parseMiner(String [] properties, WorldModel world,
      ImageStore imageStore)
   {
      if (properties.length == MINER_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
            Integer.parseInt(properties[MINER_ROW]));
         Entity entity = new Miner_Not_Full(
                 properties[MINER_ID],
                 pt,
                 imageStore.getImageList(MINER_KEY),
                 Integer.parseInt(properties[MINER_LIMIT]),
                 0,
                 Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                 Integer.parseInt(properties[MINER_ANIMATION_PERIOD]));
                 /*
            Integer.parseInt(properties[MINER_LIMIT]),
            pt,
            Integer.parseInt(properties[MINER_ACTION_PERIOD]),
            Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
            imageStore.getImageList(MINER_KEY));
            */
         world.tryAddEntity(entity);
      }

      return properties.length == MINER_NUM_PROPERTIES;
   }

   public static boolean parseObstacle(String [] properties, WorldModel world,
      ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
            Integer.parseInt(properties[OBSTACLE_COL]),
            Integer.parseInt(properties[OBSTACLE_ROW]));
         Entity entity = new Obstacle(properties[OBSTACLE_ID],
                 pt, 
                 imageStore.getImageList(OBSTACLE_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public static boolean parseOre(String [] properties, WorldModel world,
      ImageStore imageStore)
   {
      if (properties.length == ORE_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
            Integer.parseInt(properties[ORE_ROW]));
         Entity entity = new Ore(properties[ORE_ID],
                 pt, 
                 imageStore.getImageList(ORE_KEY),
                 Integer.parseInt(properties[ORE_ACTION_PERIOD]));

         world.tryAddEntity(entity);
      }

      return properties.length == ORE_NUM_PROPERTIES;
   }

   public static boolean parseSmith(String [] properties, WorldModel world,
      ImageStore imageStore)
   {
      if (properties.length == SMITH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
            Integer.parseInt(properties[SMITH_ROW]));
         Entity entity = new Blacksmith(properties[SMITH_ID],
                 pt, 
                 imageStore.getImageList(SMITH_KEY));

         world.tryAddEntity(entity);
      }

      return properties.length == SMITH_NUM_PROPERTIES;
   }

   public static boolean parseVein(String [] properties, WorldModel world,
      ImageStore imageStore)
   {
      if (properties.length == VEIN_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
            Integer.parseInt(properties[VEIN_ROW]));
         Entity entity = new Vein(
                 properties[VEIN_ID],
                 pt,
                 imageStore.getImageList(VEIN_KEY),
                 Integer.parseInt(properties[VEIN_ACTION_PERIOD]));

         world.tryAddEntity(entity);
      }

      return properties.length == VEIN_NUM_PROPERTIES;
   }

   public static boolean parseTomb(String [] properties, WorldModel world,
      ImageStore imageStore)
   {
      if (properties.length == TOMB_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[TOMB_COL]),
            Integer.parseInt(properties[TOMB_ROW]));
         Entity entity = new Tomb(
                 properties[TOMB_ID],
                 pt,
                 imageStore.getImageList(TOMB_KEY),
                 Integer.parseInt(properties[TOMB_ACTION_PERIOD]));

         world.tryAddEntity(entity);
      }

      return properties.length == TOMB_NUM_PROPERTIES;
   }
   
   public static boolean parseBones(String [] properties, WorldModel world,
      ImageStore imageStore)
   {
      if (properties.length == BONES_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BONES_COL]),
            Integer.parseInt(properties[BONES_ROW]));
         Entity entity = new Bones(properties[BONES_ID],
                 pt, 
                 imageStore.getImageList(BONES_KEY),
                 Integer.parseInt(properties[BONES_ACTION_PERIOD]));

         world.tryAddEntity(entity);
      }

      return properties.length == BONES_NUM_PROPERTIES;
   }

   public static Optional<Entity> nearestEntity(List<Entity> entities,
      Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = Point.distanceSquared(nearest.getPosition(), pos);

         for (Entity other : entities)
         {
            int otherDistance = Point.distanceSquared(other.getPosition(), pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   public static int clamp(int value, int low, int high)
   {
      return Math.min(high, Math.max(value, low));
   }

}
