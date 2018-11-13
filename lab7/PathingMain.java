import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import processing.core.*;

public class PathingMain extends PApplet
{
   private List<PImage> imgs;
   private int current_image;
   private long next_time;
   private PImage background;
   private PImage obstacle;
   private PImage goal;
   private LinkedList<Point> path;
   private int i = 0;

   private static final int TILE_SIZE = 32;

   private static final int ANIMATION_TIME = 100;

   private GridValues[][] grid;
   private static final int ROWS = 15;
   private static final int COLS = 20;

   private static enum GridValues { BACKGROUND, OBSTACLE, GOAL, SEARCHED };

   private Point wPos;

   private boolean drawPath = false;

	public void settings() {
      size(640,480);
	}
	
	/* runs once to set up world */
   public void setup()
   {
      this.path = new LinkedList<Point>();
      this.wPos = new Point(2, 2);
      this.imgs = new ArrayList<>();
      this.imgs.add(loadImage("images/wyvern1.bmp"));
      this.imgs.add(loadImage("images/wyvern2.bmp"));
      this.imgs.add(loadImage("images/wyvern3.bmp"));

      this.background = loadImage("images/grass.bmp");
      this.obstacle = loadImage("images/vein.bmp");
      this.goal = loadImage("images/water.bmp");

      this.grid = new GridValues[ROWS][COLS];
      initialize_grid(grid);

      this.current_image = 0;
      this.next_time = System.currentTimeMillis() + ANIMATION_TIME;
      noLoop();
   }

	/* set up a 2D grid to represent the world */
   private static void initialize_grid(GridValues[][] grid)
   {
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            grid[row][col] = GridValues.BACKGROUND;
         }
      }

		//set up some obstacles
      for (int row = 2; row < 8; row++)
      {
         grid[row][row + 5] = GridValues.OBSTACLE;
      }

      for (int row = 8; row < 12; row++)
      {
         grid[row][19 - row] = GridValues.OBSTACLE;
      }

      for (int col = 1; col < 8; col++)
      {
         grid[11][col] = GridValues.OBSTACLE;
      }
      grid[13][14] = GridValues.GOAL;
   }

   private void next_image()
   {
      current_image = (current_image + 1) % imgs.size();
   }

	/* runs over and over */
   public void draw()
   {
      System.out.println("Drawing...");
      // A simplified action scheduling handler
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         System.out.println("next image ...");
         next_image();
         next_time = time + ANIMATION_TIME;
      }

      draw_grid();
      draw_path();

      image(imgs.get(current_image), wPos.x * TILE_SIZE, wPos.y * TILE_SIZE);
   }

   private void draw_grid()
   {
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            draw_tile(row, col);
         }
      }
   }
   
   private void draw_path()
   {
      if (drawPath)
      {
          if (this.i < path.size()) {
            fill(255, 0, 0);
            rect(path.get(i).x * TILE_SIZE + TILE_SIZE * 3 / 8,
               path.get(i).y * TILE_SIZE + TILE_SIZE * 3 / 8,
               TILE_SIZE / 4, TILE_SIZE / 4);
            i++;
          }
          else i = 0;
      }
   }

   /*private void draw_path()
   {
      if (drawPath)
      {
         for (Point p : path)
         {
            fill(255, 0, 0);
            rect(p.x * TILE_SIZE + TILE_SIZE * 3 / 8,
               p.y * TILE_SIZE + TILE_SIZE * 3 / 8,
               TILE_SIZE / 4, TILE_SIZE / 4);
         }
      }
   }*/

   private void draw_tile(int row, int col)
   {
      switch (grid[row][col])
      {
         case BACKGROUND:
            image(background, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case OBSTACLE:
            image(obstacle, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case SEARCHED:
            if (this.drawPath == false){
                fill(0, 128);
                rect(col * TILE_SIZE + TILE_SIZE / 4,
                        row * TILE_SIZE + TILE_SIZE / 4,
                        TILE_SIZE / 2, TILE_SIZE / 2);
                System.out.println("SEARCHED");
            }
            break;
         case GOAL:
            image(goal, col * TILE_SIZE, row * TILE_SIZE);
            break;
      }
   }

   public static void main(String args[])
   {
      PApplet.main("PathingMain");
   }

   public void keyPressed()
   {
      if (key == 'o')
      {
          i=0;
         drawPath = false; 
         path.clear();
         depthFirstSearch(wPos);
         redraw();
      }
      else if (key == 'p')
      {
         drawPath = true;
          try {
              Thread.sleep(10);
          } catch (Exception e) {}
         redraw() ;
      }
      else if (key == 'r') // reset
      {
          i = 0;
         drawPath ^= false;
         initialize_grid(grid);
         this.path.clear();
         redraw();
      }    
   }

	/* replace the below with a depth first search 
		this code provided only as an example of moving in
		in one direction for one tile - it mostly is for illustrating
		how you might test the occupancy grid and add nodes to path!
	*/
   private boolean depthFirstSearch(Point node)
   {
      System.out.println("( "+node.x+" , "+node.y+" )");

      if (!withinBounds(node, grid)) { return false ;}
      //if (this.grid[node.y][node.x] == null) { return false ;} // Default Case
      if (this.grid[node.y][node.x] == GridValues.GOAL) { return true ;}
      if (this.grid[node.y][node.x] == GridValues.OBSTACLE) { return false ;}
      if (this.grid[node.y][node.x] == GridValues.SEARCHED) { return false ;}
          
      this.grid[node.y][node.x] = GridValues.SEARCHED ;

      boolean found = ( 
              depthFirstSearch( new Point(node.x +1, node.y)) ||
              depthFirstSearch( new Point(node.x , node.y +1)) ||
              depthFirstSearch( new Point(node.x -1, node.y)) ||
              depthFirstSearch( new Point(node.x, node.y -1))
              ) ;
      if (found) { 
          this.path.offerFirst(node) ;
      }
      return found;
   }
 
   /*private boolean moveOnce(Point pos, GridValues[][] grid, List<Point> path)
   {
      try {
         Thread.sleep(200);
      } catch (Exception e) {}
      redraw();

      Point rightN = new Point(pos.x +1, pos.y );
     
		//test if this is a valid grid cell 
		if (withinBounds(rightN, grid)  &&
         grid[rightN.y][rightN.x] != GridValues.OBSTACLE && 
         grid[rightN.y][rightN.x] != GridValues.SEARCHED)
      {
			//check if my right neighbor is the goal
      	if (grid[rightN.y][rightN.x] == GridValues.GOAL) {
         	path.add(0, rightN);
         	return true;
      	}
			//set this value as searched
      	grid[rightN.y][rightN.x] = GridValues.SEARCHED;
      }

		return false;
   }*/

   private static boolean withinBounds(Point p, GridValues[][] grid)
   {
      return p.y >= 0 && p.y < grid.length &&
         p.x >= 0 && p.x < grid[0].length;
   }
}
