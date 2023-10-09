package sourceFiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class CollisionDetection extends Pane {
	double bush1Xmin=400;
	double bush1Xmax=400;
	double bush1Ymin=400;
	Polyline shape;
	Settings s;
	Enemy enemy;
	public CollisionDetection()
	{
		
	}

	public boolean intersection(double x,double y)
	{
		if(x==enemy.x1||y==enemy.y1)
		return true;
		
		return false;
	}
	public boolean mapCollision(double x, double y)
	{
		
		//x=super.x;
    	//y=super.y;
		if(Settings.map==1)
		{
			if(((x>=400&&x<=550) && y>=593)||(x>=680&&x<=814&&y<623)||(x>=814&&x<=874&&y<523)||(x>=1114&&x<=1198&&y<485)||(x>=1114&&x<=1255&&y>561)||(x>1750&&y<511&&y>691))
			{
				return false;
			}
			else
				return true;
		}else if(Settings.map==2)
		{
			if((x>=475&&x<=620&&y>=473&&y<=675)||(x>=585&&x<=825&&y>=425&&y<=725)||(x>=825&&x<=1125&&y>=425&&y<=625)||(x>=1000&&x<=1125&&y<=625))
			{
				return false;
			}
			else
				return true;
		}else if(Settings.map==3)
		{
			if((y<=520&&x>=1125&&x<=1725)||(y>=690&&x>=610&&x<=1040)||(x>=610&&x<=835&&y>=650)||(x>=1010&&x<=1125&&y>=650&&y<=790))
			{
				return false;
			}
			else
				return true;
		}
		else 
			return true;
	}
		
}
