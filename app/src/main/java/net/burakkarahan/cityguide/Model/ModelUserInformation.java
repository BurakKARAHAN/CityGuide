package net.burakkarahan.cityguide.Model;

public class ModelUserInformation{
	private String image;
	private String surname;
	private String name;
	private String email;
	private String status;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ModelUserInformation{" +
				"image='" + image + '\'' +
				", surname='" + surname + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
