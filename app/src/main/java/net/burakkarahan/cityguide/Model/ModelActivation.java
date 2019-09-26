package net.burakkarahan.cityguide.Model;

public class ModelActivation{
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
			"ModelActivation{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}
