public class Persona {
    private String nombre;
    private String apeP;
    private String apeM;
    private String edad;


    public Persona(String nombre, String apeP, String apeM, String edad){
        this.nombre = nombre;
        this.apeP = apeP;
        this.apeM = apeM;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApeP() {
        return apeP;
    }

    public void setApeP(String apeP) {
        this.apeP = apeP;
    }

    public String getApeM() {
        return apeM;
    }

    public void setApeM(String apeM) {
        this.apeM = apeM;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return nombre+'\t'+'\t'+'\t'+apeP+'\t'+'\t'+'\t'+'\t'+apeM+'\t'+'\t'+'\t'+'\t'+edad;
    }
}
