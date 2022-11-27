package bmps.statement.processor;

import bmps.statement.processor.fileManager.FileManager;
import bmps.statement.processor.fileManager.FileManagerLookup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class CustomerStatementProcessor {
    public static void main(String[] args) {

        Optional<String> rootDir = Arrays.stream(args)
                .filter(arg -> arg.startsWith("inputFile"))
                .findFirst()
                .or(() -> Optional.of("/home/brunomiller/work/customer-statement-validator/src/main/resources/data"));

        if (rootDir.isEmpty()) {
            System.out.println("Please provide an absolute path to argument inputFile");
            return;
        }

        List<FileManager> fileManagers;

        try (Stream<Path> stream = Files.list(Paths.get(rootDir.get()))) {
            fileManagers = stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(FileManagerLookup.isCsvFile.or(FileManagerLookup.isXmlFile))
                    .map(FileManagerLookup::getFileManager)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fileManagers.parallelStream().forEach(FileManager::execute);
    }
}