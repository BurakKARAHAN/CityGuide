package net.burakkarahan.cityguide.Model;

public class ModelUserImage{
	private String result;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"ModelUserImage{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}
