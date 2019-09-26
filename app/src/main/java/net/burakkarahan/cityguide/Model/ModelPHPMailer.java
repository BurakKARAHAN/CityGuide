package net.burakkarahan.cityguide.Model;

public class ModelPHPMailer{
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
			"ModelPHPMailer{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}
