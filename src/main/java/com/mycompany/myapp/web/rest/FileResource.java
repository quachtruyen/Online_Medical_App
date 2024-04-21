package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.constant.FileType;
import com.mycompany.myapp.service.DailyHealthStatusService;
import com.mycompany.myapp.service.FileService;
import com.mycompany.myapp.service.MedicineService;
import com.mycompany.myapp.service.UserService;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileResource {

    private final FileService fileService;

    private final MedicineService medicineService;

    private final UserService userService;

    private final DailyHealthStatusService dailyHealthStatusService;

    public FileResource(
        FileService fileService,
        MedicineService medicineService,
        UserService userService,
        DailyHealthStatusService dailyHealthStatusService
    ) {
        this.fileService = fileService;
        this.medicineService = medicineService;
        this.userService = userService;
        this.dailyHealthStatusService = dailyHealthStatusService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String type, @RequestParam Long id) {
        try {
            String fileName = fileService.upload(file, type, id);
            if (FileType.MEDICINE.equals(type)) {
                medicineService.createOrUpdateImage(id, fileName);
            } else if (FileType.SYMPTOM.equals(type)) {
                dailyHealthStatusService.createOrUpdateImageSymptom(id, fileName);
            } else {
                userService.createOrUpdateImageCertificate(id, fileName);
            }

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFile(@RequestParam String type, @PathVariable Long id) {
        fileService.delete(type, id);
    }

    @GetMapping("/{filePath}")
    public byte[] getFileByName(@RequestParam String type, @PathVariable String filePath) throws IOException {
        return fileService.load(filePath, type);
    }
}
