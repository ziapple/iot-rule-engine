package com.casic.iot.db;

import java.util.List;

/**
 * 时序数据类
 */
public class SerialData{
    private String iot;
    private String equipment;
    private List data;

    public String getIot() {
        return iot;
    }

    public void setIot(String iot) {
        this.iot = iot;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}