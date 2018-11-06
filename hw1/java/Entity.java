import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;

public abstract class Entity 
{
    protected final EntityKind kind;
    protected final String id;
    protected Point position;
    protected final List<PImage> images;
    protected int imageIndex;
    
    protected Entity(
            EntityKind kind,
            String id,
            Point position,
            List<PImage> images )
    {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

   // accessors

    public EntityKind getKind() { return this.kind; }
    public String getID()  { return this.id; }
    public Point getPosition()  { return this.position; }
    public List<PImage> getImages()  { return this.images; }
    public int getImageIndex()  { return this.imageIndex; }
    public void setPosition(Point p)  { this.position = p; }
    public void nextImage() { this.imageIndex = (this.imageIndex + 1) % this.images.size(); }
}
