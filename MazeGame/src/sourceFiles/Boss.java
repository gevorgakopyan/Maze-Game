package sourceFiles;

import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Boss extends Enemy{
	SpriteAnimation boss_animation;
	CollisionDetection field= new CollisionDetection();
	Random rand = new Random();
	double random;
	Input input;
	int direction = 6;
	double speed= Settings.ENEMY_SPEED;
	double y1,x1;
	double enemyMinX;
    double enemyMaxX;
    double enemyMinY;
    double enemyMaxY;
	public Boss(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health,
			double damage, double speed, Input input, Image bossBubbleImage, List<Enemy> enem) {
		super(layer, image, x, y, r, dx, dy, dr, health, damage, speed, input, bossBubbleImage, enem);
		// TODO Auto-generated constructor stub
		//setSpriteAnimation();
        
        
        }
	public void move(int random) {
   	 
      	random=rand.nextInt(10);
      	if (random!= 1 && random!= 1 &&random!= 2 &&random!= 4 &&random!= 4)
    		boss_animation.pause();
        // vertical direction
    	
        if( random== 1) {
        	if(field.mapCollision(x,y))
        	{		
            dy = -3;
            direction = 12;
            boss_animation.setOffsetY(0);
            boss_animation.play();
            
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
               boss_animation.setOffsetY(Settings.Bheight);
               boss_animation.play();
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
            
            boss_animation.setOffsetY(Settings.Bheight * 2);
            boss_animation.play();
        	}else
        	{

        		super.setX(x1);

        		
        	}
            
        } else if(random== 4) {
        	if(field.mapCollision(x,y))
        	{
            dx = 3;
            direction = 3;        
            boss_animation.setOffsetY(Settings.Bheight * 3);
            boss_animation.play();
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
        
        checkBounds();
    }

	public void setSpriteAnimation() {
        imageView.setViewport(new Rectangle2D(Settings.BoffsetX, Settings.BoffsetY, Settings.Bwidth, Settings.Bheight));
        boss_animation = new SpriteAnimation(
        		imageView,
                Duration.millis(900),
                Settings.Bcount, Settings.Bcolumns,
                Settings.BoffsetX, Settings.BoffsetY,
                Settings.Bwidth, Settings.Bheight
        );
        boss_animation.setCycleCount(Animation.INDEFINITE);
        //move();
    }
	public void attack(List<Item> ebullet) {
    	if(rand.nextInt(2)==1)
    	{
    		double x = super.getX();
    		double y = super.getY();
    		int offsetY = 0;
    		int offsetY2 = 0;
    		double dx = 0;
    		double dy = 0;
    		
    		if (direction == 12) {
    			offsetY = 0;
        		y -= 40;
        		dy = -Settings.ENEMY_BULLET_SPEED;
    		}
    		else if (direction == 6) {
    			offsetY = 0;
        		y += 50;
        		dy = Settings.ENEMY_BULLET_SPEED;
    		}
    		else if (direction == 9) {
    			offsetY = 0;
        		x -= 40;
        		y += 20;
        		dx = -Settings.ENEMY_BULLET_SPEED;
    		}
    		else if (direction == 3) {
    			offsetY = 0;
        		x += 50;
        		y += 20;
        		dx = Settings.ENEMY_BULLET_SPEED;
    		}
			Item ebullett = new Item(layer, enemyBubbleImage, x, y, 0, dx, dy, 0, 1, Settings.PLAYER_SECONDARY_WEAPON_DAMAGE, ebullet);
    		ebullett.setSpriteAnimation(offsetY, 24, 29, 3, 3);
    		ebullett.bullet_animation.setOffsetY(offsetY);
    		canShoot = false; 
		//time2 = 20;
    	}
    }
	
	
}
