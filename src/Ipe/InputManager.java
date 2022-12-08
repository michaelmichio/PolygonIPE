package Ipe;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class InputManager {
    private String fileName;
    private String algorithm;
    private Document inputDoc;
    private String[] algorithms = {"area-of-polygon", "bentley-ottmann", "graham-scan", "triangulation-monotone-polygon"}; // hardcoded - fix later

    public InputManager(String[] args) {
        if(args.length < 2) {
            errorMessage("Please input a complete arguments, example: /java Main <filename.ipe> <algorithm-name>");
        }
        else {
            this.fileName = args[0];
            this.algorithm = args[1];
        }
    }

    private void errorMessage(String errMsg) {
        System.out.println(errMsg);
    }

    public SolutionManager getSolution() {
        if(!this.validation()) {
            return new SolutionManager(null, null);
        }
        return new SolutionManager(this.inputDoc, this.algorithm);
    }

    public boolean validation() {
        return this.fileNameValidation() && this.algorithmNameValidation();
    }

    private boolean fileNameValidation() {
        if(this.fileName == null) {
            this.errorMessage("file name is null.");
            return false;
        }

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            documentBuilderFactory.setValidating(false); // ignore DTD
            documentBuilderFactory.setFeature("http://xml.org/sax/features/namespaces", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/validation", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            this.inputDoc = documentBuilder.parse(this.getClass().getClassLoader().getResourceAsStream(this.fileName));

            return true;
        } catch (Exception e) {
            errorMessage("file not found.");
            return false;
        }
    }

    private boolean algorithmNameValidation() {
        if(this.algorithm == null) {
            this.errorMessage("algorithm name is null.");
            return false;
        }

        for(String algorithm : algorithms) {
            if(this.algorithm.equalsIgnoreCase(algorithm)) {
                return true;
            }
        }

        errorMessage("algorithm not found.");
        return false;
    }
}
