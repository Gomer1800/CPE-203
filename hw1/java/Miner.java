import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

public abstract class Miner extends MobileEntity
{ 
    protected final int resourceLimit ;
    protected int resourceCount ;

   protected Miner(
           EntityKind kind, 
           String id, 
           Point position,
           List<PImage> images,
           int resourceLimit,
           int resourceCount,
           int actionPeriod,
           int animationPeriod)
   {
       super(
               kind,
               id,
               position,
               images,
               actionPeriod,
               animationPeriod);
       this.resourceLimit = resourceLimit ;
       this.resourceCount = resourceCount ;
   }
   public int getResourceLimit() { return this.resourceLimit; }
   public int getResourceCount() { return this.resourceCount; }

   // ABSTRACT METHODS
   public abstract boolean transformMiner(
           WorldModel world,
           EventScheduler scheduler,
           ImageStore imageStore) ;

   public abstract Point newPosition(
           int horiz,
           Point newPos,
           WorldModel world,
           Point destPos) ;

   public abstract void executeActivity (
           WorldModel world,
           ImageStore imageStore,
           EventScheduler scheduler );

   public abstract boolean moveTo (
           WorldModel world,
           Entity target,
           EventScheduler scheduler);
}
