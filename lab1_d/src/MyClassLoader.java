import javax.tools.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyClassLoader {

    private final Path sourceDir;
    private final Path classDir;
    private final Map<String, Long> lastModified = new ConcurrentHashMap<>();
    private final JavaCompiler compiler;
    private final StandardJavaFileManager fileManager;

    public MyClassLoader(String sourceDir, String classDir) {

        this.sourceDir = Paths.get(sourceDir).toAbsolutePath();
        this.classDir = Paths.get(classDir).toAbsolutePath();
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.fileManager = compiler.getStandardFileManager(null, null, null);

        try {
            Files.createDirectories(this.sourceDir);
            Files.createDirectories(this.classDir);

        } catch (IOException e) {
            throw new RuntimeException("Failed to create directories", e);

        }
    }

    public void start() {
        compileAndReload("TestModule.java");
        Thread watchThread = new Thread(this::watchForChanges, "FileWatcherThread");
        watchThread.setDaemon(true);
        watchThread.start();
        System.out.println("Live reloader started. Watching directory: " + sourceDir);

    }

    private void watchForChanges() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()){
            sourceDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        Path changed = sourceDir.resolve((Path) event.context());
                        String fileName = changed.getFileName().toString();

                        if (fileName.endsWith(".java")) {
                            compileAndReload(fileName);
                        }
                    }
                }
                if (!key.reset()) {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error in file watcher: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void compileAndReload(String fileName) {

        try {
            String className = fileName.replace(".java", "");
            Path sourceFile = sourceDir.resolve(fileName);
            Path classFile = classDir.resolve(className + ".class");
            long lastModTime = lastModified.getOrDefault(className, 0L);
            long currentModTime = Files.getLastModifiedTime(sourceFile).toMillis();

            if (Files.exists(classFile) && Files.getLastModifiedTime(classFile)
                    .toMillis() >= currentModTime && lastModTime >= currentModTime) {

                return;

            }


            boolean compilationSuccessful = compile(sourceFile);

            if (compilationSuccessful) {
                reloadClass(className);
                lastModified.put(className, currentModTime);
                System.out.println("Reloaded: " + className);

            } else {
                System.err.println("Compilation failed for: " + className);

            }

        } catch (Exception e) {
            System.err.println("Error in compile and reload: " + e.getMessage());
            e.printStackTrace();

        }

    }

    private boolean compile(Path sourceFile) {

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        Iterable<? extends JavaFileObject> compilationUnits = fileManager
                .getJavaFileObjectsFromFiles(Collections.singletonList(sourceFile.toFile()));
        List<String> options = Arrays.asList("-d", classDir.toString());
        JavaCompiler.CompilationTask task = compiler
                .getTask(null, fileManager, diagnostics, options, null, compilationUnits);
        boolean success = task.call();

        if (!success) {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.err.format("Error on line %d in %s%n", diagnostic.getLineNumber(),
                        diagnostic.getSource().toUri());
                System.err.println(diagnostic.getMessage(null));

            }
        }

        return success;
    }

    private void reloadClass(String className) {

        try {
            CustomClassLoader loader = new CustomClassLoader();
            Class<?> loadedClass = loader.loadClass(className);
            Object t = loadedClass.getDeclaredConstructor().newInstance();
            System.out.println(t);

        } catch (Exception e) {
            System.err.println("Error reloading class " + className + ": " + e.getMessage());
            e.printStackTrace();

        }

    }

    private class CustomClassLoader extends ClassLoader {

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            Path classFile = classDir.resolve(name + ".class");

            if (Files.exists(classFile)) {

                try {
                    byte[] classBytes = Files.readAllBytes(classFile);
                    return defineClass(name, classBytes, 0, classBytes.length);

                } catch (IOException e) {
                    throw new ClassNotFoundException("Error loading class " + name, e);

                }
            }
            return super.loadClass(name);
        }

    }

    public static void main(String[] args) {

        MyClassLoader reloader = new MyClassLoader("src", "out");
        reloader.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Live reloader is running. Press Enter to exit.");
            reader.readLine();

        } catch (IOException e) {
            System.err.println("Error reading user input: " + e.getMessage());

        }

        System.out.println("Exiting live reloader.");

    }

}