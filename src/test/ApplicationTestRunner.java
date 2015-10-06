package test;

import com.sun.org.apache.bcel.internal.util.ClassLoader;
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

    public static void main(String[] args) {
        ArrayList<Class<?>> classList = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(ApplicationTestRunner.class.getProtectionDomain().getCodeSource()
                    .getLocation().getPath() + TEST_DIRECTORY), new FileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    if (file.getFileName().toString().endsWith(CLASS)
                            && (!file.getFileName().toString().contains(ApplicationTestRunner.class.getSimpleName() + CLASS)
                            && !file.getFileName().toString().contains(SUITE_END_NAME) && !file.getFileName().toString().contains(_IGNORE))) {
                        try {

                       
                            classList.add(c);
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
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result result = JUnitCore.runClasses(Arrays.copyOfRange(classList.toArray(), 0, classList.size(), Class[].class));

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println();
        System.out.println(result.getFailureCount());
        System.out.println(result.wasSuccessful());
    }
}
