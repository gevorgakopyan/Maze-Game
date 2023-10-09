package sourceFiles;

import java.util.List;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class EnemyFly extends Enemy{
	SpriteAnimation enemyFly_animation;
	public EnemyFly(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr,
			double health, double damage, double speed, Input input, Image enemyBubbleImage, List<Enemy> enem) {
		super(layer, image, x, y, r, dx, dy, dr, health, damage, speed, input, enemyBubbleImage, enem);
		// TODO Auto-generated constructor stub
	}

	public void move(int random) {
   	 
      	random=rand.nextInt(10);
      	if (random!= 1 && random!= 1 &&random!= 2 &&random!= 4 &&random!= 4)
    		enemyFly_animation.pause();
        // vertical direction
    	
        if( random== 1) {
        	if(field.mapCollision(x,y))
        	{		
            dy = -3;
            direction = 12;
            enemyFly_animation.setOffsetY(0);
            enemyFly_animation.play();
            
            y1=super.y;
        	}else{
        		//super.setY(y1+5);
        		super.setY(y1);
        	}
    }else if( random== 2) {
        	if(field.mapCollision(x,y))
        	{
        	   dy = 3;
               direction = 6;
               enemyFly_animation.setOffsetY(0);
               enemyFly_animation.play();
               y1=super.y;
        	}else {
        		//super.setY(y1-5);
        		super.setY(y1);
        	}
        } else {
            dy = 0d;
        }

        // horizontal direction
        if( random== 3) {
        	if(field.mapCollision(x,y))
        	{
            x1=super.x;
            dx = -3;
            direction = 9;
            
            enemyFly_animation.setOffsetY(0);
            enemyFly_animation.play();
        	}else
        	{

        		super.setX(x1);

        		
        	}
            
        } else if(random== 4) {
        	if(field.mapCollision(x,y))
        	{
            dx = 3;
            direction = 3;        
            enemyFly_animation.setOffsetY(0);
            enemyFly_animation.play();
            x1=super.x;
        	}else
        	{

        		super.setX(x1);

        	}
        } else {
            dx = 0d;
        }
   
    

        x += dx;
        y += dy;
         
        
        // ensure the player can't move outside of the screen
        
        super.checkBounds();
    }
	public void setSpriteAnimation() {
        imageView.setViewport(new Rectangle2D(Settings.EfoffsetX, Settings.EfoffsetY, Settings.Efwidth, Settings.Efheight));
        enemyFly_animation = new SpriteAnimation(
        		imageView,
                Duration.millis(900),
                Settings.Efcount, Settings.Efcolumns,
                Settings.EfoffsetX, Settings.EfoffsetY,
                Settings.Efwidth, Settings.Efheight
        );
        enemyFly_animation.setCycleCount(Animation.INDEFINITE);
        //move();
    }
}
