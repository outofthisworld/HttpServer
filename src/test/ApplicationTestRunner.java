package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daleappleby on 6/10/15.
 */
public class ApplicationTestRunner {
    private static final String SUITE_END_NAME = "Suite";
    private static final String CLASS = ".class";
    private static final String _IGNORE = "$";
    private static final String TEST_DIRECTORY = "test";
    private static final String PACKAGE_SEP = ".";
    private static StringBuffer currentDir = new StringBuffer();

    public static void main(String[] args) {
        TestClassVisitor testClassVisitor = new TestClassVisitor();
        try {
            Path path = Paths.get(ApplicationTestRunner.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath() + TEST_DIRECTORY);
            Files.walkFileTree(path, );
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result result = JUnitCore.runClasses(
                Arrays.copyOfRange(testClassVisitor.classList.toArray(), 0, testClassVisitor.classList.size(), Class[].class)
        );

        System.out.println("Ran: " + result.getRunCount() + " tests");
        System.out.println("Returned : " + result.getFailureCount() + " failures");
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Was successful: " + result.wasSuccessful());
    }

    private static class TestClassVisitor implements FileVisitor<Path> {
        private final ArrayList<Class<?>> classList = new ArrayList<>();

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            currentDir.append(dir.getFileName() + PACKAGE_SEP);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

            if (file.getFileName().toString().endsWith(CLASS)
                    && (!file.getFileName().toString().contains(ApplicationTestRunner.class.getSimpleName() + CLASS)
                    && !file.getFileName().toString().contains(SUITE_END_NAME) && !file.getFileName().toString().contains(_IGNORE))) {
                try {
                    String klazz = file.getFileName().toString().replace(".class", "");
                    classList.add(Class.forName(currentDir + klazz));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            currentDir.append(currentDir.toString().replaceAll(dir.getFileName() + PACKAGE_SEP, ""));
            return FileVisitResult.CONTINUE;
        }
    }
}
