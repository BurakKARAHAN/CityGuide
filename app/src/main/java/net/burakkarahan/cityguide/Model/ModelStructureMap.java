package net.burakkarahan.cityguide.Model;

public class ModelStructureMap{
	private String image;
	private String first_location;
	private String city;
	private String name;
	private String county;
	private String information;
	private String second_location;
	private int id_structure;
	private String name_tr;
	private String information_tr;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFirst_location() {
		return first_location;
	}

	public void setFirst_location(String first_location) {
		this.first_location = first_location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getSecond_location() {
		return second_location;
	}

	public void setSecond_location(String second_location) {
		this.second_location = second_location;
	}

	public int getId_structure() {
		return id_structure;
	}

	public void setId_structure(int id_structure) {
		this.id_structure = id_structure;
	}

	public String getName_tr() {
		return name_tr;
	}

	public void setName_tr(String name_tr) {
		this.name_tr = name_tr;
	}

	public String getInformation_tr() {
		return information_tr;
	}

	public void setInformation_tr(String information_tr) {
		this.information_tr = information_tr;
	}

	@Override
	public String toString() {
		return "ModelStructureMap{" +
				"image='" + image + '\'' +
				", first_location='" + first_location + '\'' +
				", city='" + city + '\'' +
				", name='" + name + '\'' +
				", county='" + county + '\'' +
				", information='" + information + '\'' +
				", second_location='" + second_location + '\'' +
				", id_structure=" + id_structure +
				", name_tr='" + name_tr + '\'' +
				", information_tr='" + information_tr + '\'' +
				'}';
	}
}
