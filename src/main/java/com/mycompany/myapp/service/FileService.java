package com.mycompany.myapp.service;

import com.mycompany.myapp.constant.FileType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    public static final String pathSymtoms = "symptom";
    public static final String pathMedicine = "medicine";
    public static final String pathCertificate = "certificate";

    static {
        File theDir = new File(pathSymtoms);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        File theDir1 = new File(pathMedicine);
        if (!theDir1.exists()) {
            theDir1.mkdirs();
        }

        File theDir2 = new File(pathCertificate);
        if (!theDir2.exists()) {
            theDir2.mkdirs();
        }
    }

    public String upload(MultipartFile file, String type, Long id) {
        Path root;
        String prefixName = "";
        if (FileType.MEDICINE.equals(type)) {
            root = Paths.get(pathMedicine);
            prefixName = pathMedicine;
        } else if (FileType.SYMPTOM.equals(type)) {
            root = Paths.get(pathSymtoms);
            prefixName = pathSymtoms;
        } else {
            root = Paths.get(pathCertificate);
            prefixName = pathCertificate;
        }
        String fileName = prefixName + "_" + id + ".png";
        try {
            Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        // save file
        // return path variable
    }

    public byte[] load(String filename, String type) throws IOException {
        if (FileType.MEDICINE.equals(type)) {
            filename = pathMedicine + File.separator + filename;
        } else if (FileType.SYMPTOM.equals(type)) {
            filename = pathSymtoms + File.separator + filename;
        } else {
            filename = pathCertificate + File.separator + filename;
        }
        return Files.readAllBytes(Paths.get(filename));
    }

    public void delete(String type, Long id) {
        String prefixName = "";
        if (FileType.MEDICINE.equals(type)) {
            prefixName = pathMedicine;
        } else if (FileType.SYMPTOM.equals(type)) {
            prefixName = pathSymtoms;
        } else {
            prefixName = pathCertificate;
        }
        File myObj = new File(prefixName + File.separator + prefixName + "_" + id + ".png");
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
