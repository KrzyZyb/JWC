package com.controller;

import com.core.WaypointDataComparator;
import com.storage.DataRepository;
import com.storage.FileUploader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedInputStream;
import java.io.IOException;

@Controller
public class HomeController {

    private DataRepository dataRepository;
    private WaypointDataComparator waypointDataComparator;
    FileUploader fileUploader;

    public HomeController(final DataRepository dataRepository, final WaypointDataComparator waypointDataComparator, final FileUploader fileUploader) {
        this.dataRepository = dataRepository;
        this.waypointDataComparator=waypointDataComparator;
        this.fileUploader=fileUploader;
    }

    @GetMapping("/")
    public String getIndexPage(Model model){
        clearPreviousUploadData();
        return "index";
    }

    @PostMapping("/")
    public ModelAndView post$FileUpload(@RequestParam("jadFile") final MultipartFile jadFile, @RequestParam("opsFile") final MultipartFile opsFile) throws IOException {
        clearPreviousUploadData();
        uploadJadFile(jadFile);
        uploadOpsFile(opsFile);
        waypointDataComparator.compareWaypoints();

        ModelAndView model = new ModelAndView("index");
        model.addObject("opsCount", dataRepository.getOpsRepository().size());
        model.addObject("jadCount", dataRepository.getJadRepository().size());
        model.addObject("raportCount", waypointDataComparator.getWaypointChanges().size());
        model.addObject("fullRaportCount", waypointDataComparator.getWaypointChangesFull().size());
        return model;
    }
    @PostMapping("/resetData")
    public ModelAndView post$ResetFile(){
        clearPreviousUploadData();
        ModelAndView model = new ModelAndView("index");
        return model;
    }

    private void uploadJadFile(final @RequestParam("file") MultipartFile file) throws IOException {
        fileUploader.uploadJad(new BufferedInputStream(file.getInputStream()));
    }
    private void uploadOpsFile(final @RequestParam("file") MultipartFile file) throws IOException {
        fileUploader.uploadOps(new BufferedInputStream(file.getInputStream()));
    }
    private void clearPreviousUploadData(){
        dataRepository.getOpsRepository().clear();
        dataRepository.getJadRepository().clear();
        waypointDataComparator.getWaypointChanges().clear();
        waypointDataComparator.getWaypointChangesFull().clear();
    }
}
