// Example game.
// Modify it to:
// 1. Use Abstract Class(s)-used
// 2. Use Interfaces-used
// 3. more inheritance.-created new classes
// 4. background music- done
// 5. add enemies-done
// 6. add score-done
// 7. collision detection between player and walls-done
//    between player bullets and enemies-done
//    between enemy bullets and player-done
// 8. Use the MVC paradigm (Model, View, Controller)-done
// 9. health bar, when get hit by enemy bullet it decreases.-done
// 10. treasure that increases health-done
// 11. multiple levels (go out through a door in current level to get to next level)-done
//     or kill all enemies in current level, or reach a certain score etc.
// 12. game over screen.-done
// 13. victory screen.-done
// 14. whatever else you'd like.-kind of done
// added to the list
// 15. add boss-done
// 16  add treasure when win the boss-done
// 17. add collision between 2 bullets- done
// 18. collision detection with background for bullet-done
// 19. check bounds player bullet-done
//     check bounds player bullet-done

/**
 * http://stackoverflow.com/questions/29057870/in-javafx-how-do-i-move-a-sprite-across-the-screen
 * http://silveiraneto.net/2008/12/08/javafx-how-to-create-a-rpg-like-game/
 * https://opengameart.org/content/generic-8-bit-jrpg-soundtrack- place for music
 */

package sourceFiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application {

    Random rnd = new Random();
    CollisionDetection field= new CollisionDetection();
    Pane playfieldLayer;
    Pane scoreLayer; 
    Pane backgrounds;
    BorderPane al;
    Image playerImage = new Image("images/Player_560x372.png");
    //Image playerImage = new Image("images/PlayerSprite.png");
    Image freeHealth = new Image("images/Heartdrop_32x32.png");
    Image playerAttackImage = new Image("images/PlayerAttack_70x373.png");
	Image playerBubbleImage = new Image("images/Player_Bubble_52x104.png");
	Image enemyBullet = new Image("images/Enemy_Bullet_72x29.png");
	Image bossImage=new Image("images/Boss_420x768.png");
    Image enemyImage = new Image("images/Enemy1_160x368.png");
    Image enemyFlyImage= new Image("images/Enemy2Fly_240x92.png");
    Image enemyFly2Image= new Image("images/Enemy2Fly2_240x92.png");
    Image victory = new Image("images/Victory_Screen_1900x998.png");
    Image gameOver = new Image ("images/Gameoverscreen_1900x998.png");
    Image maze01  = new Image("images/Room1_1900x998.png");
    Image maze02 = new Image("images/Room2_1900x998.png");
    Image maze03 = new Image("images/Room3_1900x998.png");
    Image maze04 = new Image("images/Room4_1900x998.png");
    Image playerProfile= new Image("images/Healthbar_profile_only_86x72.png");
    Image fhealth= new Image("health/5.png");
    Image scoreBorder = new Image("images/score.png");
    Image finalPiec = new Image("images/finalPiece_80x85.png");
    Image background = new Image("images/backgroung.png");
    Player player;
    Heart heart;
    FinalPiece finalPiece;
    EnemyFly enemyFly;
    EnemyFly enemyFly2;
    Enemy enemy;
    Boss boss;
    Input input;
    Random rand = new Random();
    Label score;
    int random= rand.nextInt(10);
	double bossCount=0;
    List<Item> items = new ArrayList<>();
    List<Item> weapons = new ArrayList<>();
    List<Rectangle> obstacles = new ArrayList<>();
    List<Enemy> enem= new ArrayList<>();
    List<Item> ebullet = new ArrayList<>();
    List<Heart> hearts = new ArrayList<>();
    List<FinalPiece> drink = new ArrayList<>();
    

    int scores = 0;
    int i;
    Scene scene;
    int randAttack;
    int currentScene;
    int enemyCount=0;
    int maxEnemyCount=6;
    double ec;
	double randX,randY;
	int direction;
    Group root;
    ImageView mazeView;
    ImageView healthView;
    ImageView profileView;
    ImageView gifts;
    ImageView finalDrink;
    ImageView border;
    ImageView backgroundMain;
    Settings s =new Settings();
    AnimationTimer gameLoop;
    MySounds mysounds = new MySounds();
    @Override
    public void start(Stage primaryStage) {    	
        playfieldLayer = new Pane();
        scoreLayer = new Pane();
        backgrounds = new Pane();
        
        root = new Group();
        score=new Label();
        border = new ImageView(scoreBorder);
        profileView = new ImageView(playerProfile);
        healthView = new ImageView(fhealth);
        mazeView = new ImageView(maze01);
        mazeView = new ImageView(maze02);
        mazeView = new ImageView(maze03);
        mazeView = new ImageView(maze04);
        mazeView = new ImageView(victory);
        mazeView = new ImageView(gameOver);
        gifts= new ImageView(freeHealth);
        finalDrink = new ImageView(finalPiec);
        backgroundMain = new ImageView(background);
        
        playfieldLayer.getChildren().addAll(mazeView);
        scoreLayer.getChildren().addAll(profileView,healthView,score,border);
        
        root.getChildren().add( playfieldLayer);
        root.getChildren().add( scoreLayer);
        
        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        moveToScene01(128.0,543.0 ,primaryStage);
		 mysounds.playClip(1);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        input = new Input(scene);

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over
        location();
        
        // create player
        player = new Player(playfieldLayer, playerImage, 128, 543, 0, 0, 0, 0, Settings.PLAYER_HEALTH, Settings.PLAYER_DAMAGE, Settings.PLAYER_SPEED, input,playerBubbleImage,playerAttackImage);
        player.setSpriteAnimation();
        createScoreLayer();
        heart = new Heart(playfieldLayer,null,0,0,0,0,0,0,0,0);//create empty objects, for future because it caused errors
        finalPiece = new FinalPiece(playfieldLayer,null,0,0,0,0,0,0,0,0);
        
        gameLoop = new AnimationTimer() {
        	
            private long lastUpdate = 0 ;
            @Override
            public void handle(long now) {
            	changeScene(primaryStage);
                // player input
            	player.processInput();
                // movement
                player.move();
                // movement
                spawnEnemies(random);
                enem.forEach(sprite->sprite.move(direction));
                weapons.forEach(sprite -> sprite.move());
                ebullet.forEach(sprite -> sprite.move());
                
                player.attack(weapons);
                player.reloadSword();
                
                // check collisions
                checkCollisions();
                handleCollision(primaryStage);
                randomAttack(); 
                
                checkDamage();
                
                // update sprites in scene
                enem.forEach(sprite->sprite.updateUI());
                player.updateUI();
                weapons.forEach(sprite -> sprite.updateUI());
                ebullet.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                // remove removables from list, layer, etc
                removeSprites( weapons);
                removeSprites( ebullet);

                // update score, health, etc
                updateScore();
                
            }

        };
        gameLoop.start();

    }
   
    protected void handleCollision(Stage primaryStage) {
       
           enem.forEach(enemy->enemy.collidesWith(player));
           handleBulletIntersection();
           handleEbulletToPlayer();
           handlePbulletToEnemy(primaryStage);
           handleTreasureIntersection(primaryStage);
           
    }
    private void handleTreasureIntersection(Stage primaryStage)
    {
    	drink.forEach(sprite-> player.collidesWithT(finalPiece));
    	if(finalPiece.isRemovable())
    	{
    		mysounds.stopClip(8);
        	mysounds.playClip(5);
        	victory(primaryStage);
    		removeSprites(hearts);
    	}
    	if(hearts.size()>1)
		   {
			   hearts.get(0).setRemovable(true);
			   removeSprites(hearts);
		   }
        hearts.forEach(sprite->player.collidesWithT(heart));
        if(heart.isRemovable())
		   {
			   //System.out.println(Settings.PLAYER_HEALTH);
			   if(Settings.PLAYER_HEALTH>=10)
				 Settings.PLAYER_HEALTH=10;
			   else
			   Settings.PLAYER_HEALTH++;
			   scores++;
			   removeSprites(hearts);
			   heart.setRemovable(false);
		   }
    }
    private void handleBulletIntersection()
    {
    	
    	for(i=0; i< ebullet.size();i++)
        {
     	   weapons.forEach(sprite -> sprite.collidesWithBullet(ebullet.get(i)));
     	   if(ebullet.get(i).isRemovable())
     	   {
        		   removeSprites(ebullet);
        		   removeSprites(weapons);
     	   } 
        }
    }
    private void handleEbulletToPlayer()
    {
    	  checkBounds(ebullet);
		  for(i=0;i<ebullet.size();i++) { 
			  if(ebullet.get(i).isRemovable()==true)
			  {
				  ebullet.get(i).setRemovable(true);
				  ebullet.get(i).removeFromLayer();
				  ebullet.remove(ebullet.get(i));
				  removeSprites(ebullet);
			  }else if(field.mapCollision(ebullet.get(i).getX(), ebullet.get(i).getY())==false) {
			  
				  ebullet.get(i).setRemovable(true);
				  ebullet.get(i).removeFromLayer();
				  ebullet.remove(ebullet.get(i));
				  
				  removeSprites(ebullet);
			  	}
		  
		  }    
    	ebullet.forEach(sprite->sprite.collidesWithBullet(player));
    	if(player.isRemovable())
		   {
			   //player.setHealth(Settings.PLAYER_HEALTH-Settings.ENEMY_DAMAGE);
	    	  mysounds.playClip(10);
			  Settings.PLAYER_HEALTH-=Settings.ENEMY_DAMAGE;
			  //System.out.println(Settings.PLAYER_HEALTH);
			  player.setRemovable(false);
		   } 
    	
    }
    
    private void handlePbulletToEnemy(Stage primaryStage)
    {
    	  
		  checkBounds(weapons);
		  for(i=0;i<weapons.size();i++) 
		  {
			
			  if(weapons.get(i).isRemovable()==true)
			  {
				  weapons.get(i).setRemovable(true);
				  weapons.get(i).removeFromLayer();
				  weapons.remove(weapons.get(i));
				  removeSprites(weapons);
			  }else if(field.mapCollision(weapons.get(i).getX(), weapons.get(i).getY())==false) {
				  weapons.get(i).setRemovable(true);
				  weapons.get(i).removeFromLayer();
				  weapons.remove(weapons.get(i));
				  
				  removeSprites(weapons);
				  
			  	}
		  
		  }
		 
    	
    	for(i=0; i< enem.size();i++)
        {
    	
     	   weapons.forEach(sprite -> sprite.collidesWithBullet(enem.get(i)));
     	   if(enem.get(i).isRemovable())
     	   {
     		  mysounds.playClip(10);
     		  enem.get(i).setHealth(enem.get(i).getHealth()-Settings.PLAYER_DAMAGE);
     		  enem.get(i).setRemovable(false);
	        	  if(enem.get(i).getHealth()==0)
	        	  {
	        		  
	        		   double healthX=enem.get(i).getX();
	        		   double healthY=enem.get(i).getY();
	        		   
	        		   enem.get(i).setRemovable(true);
	        		   removeSprites(enem);
	        		   if(rand.nextInt(7)==1)
	        		   {
	        			   heart = new Heart(playfieldLayer,freeHealth,healthX,healthY,0,0,0,0,0,0);
	        			   hearts.add(heart);
	        		   }
	        		   scores+=5;
	        		   if(boss==null)
	        		   boss = new Boss(playfieldLayer, null, 0, 0, 0, 0, 0, 0, -1,0,0, input,enemyBullet,enem);
	        		   if(boss.health==0&& Settings.map==4)
					   {
						 scores+=45;
						 boss.setHealth(-1);
						 finalPiece = new FinalPiece(playfieldLayer,finalPiec,healthX,healthY,0,0,0,0,0,0);
						 drink.add(finalPiece);
					   }
					   removeSprites(enem);
	        		   
	        	
	        	  }
     	   } 
        }
    }
    private void createScoreLayer() {
    	
    }
    
    private void addBoss(double x, double y, Image bossImage) {
    	boss = new Boss(playfieldLayer, bossImage, x, y, 0, 0, 0, 0, Settings.BOSS_HEALTH, Settings.BOSS_DAMAGE, Settings.BOSS_SPEED, input,enemyBullet,enem);
       enem.add(boss);
    }
    private void addEnemy(double x, double y, Image enemyImage) {
    	enemy = new Enemy(playfieldLayer, enemyImage, x, y, 0, 0, 0, 0, Settings.ENEMY_HEALTH, Settings.ENEMY_DAMAGE, Settings.ENEMY_SPEED, input,enemyBullet,enem);
    	enemyCount++;
        enem.add(enemy);
    }
    
    private void addEnemyFly(double x, double y, Image enemyImage) {
    	enemyFly = new EnemyFly(playfieldLayer, enemyImage, x, y, 0, 0, 0, 0, Settings.ENEMY_HEALTH, Settings.ENEMY_DAMAGE, Settings.ENEMY_SPEED, input,enemyBullet,enem);
    	enemyCount++;
        enem.add(enemyFly);
    }
    private void addEnemyFly2(double x, double y, Image enemyImage) {
    	enemyFly2 = new EnemyFly(playfieldLayer, enemyImage, x, y, 0, 0, 0, 0, Settings.ENEMY_HEALTH, Settings.ENEMY_DAMAGE, Settings.ENEMY_SPEED, input,enemyBullet,enem);
    	enemyCount++;
        enem.add(enemyFly2);
    }
    private void location()
    {
    	do
		{
			randX=rand.nextInt(1400)+300;
			randY =rand.nextInt(500)+300;
		}while(field.mapCollision(randX, randY)==false);
    }
    private void spawnEnemies(int random) {

    		double chooseEnemy=rand.nextInt(4);
    		if(Settings.map==1)
    			maxEnemyCount=6;
    		if(Settings.map==2)
    			maxEnemyCount=7;
    		if(Settings.map==3)
    			maxEnemyCount=8;
    		if(Settings.map==4)
        		maxEnemyCount=9;
    		if(enemyCount<maxEnemyCount)
    		{
    			if(chooseEnemy==1)
    			{
    				location();
    				addEnemy(randX, randY, enemyImage);
    			}
    			if(chooseEnemy==2)
    			{
					location();
					addEnemyFly(randX, randY, enemyFlyImage);
    			}
    			if(chooseEnemy==3)
    			{
					location();
					addEnemyFly2(randX, randY, enemyFly2Image);
    			}
				
    		}
		
		
		  if(Settings.map==4 && bossCount<1)
		  { 
		  do
		  {
			  randX=rand.nextInt(1000)+300;
			  randY=rand.nextInt(500)+300;
		  }while(field.mapCollision(randX, randY)==false&& enemy.x==randX &&enemy.y==randY);
		  addBoss(randX, randY, bossImage);
		  bossCount++;
		  
		  }   
    }
    private void removeSprites(  List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
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
    
    private void checkCollisions() {
    	
    }
    
    private void checkDamage() {
    }
    private void randomAttack()
    {
    	if(Settings.map==1)
        	randAttack=rnd.nextInt(15);
        if(Settings.map==2)
        	randAttack=rnd.nextInt(13);
        if(Settings.map==3)
        	randAttack=rnd.nextInt(11);
        if(Settings.map==4)
        	randAttack=rnd.nextInt(9);
		if(randAttack ==1)
		{
	
		  enem.forEach(enemy->enemy.attack(ebullet));
		}	
    }
    // go to a different level by exiting/entering through a door on the current level
    private void changeScene(Stage primaryStage) {
    	checkHealth();
    	score.setText("SCORE : "+scores);
    	if(Settings.map==1&& player.x>1774&&player.y>510&&player.y<690)
        {
    		mysounds.stopClip(1);
    		mysounds.playClip(6);
    		if(enem.size()==0)
    			scores+=10;
        	moveToScene02(128.0,543.0 ,primaryStage);
        	enemyCount=0;	
        }
    	if(Settings.map==2 && player.x > 1774 && player.y > 510&&player.y<690)
        {
    		mysounds.stopClip(6);
    		mysounds.playClip(7);
    		if(enem.size()==0)
    			scores+=10;
        	moveToScene03(128.0,543.0 ,primaryStage);
        	enemyCount=0;
        }
    	if(Settings.map==3&& player.x>1774&&player.y>510&&player.y<690)
        {
    		mysounds.stopClip(7);
    		mysounds.playClip(8);
    		if(enem.size()==0)
    			scores+=10;
        	moveToScene04(128.0,543.0 ,primaryStage);
        	enemyCount=0;
        }if(Settings.PLAYER_HEALTH==0||Settings.PLAYER_HEALTH<0)
        {
        	if(enem.size()==0)
    			scores+=10;
        	gameOver(primaryStage);
        	mysounds.playClip(9);
        }
    }
    private void checkHealth()
    {
    	if(Settings.PLAYER_HEALTH==10||Settings.PLAYER_HEALTH>10)
            fhealth= new Image("health/5.png");
    	if(Settings.PLAYER_HEALTH<10&&Settings.PLAYER_HEALTH>=9)
            fhealth= new Image("health/4.5.png");
        if(Settings.PLAYER_HEALTH<9&&Settings.PLAYER_HEALTH>=8)
            fhealth= new Image("health/4.png");
        if(Settings.PLAYER_HEALTH<8&&Settings.PLAYER_HEALTH>=7)
            fhealth= new Image("health/3.5.png");
        if(Settings.PLAYER_HEALTH<7&&Settings.PLAYER_HEALTH>=6)
            fhealth= new Image("health/3.png");
        if(Settings.PLAYER_HEALTH<6&&Settings.PLAYER_HEALTH>=5)
            fhealth= new Image("health/2.5.png");
        if(Settings.PLAYER_HEALTH<5&&Settings.PLAYER_HEALTH>=4)
            fhealth= new Image("health/2.png");
        if(Settings.PLAYER_HEALTH<4&&Settings.PLAYER_HEALTH>=3)
            fhealth= new Image("health/1.5.png");
        if(Settings.PLAYER_HEALTH<3&&Settings.PLAYER_HEALTH>=2)
            fhealth= new Image("health/1.png");
        if(Settings.PLAYER_HEALTH<2&&Settings.PLAYER_HEALTH>=1)
            fhealth= new Image("health/0.5.png");
    	 healthView.setImage(fhealth);
    }
    
    private void moveToScene01(double x, double y, Stage primaryStage) {
    	Settings.map=1;
        playfieldLayer = new Pane(); 
        root = new Group();
        scoreLayer = new Pane(); 
        scene = new Scene(root,Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		root.getChildren().addAll(playfieldLayer,scoreLayer);
        mazeView = new ImageView(maze01);   	           	        
        playfieldLayer.getChildren().add(mazeView);   	               
        primaryStage.setScene(scene);
        createScoreLayer();
        scoreLayer.getChildren().addAll(backgroundMain,profileView,healthView,border,score);
        border.setLayoutX(795);
        border.setLayoutY(45);
        profileView.setY(100);
        healthView.setY(100);
        healthView.setX(96);
        score.setTextFill(Color.web("#FFFFFF"));
        score.setFont(Font.font("Cambria", 40));
        score.setLayoutX(835);
        score.setLayoutY(100);
        
        input=new Input(scene);
        input.addListeners();
        hearts.clear();
        enem.clear();
        items.clear();
        //addEnemy(701, 723, enemyImage);
        
    }
    
    private void moveToScene02(double x, double y, Stage primaryStage) {
        	setCurrentScene(2);
        	Settings.map=2;
        	root = new Group();
            playfieldLayer = new Pane();   	        
            scoreLayer = new Pane();  
            
    		root.getChildren().addAll(playfieldLayer,scoreLayer);
            mazeView = new ImageView(maze02);   	           	        
            playfieldLayer.getChildren().add(mazeView);   	               
            scene = new Scene(root,Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
            primaryStage.setScene(scene);
            createScoreLayer();
            player.setX(x);
            player.setY(y);
            player.setLayer(playfieldLayer);
            player.addToLayer();
            input.setScene(scene);
            input.addListeners();
            scoreLayer.getChildren().addAll(backgroundMain,profileView,healthView,border,score);
            border.setLayoutX(795);
            border.setLayoutY(45);
            profileView.setY(100);
            healthView.setY(100);
            healthView.setX(96);
            score.setTextFill(Color.web("#FFFFFF"));
            score.setFont(Font.font("Cambria", 40));
            score.setLayoutX(835);
            score.setLayoutY(115);
            enem.clear();
            items.clear();
            hearts.clear();
    }
    
    private void moveToScene03(double x, double y, Stage primaryStage) {
    	setCurrentScene(3);
    	Settings.map=3;
    	root = new Group();
        playfieldLayer = new Pane();   	        
        scoreLayer = new Pane();  
		root.getChildren().addAll(playfieldLayer,scoreLayer);
        mazeView = new ImageView(maze03);   	           	        
        playfieldLayer.getChildren().add(mazeView);   	               
        scene = new Scene(root,Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        primaryStage.setScene(scene);
        createScoreLayer();
        scoreLayer.getChildren().addAll(backgroundMain,profileView,healthView,border,score);
        border.setLayoutX(795);
        border.setLayoutY(45);
        profileView.setY(100);
        healthView.setY(100);
        healthView.setX(96);
        score.setTextFill(Color.web("#FFFFFF"));
        score.setFont(Font.font("Cambria", 40));
        score.setLayoutX(835);
        score.setLayoutY(115);
        healthView.setX(96);
        player.setX(x);
        player.setY(y);
        player.setLayer(playfieldLayer);
        player.addToLayer();
        input.setScene(scene);
        input.addListeners();
        enem.clear();
        items.clear();
        hearts.clear();
    }
    
    private void moveToScene04(double x, double y, Stage primaryStage) {
    	setCurrentScene(4);
    	Settings.map=4;
    	root = new Group();
        playfieldLayer = new Pane();   	        
        scoreLayer = new Pane();  
		root.getChildren().addAll(playfieldLayer,scoreLayer);
        mazeView = new ImageView(maze04);   	           	        
        playfieldLayer.getChildren().add(mazeView);   	               
        scene = new Scene(root,Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        primaryStage.setScene(scene);
        createScoreLayer();
        scoreLayer.getChildren().addAll(backgroundMain,profileView,healthView,border,score);
        border.setLayoutX(795);
        border.setLayoutY(45);
        profileView.setY(100);
        healthView.setY(100);
        healthView.setX(96);
        score.setTextFill(Color.web("#FFFFFF"));
        score.setFont(Font.font("Cambria", 40));
        score.setLayoutX(835);
        score.setLayoutY(115);
        player.setX(x);
        player.setY(y);
        player.setLayer(playfieldLayer);
        player.addToLayer();
        input.setScene(scene);
        input.addListeners();
        enem.clear();
        items.clear();
        hearts.clear();
    }
    private void victory(Stage primaryStage) {
    	setCurrentScene(5);
    	Settings.map=5;
    	root = new Group();
        playfieldLayer = new Pane();   	        
        scoreLayer = new Pane();  
		root.getChildren().addAll(playfieldLayer,scoreLayer);
		scoreLayer.getChildren().addAll(score);
		score.setTextFill(Color.web("#FFFFFF"));
        score.setFont(Font.font("Cambria", 40));
        score.setLayoutX(835);
        score.setLayoutY(425);
        mazeView = new ImageView(victory);   	           	        
        playfieldLayer.getChildren().add(mazeView);   	               
        scene = new Scene(root,Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        primaryStage.setScene(scene);
        createScoreLayer();
        input.setScene(scene);
        input.addListeners();
        enem.clear();
        items.clear();
        hearts.clear();
    }
    private void gameOver(Stage primaryStage) {
    	setCurrentScene(5);
    	Settings.map=5;
    	root = new Group();
        playfieldLayer = new Pane();   	        
        scoreLayer = new Pane();  
		root.getChildren().addAll(playfieldLayer,scoreLayer);
        mazeView = new ImageView(gameOver);   	           	        
        playfieldLayer.getChildren().add(mazeView);   	               
        scene = new Scene(root,Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        primaryStage.setScene(scene);
        createScoreLayer();
        input.setScene(scene);
        input.addListeners();
        enem.clear();
        items.clear();
        hearts.clear();
    }
    private void checkBounds(List<? extends SpriteBase> spriteList)
    {
    	for(i=0;i<spriteList.size();i++) {
	    	if( Double.compare( spriteList.get(i).getY(), player.playerMinY) < 0) {
	            spriteList.get(i).setRemovable(true);
	        } else if( Double.compare(spriteList.get(i).getY(), player.playerMaxY+50) > 0) {
	            spriteList.get(i).setRemovable(true);
	        }
	
	        // horizontal
	        if( Double.compare( spriteList.get(i).getX(), player.playerMinX) < 0) {
	            spriteList.get(i).setRemovable(true);
	        } else if( Double.compare(spriteList.get(i).getX(), player.playerMaxX-80) > 0) {
	            spriteList.get(i).setRemovable(true);
	        }
    	}
    }
    private void setCurrentScene(int currentScene)
    {
    	this.currentScene= currentScene;
    }
    int getCurrentScene()
    {
    	return currentScene;
    }
    
    private void updateScore() {
    }
    
    
    

    public static void main(String[] args) {
        launch(args);
    }

}