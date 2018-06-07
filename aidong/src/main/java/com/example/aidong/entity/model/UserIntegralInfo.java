package com.example.aidong.entity.model;

import java.util.ArrayList;

public class UserIntegralInfo {

    private int has;
    private int processing;
    private ArrayList<IntegralDetailInfo> log;

    public int getHas() {
        return has;
    }

    public void setHas(int has) {
        this.has = has;
    }

    public int getProcess() {
        return processing;
    }

    public void setProcess(int process) {
        this.processing = process;
    }

    public ArrayList<IntegralDetailInfo> getLog() {
        return log;
    }

    public void setLog(ArrayList<IntegralDetailInfo> log) {
        this.log = log;
    }
}
