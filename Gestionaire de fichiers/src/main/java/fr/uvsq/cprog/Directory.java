package fr.uvsq.cprog;

import java.util.ArrayList;
import java.util.List;

public class Directory extends FileProperties {
    private final List<FileProperties> elements;

    public Directory(int NER, String name, String path) {
        super(NER, name, "directory", path);
        this.elements = new ArrayList<>();
    }
    public List<FileProperties> getElements() {
        return elements;
    }
    public void addElement(FileProperties element) {
        elements.add(element);
    }
}
