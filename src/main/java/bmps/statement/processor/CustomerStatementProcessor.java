package bmps.statement.processor;

import bmps.statement.processor.application.DuplicateStatementsProcessorDecorator;
import bmps.statement.processor.application.SingleCustomerStatementService;
import bmps.statement.processor.domain.Statement;
import bmps.statement.processor.domain.rules.BalanceMatchHandler;
import bmps.statement.processor.domain.rules.RuleHandler;
import bmps.statement.processor.framework.files.FileFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CustomerStatementProcessor {

    // All checks are linked. Client can build various chains using the same
    // components.
    private static final RuleHandler processors = RuleHandler.link(
            new BalanceMatchHandler()
    );

    public static void main(String[] args) throws IOException {


        var filePathOptional = Arrays.stream(args).filter(arg -> arg.startsWith("filePath")).findFirst();

        if (filePathOptional.isEmpty()) {
            System.out.println("No filePath argument found!");
            System.exit(-1);
        }

        var path = filePathOptional.get().split("filePath=")[1];
        System.out.println("Reading files from " + path);
        try (var rootDir = Files.list(Path.of(path))) {
            rootDir.filter(Files::isRegularFile)
                    .parallel()
                    .forEach(CustomerStatementProcessor::process);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void process(Path filePath) {
        List<Statement> statementsWithIssues = readAndProcessStatements(filePath);
        writeStatementsWithIssues(filePath, statementsWithIssues);
    }

    private static void writeStatementsWithIssues(Path filePath, List<Statement> statementsWithIssues) {
        if (statementsWithIssues.size() > 0) {
            FileFactory.getFileWriter(filePath)
                    .write(statementsWithIssues);
        } else {
            System.out.println("No issue found for file " + filePath);
        }
    }

    private static List<Statement> readAndProcessStatements(Path filePath) {

        var service = new DuplicateStatementsProcessorDecorator(
                new SingleCustomerStatementService(FileFactory.getFileReader(filePath), processors)
        );

        return service.execute()
                .stream()
                .filter(s -> s.issues().size() > 0)
                .toList();
    }
}