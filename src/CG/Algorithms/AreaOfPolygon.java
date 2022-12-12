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
        int currentLayer = 0;

        attributes.put("layer", "0");
        attributes.put("fill", "white");
        paths.add(new Path(points));
        String view = "0";
        layers.add(new Layer("path", view, attributes, paths, null));
        currentLayer++;

        // set step layers
        double totalArea = 0.0;
        double additionArea = 0.0;
        for(int i = 0; i < vertices.size() - 2; i++) {
            if(i > 0) {
                // ---
                attributes = new HashMap<>();
                paths = new ArrayList<>();

                points = new ArrayList<>();
                for(Point pp : vertices) {
                    points.add(pp);
                }

                attributes.put("layer", String.valueOf(currentLayer));
                attributes.put("fill", "lightgreen");
                attributes.put("opacity", "50%");
                paths.add(new Path(points).subPath(0, i+1));
                view = "";
                layers.add(new Layer("path", view, attributes, paths, null));
                currentLayer++;
                // ---
            }

            LineSegment ls = new LineSegment(new Point(vertices.get(0).x, vertices.get(0).y),
                    new Point(vertices.get(i + 1).x, vertices.get(i + 1).y));
            Point p = new Point(vertices.get(i + 2).x, vertices.get(i + 2).y);

            if(ls.crossProductToPoint(p) > 0) { // right
                attributes = new HashMap<>();
                paths = new ArrayList<>();

                points = new ArrayList<>();
                for(Point pp : vertices) {
                    points.add(pp);
                }

                attributes.put("layer", String.valueOf(currentLayer));
                attributes.put("fill", "lightgreen");
                attributes.put("opacity", "50%");
                paths.add(new Path(points).triangulation(0, i+1, i+2));
                view = currentLayer - 1 + " " + currentLayer;
                layers.add(new Layer("path", view, attributes, paths, null));

                // label
//                additionArea = new Path(points).triangle(i, i+1, i+2);
//                String[] texts = new String[1];
//                texts[0] = totalArea + " + " + additionArea + " = " + totalArea + additionArea;
//                paths.add(new Path(points).text(texts));
//                totalArea += new Path(points).triangle(i, i+1, i+2);
//
//                attributes.put("matrix", "1 0 0 1 -656 -32");
//                attributes.put("transformations", "translations");
//                attributes.put("pos", "672 48");
//                attributes.put("stroke", "black");
//                attributes.put("type", "label");
//                attributes.put("width", "186.439");
//                attributes.put("height", "18.5735");
//                attributes.put("depth", "0.2475");
//                attributes.put("valign", "baseline");
//                layers.add(new Layer("text", attributes, paths, null));

                currentLayer++;
            }
            else if(ls.crossProductToPoint(p) < 0) { // left
                attributes = new HashMap<>();
                paths = new ArrayList<>();

                points = new ArrayList<>();
                for(Point pp : vertices) {
                    points.add(pp);
                }

                attributes.put("layer", String.valueOf(currentLayer));
                attributes.put("fill", "red");
                attributes.put("opacity", "50%");
                paths.add(new Path(points).triangulation(0, i+1, i+2));
                view = currentLayer - 1 + " " + currentLayer;
                layers.add(new Layer("path", view, attributes, paths, null));
                currentLayer++;

//                attributes = new HashMap<>();
//                paths = new ArrayList<>();
//
//                points = new ArrayList<>();
//                for(Point pp : vertices) {
//                    points.add(pp);
//                }
//
//                attributes.put("layer", String.valueOf(currentLayer));
//                attributes.put("fill", "white");
//                attributes.put("stroke", "white");
//                paths.add(new Path(points).triangulation(0, i+1, i+2));
//                layers.add(new Layer("path", attributes, paths, null));
//                currentLayer++;
            }
            else { // collinear
                // ...
            }
        }

        attributes = new HashMap<>();
        paths = new ArrayList<>();

        points = new ArrayList<>();
        for(Point pp : vertices) {
            points.add(pp);
        }

        attributes.put("layer", String.valueOf(currentLayer));
        attributes.put("stroke", "black");
        paths.add(new Path(points));
        layers.add(new Layer("path", String.valueOf(currentLayer), attributes, paths, null));

        return layers;
    }

}
