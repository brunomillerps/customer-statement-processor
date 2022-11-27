package bmps.statement.processor.fileManager.xml;

import bmps.statement.processor.processors.TransactionProcessor;
import bmps.statement.processor.domain.Transaction;
import bmps.statement.processor.fileManager.FileWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlFileWriter implements FileWriter {

    private final TransactionProcessor txProcessor;
    private final Path file;

    public XmlFileWriter(TransactionProcessor txProcessor, Path path) {
        this.txProcessor = txProcessor;
        this.file = path;
    }

    @Override
    public void writeReport() {
        if (txProcessor.getViolations().size() == 0) {
            return;
        }

        System.out.println( Thread.currentThread().getName() + " - Writing report for file " + this.file);

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            Path reportPath = Path.of(this.file.getParent().toString(), "/report");
            Files.createDirectories(reportPath);

            try (FileOutputStream fos = new FileOutputStream(reportPath + "/records.xml")) {

                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();

                Element rootElement = doc.createElement("records");
                doc.appendChild(rootElement);

                for (Transaction tx : txProcessor.getViolations()) {
                    createTransactionNode(doc, rootElement, tx);
                }

                Transformer transformer = TransformerFactory.newInstance().newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(fos);

                transformer.transform(source, result);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while writing report for file " + file + " at /report");

            System.out.println(e);
        }
    }

    private static void createTransactionNode(Document doc, Element rootElement, Transaction tx) {
        Element transactionElement = doc.createElement("record");
        rootElement.appendChild(transactionElement);

        transactionElement.setAttribute("reference", String.valueOf(tx.reference()));
        Element startBalanceElement = doc.createElement("startBalance");
        Element mutationElement = doc.createElement("mutation");
        Element endBalanceElement = doc.createElement("endBalance");
        Element issuesElement = doc.createElement("issues");

        startBalanceElement.setTextContent(tx.startBalance().toString());
        mutationElement.setTextContent(tx.mutation().toString());
        endBalanceElement.setTextContent(tx.endBalance().toString());

        transactionElement.appendChild(startBalanceElement);
        transactionElement.appendChild(mutationElement);
        transactionElement.appendChild(endBalanceElement);
        transactionElement.appendChild(issuesElement);

        tx.issues().forEach(e -> {
            Element issue = doc.createElement("issue");
            issue.setTextContent(e.toString());
            issuesElement.appendChild(issue);
        });
    }
}
