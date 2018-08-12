package com.controller;

import com.core.Waypoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WaypointController {

    @RequestMapping("/api/jadwaypoints")
    public Waypoint getJadWaypoints(){
        return new Waypoint("Some","Testing","Waypoint","Data","Postman");
    }
}
