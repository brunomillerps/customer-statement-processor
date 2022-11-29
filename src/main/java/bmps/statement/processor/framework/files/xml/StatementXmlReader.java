package bmps.statement.processor.framework.files.xml;

import bmps.statement.processor.domain.Statement;
import bmps.statement.processor.domain.StatementReader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * Defines a reader API that obtain a SAX based parser to parse XML documents
 */
public class StatementXmlReader implements StatementReader {
    private final Path file;

    public StatementXmlReader(Path file) {
        this.file = file;
    }

    @Override
    public Stream<Statement> read() throws IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();

        List<Statement> statements = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser saxParser = factory.newSAXParser();

            saxParser.parse(new FileInputStream(this.file.toFile()), new DefaultHandler());

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

                    var statement = new Statement(
                            Integer.valueOf(reference),
                            new BigDecimal(startBalance),
                            new BigDecimal(mutation),
                            new BigDecimal(endBalance),
                            new HashSet<>());

                    statements.add(statement);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return statements.stream();
    }
}
