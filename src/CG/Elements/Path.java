package CG.Elements;

import CG.Objects.Point;

import java.util.ArrayList;

public class Path {
    ArrayList<Point> vertices = new ArrayList<>();
    String[] paths;

    public Path(ArrayList<Point> vertices) {
        this.vertices = vertices;
        String[] paths = new String[vertices.size()];
        for(int i = 0; i < vertices.size(); i++) {
            paths[i] = vertices.get(i).toString();
        }
        this.paths = paths;
    }

    public String[] polygon() {
        String[] polygon = new String[this.paths.length + 1];
        for(int i = 0; i < polygon.length; i++) {
            if(i == 0) {
                polygon[i] = paths[i] + " m";
            }
            else if(i == polygon.length - 1){
                polygon[i] = "h";
            }
            else {
                polygon[i] = paths[i] + " l";
            }
        }
        return polygon;
    }

    public Path subPath(int start, int end) {
        String[] subPaths = new String[end - start + 1];
        int idx = 0;
        for(int i = start; i <= end; i++) {
            subPaths[idx] = this.paths[i];
            idx++;
        }
        this.paths = subPaths;
        return this;
    }

    public Path triangulation(int p1, int p2) {
        String[] triangulation = new String[3];
        triangulation[0] = this.paths[0];
        triangulation[1] = this.paths[p1];
        triangulation[2] = this.paths[p2];
        this.paths = triangulation;
        return this;
    }
}
