package com.controller;

import com.storage.DataRepository;
import com.storage.FileUploader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class UploadController {

    DataRepository dataRepository;
    FileUploader fileUploader;

    public UploadController(final FileUploader fileUploader, final DataRepository dataRepository) {
        this.fileUploader=fileUploader;
        this.dataRepository = dataRepository;
    }

    @PostMapping("/fileUpload")
    public ModelAndView post$FileUpload(@RequestParam("jadFile") final MultipartFile jadFile, @RequestParam("opsFile") final MultipartFile opsFile) throws IOException {
        uploadJadFile(jadFile);
        uploadOpsFile(opsFile);
        ModelAndView model = new ModelAndView("index");
        return model;
    }

    @PostMapping("/resetData")
    public ModelAndView post$ResetFile(){
        dataRepository.getJadRepository().clear();
        dataRepository.getOpsRepository().clear();
        ModelAndView model = new ModelAndView("index");
        return model;
    }

    private ModelAndView getModelAndView() {
        return new ModelAndView("index");
    }


    private void uploadJadFile(final @RequestParam("file") MultipartFile file) throws IOException {
        fileUploader.uploadJad(new BufferedInputStream(file.getInputStream()));
    }
    private void uploadOpsFile(final @RequestParam("file") MultipartFile file) throws IOException {
        fileUploader.uploadOps(new BufferedInputStream(file.getInputStream()));
    }

}

