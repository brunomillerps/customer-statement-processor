package bmps.statement.processor.fileManager.xml;

import bmps.statement.processor.fileManager.FileReader;
import bmps.statement.processor.processors.TransactionProcessor;
import bmps.statement.processor.domain.Transaction;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashSet;

public class XmlDomFileReader implements FileReader {

    private final Path file;
    private final TransactionProcessor txProcessor;

    public XmlDomFileReader(TransactionProcessor txProcessor, Path file) {
        this.file = file;
        this.txProcessor = txProcessor;
    }

    @Override
    public void readFile() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();

        try {
            System.out.println(Thread.currentThread().getName() + " - Reading file " + this.file);
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            var db = dbf.newDocumentBuilder();
            var doc = db.parse(file.toFile());
            doc.getDocumentElement().normalize();

            var transactionsNodeList = doc.getElementsByTagName("record");
            for (int i = 0; i < transactionsNodeList.getLength(); i++) {
                var transactionNode = transactionsNodeList.item(i);

                if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element transactionElement = (Element) transactionNode;

                    var reference = transactionElement.getAttribute("reference");
                    var startBalance = transactionElement.getElementsByTagName("startBalance").item(0).getTextContent();
                    var mutation = transactionElement.getElementsByTagName("mutation").item(0).getTextContent();
                    var endBalance = transactionElement.getElementsByTagName("endBalance").item(0).getTextContent();

                    var transaction = new Transaction(
                            Integer.valueOf(reference),
                            new BigDecimal(startBalance),
                            new BigDecimal(mutation),
                            new BigDecimal(endBalance),
                            new HashSet<>());

                    txProcessor.process(transaction);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
