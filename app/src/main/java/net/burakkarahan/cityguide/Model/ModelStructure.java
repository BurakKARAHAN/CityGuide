package net.burakkarahan.cityguide.Model;

public class ModelStructure{
	private String image;
	private String first_location;
	private String city;
	private String name;
	private String county;
	private String information;
	private String second_location;
	private int id_structure;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setFirstLocation(String firstLocation){
		this.first_location = firstLocation;
	}

	public String getFirstLocation(){
		return first_location;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCounty(String county){
		this.county = county;
	}

	public String getCounty(){
		return county;
	}

	public void setInformation(String information){
		this.information = information;
	}

	public String getInformation(){
		return information;
	}

	public void setSecondLocation(String secondLocation){
		this.second_location = secondLocation;
	}

	public String getSecondLocation(){
		return second_location;
	}

	public void setIdStructure(int idStructure){
		this.id_structure = idStructure;
	}

	public int getIdStructure(){
		return id_structure;
	}

	@Override
	public String toString() {
		return "ModelStructure{" +
				"image='" + image + '\'' +
				", first_location='" + first_location + '\'' +
				", city='" + city + '\'' +
				", name='" + name + '\'' +
				", county='" + county + '\'' +
				", information='" + information + '\'' +
				", second_location='" + second_location + '\'' +
				", id_structure=" + id_structure +
				'}';
	}
}
