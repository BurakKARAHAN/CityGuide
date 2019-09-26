package net.burakkarahan.cityguide.Model;

public class ModelSignup {

    private String result;
    private boolean tf;
    private String activationCode;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public String toString() {
        return "ModelSignup{" +
                "result='" + result + '\'' +
                ", tf=" + tf +
                ", activationCode='" + activationCode + '\'' +
                '}';
    }
}
