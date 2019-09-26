package net.burakkarahan.cityguide.Model;

public class ModelStructureImage{
	private String image;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	@Override
 	public String toString(){
		return 
			"ModelStructureImage{" + 
			"image = '" + image + '\'' + 
			"}";
		}
}
