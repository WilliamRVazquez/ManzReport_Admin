package Project.coder.mzreport_adm.model;

public class Report {
    String tiporeporte;

    public Report(){

    }

    public Report(String tiporeporte, String ubicacion) {
        this.tiporeporte = tiporeporte;

    }

    public String getTiporeporte() {
        return tiporeporte;
    }

    public void setTiporeporte(String tiporeporte) {
        this.tiporeporte = tiporeporte;
    }


}
