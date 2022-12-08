package Ipe;

import CG.Objects.LineSegment;
import CG.Objects.Point;
import CG.Objects.Polygon;
import org.w3c.dom.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DocumentManager {
    private Document doc;
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();
    private ArrayList<Polygon> polygons = new ArrayList<>();

    public DocumentManager(Document doc) {
        this.doc = doc;
        this.getObject();
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public ArrayList<LineSegment> getLineSegments() {
        return this.lineSegments;
    }

    public ArrayList<Polygon> getPolygons() {
        return this.polygons;
    }

    private void getObject() {
        /**
         * [0] ipe
         * - [0] ...
         */
        Node ipeNode = this.doc.getElementsByTagName("ipe").item(0);

        NodeList ipeNodeList = ipeNode.getChildNodes();

        for(int i = 0; i < ipeNodeList.getLength(); i++) { // ipe child nodes

            /**
             * [0] ipe
             * - [0] ?xml
             * - [1] !DOCTYPE
             * - [2] ipe
             * - [3] info
             * - [4] preamble
             * - [5] bitmap
             * - [6] ipestyle
             *   - [0] ...
             * - [7] ipestyle
             *   - [0] ...
             * - [8] page
             *   - [0] ...
             */
            Node ipeChildNode = ipeNodeList.item(i);

            if(ipeChildNode.getNodeType() == Node.ELEMENT_NODE) { // ignore #text from ipe child nodes

                Element ipeChildElement = (Element) ipeChildNode;

                /**
                 * [0] ipe
                 * - ...
                 * - [3] info
                 * - ...
                 */
                // [3] info: modify attributes ( format: D:yyyyMMddHHmmss )
                if(ipeChildElement.getTagName().equals("info")) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    LocalDateTime now = LocalDateTime.now();
                    String attInfo = dtf.format(now);

                    ipeChildElement.setAttribute("created", "D:" + attInfo);
                    ipeChildElement.setAttribute("modified", "D:" + attInfo);
                }
                /**
                 * [0] ipe
                 * - ...
                 * - [8] page
                 *   - [0] layer
                 *   - [1] view
                 *   - [2] path
                 */
                // [8] page: modify child nodes
                else if(ipeChildElement.getTagName().equals("page")) {

                    NodeList pageNodeList = ipeChildElement.getChildNodes();

                    // read & store input from first path
                    for(int j = 0; j < pageNodeList.getLength(); j++) {
                        Node pageChildNode = pageNodeList.item(j);

                        // System.out.println(pageChildNode.getNodeName());
                        // #text, layer, #text, view, #text, path, #text

                        if(pageChildNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element pageChildElement = (Element) pageChildNode;

                            // only get path and use on the first (alpha) page

                            if(pageChildElement.getTagName().equals("path") &&
                                    !pageChildElement.getAttribute("layer").equals("alpha") &&
                                    !pageChildElement.getAttribute("layer").equals("")) {
                                break;
                            }

                            if(pageChildElement.getTagName().equals("use") &&
                                    !pageChildElement.getAttribute("layer").equals("alpha") &&
                                    !pageChildElement.getAttribute("layer").equals("")) {
                                break;
                            }

                            if(pageChildElement.getTagName().equals("path")) {
                                String[] paths = pageChildElement.getFirstChild().getNodeValue().split("\n");
                                if(paths.length == 3) { // line segment (?)
                                    // paths[0] is blank
                                    String[] m = paths[1].split(" ");
                                    String[] l = paths[2].split(" ");
                                    this.lineSegments.add(new LineSegment(new Point(Integer.parseInt(m[0]), Integer.parseInt(m[1])), new Point(Integer.parseInt(l[0]), Integer.parseInt(l[1]))));
                                }
                                else if(paths.length > 3 && paths[paths.length - 1].equals("h")) { // polygon (?)
                                    // paths[0] is blank
                                    ArrayList<Point> vertices = new ArrayList<>();
                                    for(int k = 1; k < paths.length - 2; k++) {
                                        String[] m = paths[k].split(" ");
                                        vertices.add(new Point(Integer.parseInt(m[0]), Integer.parseInt(m[1])));
                                    }
                                    String[] l = paths[paths.length - 2].split(" ");
                                    vertices.add(new Point(Integer.parseInt(l[0]), Integer.parseInt(l[1])));
                                    // paths[paths.length - 1] is "h"
                                    this.polygons.add(new Polygon(vertices));
                                }
                            }
                            else if(pageChildElement.getTagName().equals("use")) { // point (?)
                                String[] pos = pageChildElement.getAttribute("pos").split(" ");
                                this.points.add(new Point(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])));
                            }

                        }
                    }

                    // clear page child nodes
                    while (ipeChildElement.hasChildNodes()) {
                        ipeChildElement.removeChild(ipeChildElement.getFirstChild());
                    }

                }

            }

        }
    }

}
