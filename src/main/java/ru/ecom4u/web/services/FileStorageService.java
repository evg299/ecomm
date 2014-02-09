package ru.ecom4u.web.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Random;

@Service
public class FileStorageService {

    @Value("#{properties['fs.dir_path']}")
    private String fileStorageDir;

    @PostConstruct
    private void init() {
        new File(fileStorageDir).mkdirs();
    }

    public File getFile(String fullName) {
        File fsSub = getFile4Level(fullName);

        if (fsSub.exists())
            return fsSub;
        else
            return null;
    }

    public File createFile(String fullName) {
        return getFile4Level(fullName);
    }

    public synchronized void saveFileWithName(InputStream is, String name) throws IOException {
        File fsSub = getFile4Level(name);

        OutputStream os = new FileOutputStream(fsSub);
        int read = 0;
        byte[] bufer = new byte[1024];
        while ((read = is.read(bufer)) != -1) {
            os.write(bufer, 0, read);
        }
        os.close();
    }

    public synchronized String saveFile(InputStream is, String extention) throws IOException {
        return saveFile(is, "", extention);
    }

    public synchronized String saveFile(InputStream is, String suffix, String extention) throws IOException {
        String name = generateName();
        String fullName = name + suffix + extention;
        saveFileWithName(is, fullName);
        return fullName;
    }

    public synchronized boolean deleteFile(String fullName) {
        File fsSub = getFile4Level(fullName);
        return fsSub.delete();
    }

    private File getFile4Level(String fullName) {
        File fsDir = new File(fileStorageDir);
        File fsSub1 = new File(fsDir, fullName.substring(0, 1));
        File fsSub2 = new File(fsSub1, fullName.substring(1, 2));
        File fsSub3 = new File(fsSub2, fullName.substring(2, 3));
        File fsSub4 = new File(fsSub3, fullName.substring(3, 4));
        fsSub4.mkdirs();

        return new File(fsSub4, fullName);
    }

    public String generateName() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < 32; i++) {
            char c = (char) (65 + random.nextInt(25));
            buffer.append(c);
        }

        return buffer.toString();
    }

    public String extractExtention(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }
}
