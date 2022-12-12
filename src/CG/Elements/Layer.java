package CG.Elements;

import java.util.ArrayList;
import java.util.HashMap;

public class Layer {
    public String layerType;
    public String view;
    public HashMap<String, String> attributes = new HashMap<>();
    public ArrayList<Path> paths = new ArrayList<>();
    public ArrayList<Use> uses = new ArrayList<>();

    public Layer(String layerType, String view, HashMap<String, String> attributes, ArrayList<Path> paths, ArrayList<Use> uses) {
        this.layerType = layerType;
        this.view = view;
        this.attributes = attributes;
        this.paths = paths;
        this.uses = uses;
    }

}
