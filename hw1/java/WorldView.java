import processing.core.PApplet;
import processing.core.PImage;
import java.util.Optional;

final class WorldView
{
   private final PApplet screen;
   private final WorldModel world;
   private final int tileWidth;
   private final int tileHeight;
   private final Viewport viewport;

   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
      int tileWidth, int tileHeight)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.viewport = new Viewport(numRows, numCols);
   }
   // Methods
   //
   public void shiftView(int colDelta, int rowDelta)
   {
      int newCol = Functions.clamp(this.viewport.getCol() + colDelta, 0,
         this.world.numCols - this.viewport.getNumCols());
      int newRow = Functions.clamp(this.viewport.getRow() + rowDelta, 0,
         this.world.numRows - this.viewport.getNumRows());

      this.viewport.shift(newCol, newRow);
   }

   private void drawBackground()
   {
      for (int row = 0; row < this.viewport.getNumRows(); row++)
      {
         for (int col = 0; col < this.viewport.getNumCols(); col++)
         {
            Point worldPoint = this.viewport.viewportToWorld(col, row);
            Optional<PImage> image = Functions.getBackgroundImage(this.world,
               worldPoint);
            if (image.isPresent())
            {
               this.screen.image(image.get(), col * this.tileWidth,
                  row * this.tileHeight);
            }
         }
      }
   }

   private void drawEntities()
   {
      for (Entity entity : this.world.entities)
      {
         Point pos = entity.position;

         if (this.viewport.contains(pos))
         {
            Point viewPoint = this.viewport.worldToViewport(pos.x, pos.y);
            this.screen.image(Functions.getCurrentImage(entity),
               viewPoint.x * this.tileWidth, viewPoint.y * this.tileHeight);
         }
      }
   }

   public void drawViewport()
   {
      this.drawBackground();
      this.drawEntities();
   }
}
