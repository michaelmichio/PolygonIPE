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

    public String[] getPath() {
        if(this.paths.length == 1) {
            return this.label();
        }
        return this.polygon();
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

    public String[] label() {
        return this.paths;
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

    public Path triangulation(int p1, int p2, int p3) {
        String[] triangulation = new String[3];
        triangulation[0] = this.paths[p1];
        triangulation[1] = this.paths[p2];
        triangulation[2] = this.paths[p3];
        this.paths = triangulation;
        return this;
    }

    public Path text(String[] texts) {
        this.paths = texts;
        return this;
    }

    public double triangle(int p1, int p2, int p3) {
        return Math.abs((this.vertices.get(p1).x * (this.vertices.get(p2).y - this.vertices.get(p3).y) + this.vertices.get(p2).x * (this.vertices.get(p3).y - this.vertices.get(p1).y) + this.vertices.get(p3).x * (this.vertices.get(p1).y - this.vertices.get(p2).y)) / 2.0);
    }
}
