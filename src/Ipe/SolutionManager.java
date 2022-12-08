package Ipe;

import CG.Algorithms.AreaOfPolygon;
import org.w3c.dom.Document;

public class SolutionManager {
    private Document doc;
    private String algorithm;

    public SolutionManager(Document doc, String algorithm) {
        this.doc = doc;
        this.algorithm = algorithm;
    }

    public void printOutput() {
        if(this.doc == null || this.algorithm == null) {
            System.out.println("failed to get the output.");
        }
        else {
            this.runAlgorithm();
            new OutputManager(this.doc).print();
        }
    }

    private void runAlgorithm() {
        DocumentManager documentManager = new DocumentManager(this.doc);


        switch (this.algorithm) {
            case "area-of-polygon":
                this.doc = new AreaOfPolygon(this.doc).getResult();
                break;
            case "bentley-ottmann":
                //
                break;
            case "graham-scan":
                //
                break;
            case "triangulation-monotone-polygon":
                //
                break;
        }

    }
}
