package net.burakkarahan.cityguide.Model;

public class ModelFavoriteShow{
	private String name_tr;
	private String image;
	private Object id_Favorite;
	private String first_location;
	private String city;
	private String name;
	private String county;
	private String information;
	private Object id_user;
	private String second_location;
	private String id_structure;
	private String information_tr;

	public String getName_tr() {
		return name_tr;
	}

	public void setName_tr(String name_tr) {
		this.name_tr = name_tr;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Object getId_Favorite() {
		return id_Favorite;
	}

	public void setId_Favorite(Object id_Favorite) {
		this.id_Favorite = id_Favorite;
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

	public Object getId_user() {
		return id_user;
	}

	public void setId_user(Object id_user) {
		this.id_user = id_user;
	}

	public String getSecond_location() {
		return second_location;
	}

	public void setSecond_location(String second_location) {
		this.second_location = second_location;
	}

	public String getId_structure() {
		return id_structure;
	}

	public void setId_structure(String id_structure) {
		this.id_structure = id_structure;
	}

	public String getInformation_tr() {
		return information_tr;
	}

	public void setInformation_tr(String information_tr) {
		this.information_tr = information_tr;
	}

	@Override
	public String toString() {
		return "ModelFavoriteShow{" +
				"name_tr='" + name_tr + '\'' +
				", image='" + image + '\'' +
				", id_Favorite=" + id_Favorite +
				", first_location='" + first_location + '\'' +
				", city='" + city + '\'' +
				", name='" + name + '\'' +
				", county='" + county + '\'' +
				", information='" + information + '\'' +
				", id_user=" + id_user +
				", second_location='" + second_location + '\'' +
				", id_structure='" + id_structure + '\'' +
				", information_tr='" + information_tr + '\'' +
				'}';
	}
}
