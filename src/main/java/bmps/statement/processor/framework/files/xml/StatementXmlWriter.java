package bmps.statement.processor.framework.files.xml;

import bmps.statement.processor.domain.Statement;
import bmps.statement.processor.domain.StatementWriter;
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
import java.util.List;

public class StatementXmlWriter implements StatementWriter {

    private final Path file;

    public StatementXmlWriter(Path file) {
        this.file = file;
    }

    @Override
    public void write(List<Statement> statements) {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            System.out.println(Thread.currentThread().getName() + " - Writing report for file " + this.file);

            Path reportPath = Path.of(this.file.getParent().toString(), "/report");
            Files.createDirectories(reportPath);

            try (FileOutputStream fos = new FileOutputStream(reportPath + "/records.xml")) {

                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();

                Element rootElement = doc.createElement("records");
                doc.appendChild(rootElement);

                for (Statement statement : statements) {
                    createTransactionNode(doc, rootElement, statement);
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

    private static void createTransactionNode(Document doc, Element rootElement, Statement statement) {
        Element recordElement = doc.createElement("record");
        rootElement.appendChild(recordElement);

        recordElement.setAttribute("reference", String.valueOf(statement.reference()));
        Element startBalanceElement = doc.createElement("startBalance");
        Element mutationElement = doc.createElement("mutation");
        Element endBalanceElement = doc.createElement("endBalance");
        Element issuesElement = doc.createElement("issues");

        startBalanceElement.setTextContent(statement.startBalance().toString());
        mutationElement.setTextContent(statement.mutation().toString());
        endBalanceElement.setTextContent(statement.endBalance().toString());

        recordElement.appendChild(startBalanceElement);
        recordElement.appendChild(mutationElement);
        recordElement.appendChild(endBalanceElement);
        recordElement.appendChild(issuesElement);

        statement.issues().forEach(e -> {
            Element issue = doc.createElement("issue");
            issue.setTextContent(e.toString());
            issuesElement.appendChild(issue);
        });
    }
}
