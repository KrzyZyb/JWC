package com.controller;

import com.core.WaypointDataComparator;
import com.storage.DataRepository;
import com.storage.FileUploader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private DataRepository dataRepository;
    private WaypointDataComparator waypointDataComparator;

    public HomeController(final DataRepository dataRepository, final WaypointDataComparator waypointDataComparator) {
        this.dataRepository = dataRepository;
        this.waypointDataComparator=waypointDataComparator;
    }

    @GetMapping("/")
    public String getIndexPage(Model model){
        dataRepository.getOpsRepository().clear();
        dataRepository.getJadRepository().clear();
        waypointDataComparator.getWaypointChanges().clear();

        return "index";
    }
}
