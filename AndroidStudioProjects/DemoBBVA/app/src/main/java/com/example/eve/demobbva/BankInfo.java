package com.example.eve.demobbva;

import java.util.ArrayList;

/**
 * Created by Eve on 8/14/17.
 */

public class BankInfo {

    String formatted_address, lat, lng,
        icon, id, name, place_id, types;

    public BankInfo(String formatted_address, String lat, String lng,
                    String icon, String id, String name,
                    String place_id, String types) {
        this.formatted_address = formatted_address;
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.place_id = place_id;
        this.types = types;
    }

    public String getFormatted_address() {
        return this.formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return this.place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getTypes() {
        return this.types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
