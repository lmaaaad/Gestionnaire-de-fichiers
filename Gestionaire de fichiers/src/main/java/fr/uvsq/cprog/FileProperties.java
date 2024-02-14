package fr.uvsq.cprog;
import java.io.Serializable;

public class FileProperties implements Serializable {
    private final int NER;
    private final String name;
    private String type;
    private String path;

    public FileProperties(int NER, String name, String type, String path) {
        this.NER = NER;
        this.name = name;
        this.type = type;
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    /*public void setTaille(String taille){
        this.taille=taille;
    }*/

    public int getNER() {
        return NER;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getPath() {
        return path;
    }
    /*public String getTaille(){
        return taille;
    }*/
}
