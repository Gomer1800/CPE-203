import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

public abstract class AnimatedEntity extends ActiveEntity
{
   protected final int animationPeriod;
   
   protected AnimatedEntity(
           EntityKind kind, 
           String id, 
           Point position,
           List<PImage> images,
           int actionPeriod,
           int animationPeriod)
   {
       super(
               kind,
               id,
               position,
               images,
               actionPeriod);
      this.animationPeriod = animationPeriod;
   }

   public int getAnimationPeriod()
   {
      switch (super.kind)
      {
      case MINER_FULL:
      case MINER_NOT_FULL:
      case ORE_BLOB:
      case ZOMBIE:
      case PLAGUE_CLOUD:
      case QUAKE:
         return this.animationPeriod;
      default:
         throw new UnsupportedOperationException(
            String.format("getAnimationPeriod not supported for %s",
            this.kind));
      }
   }
   
   public Action createAnimationAction ( int repeatCount ) { return new Animation( this, null, null, repeatCount); }

   public abstract void executeActivity (
           WorldModel world,
           ImageStore imageStore,
           EventScheduler scheduler );
}
