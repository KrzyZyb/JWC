package com.storage;

import com.core.Waypoint;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataRepository {
    public DataRepository() {
        jadRepository= new ArrayList<>();
        opsRepository= new ArrayList<>();
    }
    private List<Waypoint> jadRepository;
    private List<Waypoint> opsRepository;

    void saveJad(Waypoint waypoint){
        jadRepository.add(waypoint);
    }
    void saveOps(Waypoint waypoint){
        opsRepository.add(waypoint);
    }

    public List<Waypoint> getJadRepository(){
        return jadRepository;
    }
    public List<Waypoint> getOpsRepository(){
        return opsRepository;
    }
}
