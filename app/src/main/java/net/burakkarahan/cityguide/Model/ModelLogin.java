package net.burakkarahan.cityguide.Model;

public class ModelLogin {

    private Integer iduser;
    private String email;

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ModelLogin{" +
                "iduser=" + iduser +
                ", email='" + email + '\'' +
                '}';
    }
}
