package com.leyuan.aidong.entity.course;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseSeat {

    boolean need;// true/false,#是否需要选座
    int row;//  '行数',
    int col;//  '列数',
    ArrayList<String> removed;//  ["移除座位(行数_列数)"],
    ArrayList<String> reserved;//  ["保留座位(行数_列数)"],
    ArrayList<String> appointed;//  ["已预约座位(行数_列数)"]

    public boolean isNeed() {
        return need;
    }

    public void setNeed(boolean need) {
        this.need = need;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public ArrayList<String> getRemoved() {
        return removed;
    }

    public void setRemoved(ArrayList<String> removed) {
        this.removed = removed;
    }

    public ArrayList<String> getReserved() {
        return reserved;
    }

    public void setReserved(ArrayList<String> reserved) {
        this.reserved = reserved;
    }

    public ArrayList<String> getAppointed() {
        return appointed;
    }

    public void setAppointed(ArrayList<String> appointed) {
        this.appointed = appointed;
    }
}
