package com.controller;

import com.storage.JadUploader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class UploadController {

    private static final String WRONG_FILE_TYPE_MESSAGE = "Correct file type is not selected";
    private static final String MESSAGES_ATTRIBUTE = "messages";
    private static final String IS_SUCCESS_ATTRIBUTE = "isSuccess";

    JadUploader jadUploader;

    public UploadController(final JadUploader jadUploader) {
        this.jadUploader=jadUploader;
    }

    @GetMapping("/jadUpload")
    public ModelAndView get$JadUpload() {
        return getModelAndView();
    }

    @GetMapping("/opsUpload")
    public ModelAndView get$OpsUpload() {
        return getModelAndView();
    }

    @PostMapping("/jadUpload")
    public ModelAndView post$JadUpload(@RequestParam("file") final MultipartFile file) throws IOException {
        uploadJadFile(file);
        return getModelAndView();
    }

    private ModelAndView getModelAndView() {
        return new ModelAndView("index");
    }


    private void uploadJadFile(final @RequestParam("file") MultipartFile file) throws IOException {
        jadUploader.upload(new BufferedInputStream(file.getInputStream()));
    }

}

