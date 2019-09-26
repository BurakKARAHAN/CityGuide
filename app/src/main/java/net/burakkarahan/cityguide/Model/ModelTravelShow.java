package net.burakkarahan.cityguide.Model;

public class ModelTravelShow{
	private String date;
	private String name;
	private String id_structure;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_structure() {
        return id_structure;
    }

    public void setId_structure(String id_structure) {
        this.id_structure = id_structure;
    }

    @Override
    public String toString() {
        return "ModelTravelShow{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", id_structure='" + id_structure + '\'' +
                '}';
    }
}
