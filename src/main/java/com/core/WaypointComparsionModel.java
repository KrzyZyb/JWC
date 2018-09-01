package com.core;

public class WaypointComparsionModel {
    private String countryOPS;
    private String ICAOOPS;
    private String WPT_idOPS;
    private String latitudeOPS;
    private String longitudeOPS;
    private String longxlatiOPS;
    private String countryJAD;
    private String ICAOJAD;
    private String WPT_idJAD;
    private String latitudeJAD;
    private String longitudeJAD;
    private String longxlatiJAD;
    private String status="";

    /*
     * Constructor for waypoints which country name is made of two parts (for example: Poland, republic of)
     * @param country - String with first part of country name
     * @param country2 - String with second part of coutry name
     * @param ICAO - Operators code String
     * @param WPT_id Particular Waypoint id, that is unique (can not be repeated in whole continent)
     * @param latitude - String with latitude data
     * @param longitude - String with longitude data
     * @return a valid Waypoint object with two segments countryname
     */

    public WaypointComparsionModel(String countryOPS,
                                   String countryJAD,
                                   String countryOPS2,
                                   String countryJAD2,
                                   String ICAOOPS,
                                   String ICAOJAD,
                                   String WPT_idOPS,
                                   String WPT_idJAD,
                                   String latitudeOPS,
                                   String latitudeJAD,
                                   String longitudeOPS,
                                   String longitudeJAD){
        this.countryOPS=(countryOPS.concat(",").concat(countryOPS2));
        this.countryJAD=(countryJAD.concat(",").concat(countryJAD2));
        this.ICAOOPS=ICAOOPS;
        this.countryJAD=ICAOJAD;
        this.WPT_idOPS=WPT_idOPS;
        this.WPT_idJAD=WPT_idJAD;
        this.latitudeOPS=latitudeOPS;
        this.latitudeJAD=latitudeJAD;
        this.longitudeOPS=longitudeOPS;
        this.longitudeJAD=longitudeJAD;
        this.longxlatiOPS=latitudeOPS.concat(longitudeOPS);
        this.longitudeJAD=latitudeJAD.concat((longitudeJAD));
    }

    /*
     * Constructor for waypoints which country name is made of one part (for example: Russia)
     * @param country - String with first (and only) part of country name
     * @param ICAO - Operators code String
     * @param WPT_id Particular Waypoint id, that is unique (can not be repeated in whole continent)
     * @param latitude - String with latitude data
     * @param longitude - String with longitude data
     * @return a valid Waypoint object with two segments countryname
     */

    public WaypointComparsionModel(String countryOPS,
                                   String countryJAD,
                                   String ICAOOPS,
                                   String ICAOJAD,
                                   String WPT_idOPS,
                                   String WPT_idJAD,
                                   String latitudeOPS,
                                   String latitudeJAD,
                                   String longitudeOPS,
                                   String longitudeJAD){
        this.countryOPS='\u0022'+countryOPS+'\u0022';
        this.countryJAD='\u0022'+countryJAD+'\u0022';
        this.ICAOOPS=ICAOOPS;
        this.countryJAD=ICAOJAD;
        this.WPT_idOPS=WPT_idOPS;
        this.WPT_idJAD=WPT_idJAD;
        this.latitudeOPS=latitudeOPS;
        this.latitudeJAD=latitudeJAD;
        this.longitudeOPS=longitudeOPS;
        this.longitudeJAD=longitudeJAD;
        this.longxlatiOPS=latitudeOPS.concat(longitudeOPS);
        this.longitudeJAD=latitudeJAD.concat((longitudeJAD));
    }
    public WaypointComparsionModel(){}


    public String getCountryOPS() {
        return countryOPS;
    }

    public void setCountryOPS(String countryOPS) {
        this.countryOPS = countryOPS;
    }

    public String getICAOOPS() {
        return ICAOOPS;
    }

    public void setICAOOPS(String ICAOOPS) {
        this.ICAOOPS = ICAOOPS;
    }

    public String getWPT_idOPS() {
        return WPT_idOPS;
    }

    public void setWPT_idOPS(String WPT_idOPS) {
        this.WPT_idOPS = WPT_idOPS;
    }

    public String getLatitudeOPS() {
        return latitudeOPS;
    }

    public void setLatitudeOPS(String latitudeOPS) {
        this.latitudeOPS = latitudeOPS;
    }

    public String getLongitudeOPS() {
        return longitudeOPS;
    }

    public void setLongitudeOPS(String longitudeOPS) {
        this.longitudeOPS = longitudeOPS;
    }

    public String getLongxlatiOPS() {
        return longxlatiOPS;
    }

    public void setLongxlatiOPS(String longxlatiOPS) {
        this.longxlatiOPS = longxlatiOPS;
    }

    public String getCountryJAD() {
        return countryJAD;
    }

    public void setCountryJAD(String countryJAD) {
        this.countryJAD = countryJAD;
    }

    public String getICAOJAD() {
        return ICAOJAD;
    }

    public void setICAOJAD(String ICAOJAD) {
        this.ICAOJAD = ICAOJAD;
    }

    public String getWPT_idJAD() {
        return WPT_idJAD;
    }

    public void setWPT_idJAD(String WPT_idJAD) {
        this.WPT_idJAD = WPT_idJAD;
    }

    public String getLatitudeJAD() {
        return latitudeJAD;
    }

    public void setLatitudeJAD(String latitudeJAD) {
        this.latitudeJAD = latitudeJAD;
    }

    public String getLongitudeJAD() {
        return longitudeJAD;
    }

    public void setLongitudeJAD(String longitudeJAD) {
        this.longitudeJAD = longitudeJAD;
    }

    public String getLongxlatiJAD() {
        return longxlatiJAD;
    }

    public void setLongxlatiJAD(String longxlatiJAD) {
        this.longxlatiJAD = longxlatiJAD;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
