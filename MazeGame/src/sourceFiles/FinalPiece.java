package sourceFiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class FinalPiece extends SpriteBase {
	double x, y;
	public FinalPiece(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health,
			double damage) {
		super(layer, image, x, y, r, dx, dy, dr, health, damage);
		// TODO Auto-generated constructor stub
	}
	

	
	public void setX(double x)
	{
		this.x=x;
	}
	public double getX()
	{
		return x;
	}
	public void setY(double y)
	{
		this.y=y;
	}
	public double getY()
	{
		return y;
	}
	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
}
