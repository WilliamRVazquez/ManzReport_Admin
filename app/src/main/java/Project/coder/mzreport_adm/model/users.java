package Project.coder.mzreport_adm.model;

public class users {
    String tiporeporte;

    public users(){

    }

    public users(String tiporeporte, String ubicacion) {
        this.tiporeporte = tiporeporte;

    }

    public String getTiporeporte() {
        return tiporeporte;
    }

    public void setTiporeporte(String tiporeporte) {
        this.tiporeporte = tiporeporte;
    }


}