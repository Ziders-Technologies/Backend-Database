package Datastore.Datastore;

import java.io.File;

public class FileClass {
    private File file;
    private boolean result;

    public FileClass(File file, boolean result) {
        this.file = file;
        this.result = result;
    }

    public FileClass() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
