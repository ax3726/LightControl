package com.mf.lightcontrol.model.control;

public class RedModel {
    private int CommType;
    private IRMappingBean IRMapping;

    public IRMappingBean getIRMapping() {
        return IRMapping;
    }

    public void setIRMapping(IRMappingBean IRMapping) {
        this.IRMapping = IRMapping;
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public static class IRMappingBean {
        private int SensorID;
        private String SetType;
        private int MappLignt;

        public IRMappingBean(int sensorID, String setType, int mappLignt) {
            SensorID = sensorID;
            SetType = setType;
            MappLignt = mappLignt;
        }

        public int getSensorID() {
            return SensorID;
        }

        public void setSensorID(int sensorID) {
            SensorID = sensorID;
        }

        public String getSetType() {
            return SetType;
        }

        public void setSetType(String setType) {
            SetType = setType;
        }

        public int getMappLignt() {
            return MappLignt;
        }

        public void setMappLignt(int mappLignt) {
            MappLignt = mappLignt;
        }
    }
}
