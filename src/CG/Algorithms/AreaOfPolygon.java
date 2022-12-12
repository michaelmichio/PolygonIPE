package CG.Algorithms;

import CG.Elements.Layer;
import CG.Elements.Path;
import CG.Objects.LineSegment;
import CG.Objects.Point;
import Ipe.DocumentManager;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AreaOfPolygon implements CGA {
    private Document doc;
    private ArrayList<Point> vertices = new ArrayList<>();

    public AreaOfPolygon(Document doc) {
        this.doc = doc;
    }

    public Document getResult() {
        DocumentManager documentManager = new DocumentManager(this.doc);
        this.vertices = documentManager.getPolygons().get(0).vertices;
        this.sortVertices();
        Document outputDoc = documentManager.printOutputDocument(this.generateLayer());
        return outputDoc;
    }

    private void sortVertices() {
        ArrayList<Point> pointsTemp = new ArrayList<>();
        // find vertice with the lowest y
        double minY = Integer.MAX_VALUE;
        int idxMinY = 0;
        for(int i = 0; i < vertices.size(); i++) {
            if(vertices.get(i).y < minY) {
                minY = vertices.get(i).y;
                idxMinY = i;
            }
        }
        if(idxMinY != 0) { // sort for y
            if(idxMinY == vertices.size() - 1) {
                pointsTemp.add(vertices.get(idxMinY));
                for(int i = 0; i < vertices.size() - 1; i++) {
                    pointsTemp.add(vertices.get(i));
                }
                vertices.clear();
                for(Point p : pointsTemp) {
                    vertices.add(p);
                }
            }
            else {
                for(int i = idxMinY; i < vertices.size(); i++) {
                    pointsTemp.add(vertices.get(i));
                }
                for(int i = 0; i < idxMinY; i++) {
                    pointsTemp.add(vertices.get(i));
                }
                vertices.clear();
                for(Point p : pointsTemp) {
                    vertices.add(p);
                }
            }
        }
        pointsTemp.clear();
        if(vertices.get(1).x < vertices.get(vertices.size()-1).x) { // sort for x
            pointsTemp.add(vertices.get(0));
            for(int i = vertices.size() - 1; i > 0; i--) {
                pointsTemp.add(vertices.get(i));
            }
            vertices.clear();
            for(Point p : pointsTemp) {
                vertices.add(p);
            }
        }
        pointsTemp.clear();
    }

    private ArrayList<Layer> generateLayer() {
        ArrayList<Layer> layers = new ArrayList<>();
        HashMap<String, String> attributes = new HashMap<>();
        ArrayList<Path> paths = new ArrayList<>();

        ArrayList<Point> points = new ArrayList<>();
        for(Point p : vertices) {
            points.add(p);
        }

        // set initial layer
        attributes.put("layer", "0");
        attributes.put("stroke", "black");
        paths.add(new Path(points));
        layers.add(new Layer("path", attributes, paths, null));

        // set step layers
        for(int i = 0; i < vertices.size() - 2; i++) {
//            attributes = new HashMap<>();
//            paths = new ArrayList<>();
//
//            points = new ArrayList<>();
//            for(Point p : vertices) {
//                points.add(p);
//            }
//
//            attributes.put("layer", String.valueOf(i+1));
//            attributes.put("stroke", "white");
//            attributes.put("fill", "lightgreen");
//            attributes.put("opacity", "50%");
//            attributes.put("stroke-opacity", "opaque");
//            paths.add(new Path(points).subPath(0, i+2));
//            layers.add(new Layer("path", attributes, paths, null));

            LineSegment ls = new LineSegment(new Point(vertices.get(0).x, vertices.get(0).y),
                    new Point(vertices.get(i + 1).x, vertices.get(i + 1).y));
            Point p = new Point(vertices.get(i + 2).x, vertices.get(i + 2).y);
            if(ls.crossProductToPoint(p) > 0) { // right
                // ...
//                System.out.println("right: " + ls.crossProductToPoint(p));
                attributes = new HashMap<>();
                paths = new ArrayList<>();

                points = new ArrayList<>();
                for(Point pp : vertices) {
                    points.add(pp);
                }

                attributes.put("layer", String.valueOf(i+1));
                attributes.put("stroke", "white");
                attributes.put("fill", "lightgreen");
                attributes.put("opacity", "50%");
                attributes.put("stroke-opacity", "opaque");
                paths.add(new Path(points).triangulation(i+1, i+2));
                layers.add(new Layer("path", attributes, paths, null));
            }
            else if(ls.crossProductToPoint(p) < 0) { // left
                // ...
//                System.out.println("left: " + ls.crossProductToPoint(p));
                attributes = new HashMap<>();
                paths = new ArrayList<>();

                points = new ArrayList<>();
                for(Point pp : vertices) {
                    points.add(pp);
                }

                attributes.put("layer", String.valueOf(i+1));
                attributes.put("stroke", "white");
                attributes.put("fill", "red");
                attributes.put("opacity", "50%");
                attributes.put("stroke-opacity", "opaque");
                paths.add(new Path(points).triangulation(i+1, i+2));
                layers.add(new Layer("path", attributes, paths, null));
            }
            else { // collinear
                // ...
            }
        }

        return layers;
    }

}
