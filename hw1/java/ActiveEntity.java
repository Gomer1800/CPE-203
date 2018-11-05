import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

public abstract class ActiveEntity extends Entity
{
   private final int actionPeriod;
   
   protected ActiveEntity(
           EntityKind kind,
           String id, 
           Point position,
           List<PImage> images, 
           int actionPeriod)
   {
       super( kind,
               id,
               position,
               images);
       this.actionPeriod = actionPeriod;
   }
    
   public int getActionPeriod() { return this.actionPeriod; }
   public Action createActivityAction (WorldModel w, ImageStore i );
   public void executeActivity ( WorldModel w, ImageStore i, EventScheduler e );
   public void scheduleActions ( EventScheduler e, WorldModel w, ImageStore i );
}
