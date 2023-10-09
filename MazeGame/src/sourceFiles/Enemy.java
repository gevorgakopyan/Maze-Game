package sourceFiles;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Random;
import java.util.function.Consumer;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;




public class Enemy extends SpriteBase{
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
	Image enemyBubbleImage;
    ImageView enemyBubbleView;
    Image enemyAttackImage;
    protected boolean canShoot = true;
    MySounds mySounds;
    SpriteAnimation enemy_animation;
    List<Item> ebullet = new ArrayList<>();
    List<Enemy> enemy = new ArrayList<>();
    
    public Enemy(Pane layer, Image image, double x, double y, double r, double dx,
    		double dy, double dr, double health, double damage, double speed,
    		Input input, Image enemyBubbleImage,List<Enemy> enem) {

        super(layer, image, x, y, r, dx, dy, dr, health, damage);
       // this.speed = speed;
        //this.input = input;
        y1=y;
        x1=x;
        this.enemyBubbleImage = enemyBubbleImage;
        setSpriteAnimation();
        //attack();
       // move();
        enemy=enem;
        
        mySounds = new MySounds();
        init();
        
        //move();
    }
    protected void init() {

        // calculate movement bounds of the player
    	 enemyMinX = 90;
         enemyMaxX = Settings.SCENE_WIDTH - Settings.Pwidth-90;
         enemyMinY = 310;
         enemyMaxY = Settings.SCENE_HEIGHT - Settings.Pheight-90;

    }
    @Override
    public void checkRemovability(){
    	
    }
    @Override
    public void move()
    {
    	
    }
    public void move(int random) {
    	 
      	random=rand.nextInt(10);
      	if (random!= 1 && random!= 1 &&random!= 2 &&random!= 4 &&random!= 4)
    		enemy_animation.pause();
        // vertical direction
    	
        if( random== 1) {
        	if(field.mapCollision(x,y))
        	{		
            dy = -3;
            direction = 12;
            enemy_animation.setOffsetY(0);
            enemy_animation.play();
            
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
               enemy_animation.setOffsetY(Settings.Eheight);
               enemy_animation.play();
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
            
            enemy_animation.setOffsetY(Settings.Eheight * 2);
            enemy_animation.play();
        	}else
        	{

        		super.setX(x1);

        		
        	}
            
        } else if(random== 4) {
        	if(field.mapCollision(x,y))
        	{
            dx = 3;
            direction = 3;        
            enemy_animation.setOffsetY(Settings.Eheight * 3);
            enemy_animation.play();
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

        public void attack(List<Item> ebullet) {
        	if(rand.nextInt(10)==1)
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
    
    
        public void removeSprites() {
            Iterator<? extends SpriteBase> iter = enemy.iterator();
            while( iter.hasNext()) {
                SpriteBase sprite = iter.next();

                if( sprite.isRemovable()) {

                    // remove from layer
                    sprite.removeFromLayer();

                    // remove from list
                    iter.remove();
                    
                }
            }
        }
    public void setSpriteAnimation() {
        imageView.setViewport(new Rectangle2D(Settings.EoffsetX, Settings.EoffsetY, Settings.Ewidth, Settings.Eheight));
        enemy_animation = new SpriteAnimation(
        		imageView,
                Duration.millis(900),
                Settings.Ecount, Settings.Ecolumns,
                Settings.EoffsetX, Settings.EoffsetY,
                Settings.Ewidth, Settings.Eheight
        );
        enemy_animation.setCycleCount(Animation.INDEFINITE);
        //move();
    }

    protected void checkBounds() {
    	
        // vertical
        if( Double.compare( y, enemyMinY) < 0) {
            y = enemyMinY;
        } else if( Double.compare(y, enemyMaxY) > 0) {
            y = enemyMaxY;
        }

        // horizontal
        if( Double.compare( x, enemyMinX) < 0) {
            x = enemyMinX;
        } else if( Double.compare(x, enemyMaxX) > 0) {
            x = enemyMaxX;
        }

    }

   
}
