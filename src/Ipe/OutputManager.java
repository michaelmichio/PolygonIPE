package Ipe;

import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class OutputManager {
    private Document outputDoc;

    public OutputManager(Document doc) {
        this.outputDoc = doc;
    }

    public void print() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(this.outputDoc);
            StreamResult streamResult = new StreamResult(new File("output.ipe"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
            transformer.transform(source, streamResult);
        } catch (Exception e) {
            System.out.println("failed to print result.");
        }
    }
}
