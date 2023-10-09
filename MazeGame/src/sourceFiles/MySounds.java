package sourceFiles;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MySounds {

	protected static final String AudioPlayer = null;
	
	public Clip map4  = loadClip("/sounds/bossMap.wav");
	public Clip map3  = loadClip("/sounds/map3.wav");
	public Clip map2  = loadClip("/sounds/secondMap.wav");
	public Clip Background  = loadClip("/sounds/Main_BGM.wav");
	public Clip PlayerAttack  = loadClip("/sounds/Player_attack.wav");
	public Clip BossBackground  = loadClip("/sounds/Boss_BGM.wav");
	public Clip BossAttack  = loadClip("/sounds/Boss_attack.wav");
	public Clip VictoryBackground = loadClip("/sounds/VictoryScreen_BGM.wav");
	public Clip gameOver = loadClip("/sounds/20-Game-Over.wav");
	public Clip collided = loadClip("/sounds/collided.wav");
	
	
	/**
	 * Create the frame.
	 */
	public MySounds() {
		
	} // MySounds constructor

	public Clip loadClip(String filename)
	{
		Clip clip = null;
		
		try
		{
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(filename));
			clip = AudioSystem.getClip();
			clip.open( audioIn );
		}// try
		catch (Exception e)
		{
			e.printStackTrace();
		}// catch
		
		return(clip);
		
	} // Clip
	
	public void playClip( int index )
	{
		if (index == 1) {
		   stopClip(1);
		   Background.loop(-1);
		   Background.start();
		}
		else if (index == 2) {
		   if (!PlayerAttack.isRunning()) {
		      stopClip(2);
		      PlayerAttack.start();
		   }
		}
		else if (index == 3) {
			   if (!BossBackground.isRunning()) {
			      stopClip(3);
			      BossBackground.loop(-1);
			      BossBackground.start();
			   }
			}
		else if (index == 4) {
			   if (!BossAttack.isRunning()) {
			      stopClip(4);
			      BossAttack.start();
			   }
			}
		else if (index == 5) {
			   if (!VictoryBackground.isRunning()) {
			      stopClip(5);
			      VictoryBackground.loop(-1);
			      VictoryBackground.start();
			   }
			}
		else if (index == 6) {
			   if (!map2.isRunning()) {
				      stopClip(6);
				      map2.loop(-1);
				      map2.start();
				   }
				}
		else if (index == 7) {
			   if (!map2.isRunning()) {
				      stopClip(7);
				      map3.loop(-1);
				      map3.start();
				   }
				}
		else if (index == 8) {
			   if (!map3.isRunning()) {
				      stopClip(8);
				      map4.loop(-1);
				      map4.start();
				   }
				}
		else if(index==9)
		{
			for(int i=0;i<8;i++)
			stopClip(i);
			gameOver.loop(-1);
		    gameOver.start();
		}else if(index==10)
		{
			collided.loop(1);
		    collided.start();
		}
	} // playClip
	
	public void stopClip( int index )
	{
		if (index == 1) {
		   if (Background.isRunning() )
			   Background.stop();
		   Background.setFramePosition(0);
		}
		else if (index == 2) {
		   if (PlayerAttack.isRunning() )
			   PlayerAttack.stop();
		   PlayerAttack.setFramePosition(0);
		}
		else if (index == 3) {
			   if (BossBackground.isRunning() )
				   BossBackground.stop();
			   BossBackground.setFramePosition(0);
			}
		else if (index == 4) {
			   if (BossAttack.isRunning() )
				   BossAttack.stop();
			   BossAttack.setFramePosition(0);
			}
		else if (index == 5) {
			   if (VictoryBackground.isRunning() )
				   VictoryBackground.stop();
			   VictoryBackground.setFramePosition(0);
			}
		else if (index == 6) {
			   if (map2.isRunning())
				   map2.stop();
			   map2.setFramePosition(0);
			}
		else if (index == 7) {
			   if (map3.isRunning())
				   map3.stop();
			   map3.setFramePosition(0);
			}
		else if (index == 8) {
			   if (map4.isRunning())
				   map4.stop();
			   map4.setFramePosition(0);
			}
		
	} // stopClip
	
} // MySounds class
