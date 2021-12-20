package cn.enaiun.ja.grater.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * @author Enaium
 */
public class JarFileUtil {
    public static List<JarFile> walkTree(String dir) {
        final List<JarFile> jarFiles = new ArrayList<>();
        try {
            Files.walkFileTree(new File(dir).toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toFile().getName().endsWith(".jar")) {
                        jarFiles.add(new JarFile(file.toFile()));
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jarFiles;
    }
}
