package net.burakkarahan.cityguide.Model;

public class ModelFavoriteAdd{
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
			"ModelFavoriteAdd{" + 
			"tf = '" + tf + '\'' + 
			"}";
		}
}
