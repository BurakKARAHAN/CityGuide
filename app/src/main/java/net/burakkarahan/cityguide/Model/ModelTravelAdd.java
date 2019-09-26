package net.burakkarahan.cityguide.Model;

public class ModelTravelAdd{
	private boolean tf;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	@Override
 	public String toString(){
		return 
			"ModelTravelAdd{" + 
			"tf = '" + tf + '\'' + 
			"}";
		}
}
