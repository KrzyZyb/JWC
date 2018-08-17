package com.controller;

import com.storage.DataRepository;
import com.storage.FileUploader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    DataRepository dataRepository;

    public HomeController(final DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @GetMapping("/")
    public String getIndexPage(Model model){
        dataRepository.getOpsRepository().clear();
        dataRepository.getJadRepository().clear();
        return "index";
    }
}
