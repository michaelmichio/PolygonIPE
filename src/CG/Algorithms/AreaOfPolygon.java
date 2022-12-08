package CG.Algorithms;

import CG.Objects.Point;
import CG.Objects.Polygon;
import Ipe.DocumentManager;
import org.w3c.dom.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AreaOfPolygon implements CGA {
    private Document doc;
    private ArrayList<Point> vertices;
    private String[] pathValues;

    public AreaOfPolygon(Document doc) {
        this.doc = doc;
    }

    public Document getResult() {
        DocumentManager documentManager = new DocumentManager(this.doc);
        this.vertices = documentManager.getPolygons().get(0).vertices;
        this.sortVertices();
        this.generateSteps();
        return null;
//        this.modifyDocument();
//        return this.doc;
    }

    private void sortVertices() {
        // find vertice with the lowest y
        double minY = Integer.MAX_VALUE;
        int idxMinY = 0;
        for(int i = 0; i < vertices.size(); i++) {
            if(vertices.get(i).y < minY) {
                minY = vertices.get(i).y;
                idxMinY = i;
            }
        }
        // check previous and next point from vertices.get(idxMinY)
        if(idxMinY == 0) { // compare index 1 and size()-1

        }
        else { // compare index idxMinY-1 and idxMinY+1

        }
    }

    private void generateSteps() {

    }

//    public void modifyDocument() {
//
//        /**
//         * [0] ipe
//         * - [0] ...
//         */
//        Node ipeNode = this.doc.getElementsByTagName("ipe").item(0);
//
//        NodeList ipeNodeList = ipeNode.getChildNodes();
//
//        for(int i = 0; i < ipeNodeList.getLength(); i++) {
//
//            /**
//             * [0] ipe
//             * - [0] ?xml
//             * - [1] !DOCTYPE
//             * - [2] ipe
//             * - [3] info
//             * - [4] preamble
//             * - [5] bitmap
//             * - [6] ipestyle
//             *   - [0] ...
//             * - [7] ipestyle
//             *   - [0] ...
//             * - [8] page
//             *   - [0] ...
//             */
//            Node ipeChildNode = ipeNodeList.item(i);
//
//            if(ipeChildNode.getNodeType() == Node.ELEMENT_NODE) {
//
//                Element ipeChildElement = (Element) ipeChildNode;
//
//                /**
//                 * [0] ipe
//                 * - ...
//                 * - [3] info
//                 * - ...
//                 */
//                // [3] info: modify attributes ( format: D:yyyyMMddHHmmss )
//                if(ipeChildElement.getTagName().equals("info")) {
//                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//                    LocalDateTime now = LocalDateTime.now();
//                    String attInfo = dtf.format(now);
//
//                    ipeChildElement.setAttribute("created", "D:" + attInfo);
//                    ipeChildElement.setAttribute("modified", "D:" + attInfo);
//                }
//                /**
//                 * [0] ipe
//                 * - ...
//                 * - [8] page
//                 *   - [0] layer
//                 *   - [1] view
//                 *   - [2] path
//                 */
//                // [8] page: modify child nodes
//                else if(ipeChildElement.getTagName().equals("page")) {
//
//                    NodeList pageNodeList = ipeChildElement.getChildNodes();
//
//                    // read & store input from first path
//                    for(int j = 0; j < pageNodeList.getLength(); j++) {
//                        Node pageChildNode = pageNodeList.item(j);
//
//                        // System.out.println(pageChildNode.getNodeName());
//                        // #text, layer, #text, view, #text, path, #text
//
//                        if(pageChildNode.getNodeType() == Node.ELEMENT_NODE) {
//
//                            Element pageChildElement = (Element) pageChildNode;
//
//                            if(pageChildElement.getTagName().equals("path")) {
//                                this.pathValues = pageChildElement.getFirstChild().getNodeValue().split("\n");
//                                break;
//                            }
//
//                        }
//                    }
//
//                    // clear page child nodes
//                    while (ipeChildElement.hasChildNodes()) {
//                        ipeChildElement.removeChild(ipeChildElement.getFirstChild());
//                    }
//
//                    // generate new layers
//                    for(int j = 0; j < pathValues.length - 3; j++) {
//                        Element element = ipeChildElement.getOwnerDocument().createElement("layer");
//                        ipeChildElement.appendChild(element);
//
//                        Attr attr = ipeChildElement.getOwnerDocument().createAttribute("name");
//                        attr.setValue(String.valueOf(j));
//                        element.setAttributeNode(attr);
//                    }
//
//                    // generate new views
//                    for(int j = 0; j < pathValues.length - 3; j++) {
//                        Element element = ipeChildElement.getOwnerDocument().createElement("view");
//                        ipeChildElement.appendChild(element);
//
//                        Attr attr = ipeChildElement.getOwnerDocument().createAttribute("layers");
//                        if(j == 0) {
//                            attr.setValue("0");
//                        }
//                        else {
//                            attr.setValue("0 " + j);
//                        }
//                        element.setAttributeNode(attr);
//                    }
//
//                    // generate new paths
//                    for(int j = 0; j < pathValues.length - 3; j++) {
//                        Element element;
//                        Attr attr;
//                        Text text;
//
//                        // attributes
//                        element = ipeChildElement.getOwnerDocument().createElement("path");
//                        ipeChildElement.appendChild(element);
//
//                        attr = element.getOwnerDocument().createAttribute("layer");
//                        attr.setValue(String.valueOf(j));
//                        element.setAttributeNode(attr);
//
//                        attr = element.getOwnerDocument().createAttribute("stroke");
//                        attr.setValue("black");
//                        element.setAttributeNode(attr);
//
//                        if(j > 0) {
//                            attr = element.getOwnerDocument().createAttribute("fill");
//                            attr.setValue("lightgreen");
//                            element.setAttributeNode(attr);
//
//                            attr = element.getOwnerDocument().createAttribute("opacity");
//                            attr.setValue("50%");
//                            element.setAttributeNode(attr);
//
//                            for(int k = 1; k < j + 3; k++) {
//                                text = this.doc.createTextNode("\n" + pathValues[k]);
//                                element.appendChild(text);
//                            }
//                            text = this.doc.createTextNode("\nh\n");
//                            element.appendChild(text);
//                        }
//                        else {
//                            for(int k = 1; k < pathValues.length; k++) {
//                                text = this.doc.createTextNode("\n" + pathValues[k]);
//                                element.appendChild(text);
//                            }
//                            text = this.doc.createTextNode("\n");
//                            element.appendChild(text);
//                        }
//                    }
//
//                }
//
//            }
//
//        }
//
//    }

}
