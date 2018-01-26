package com.leyuan.aidong.entity.data;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/23.
 */
public class DynamicRelateExtras {
    public String id;
    public String name;
    public String cover;
    public String start;
    public String landmark;
    public String view_count;
    private ArrayList<String> tags;
    public String hard_degree;


    private StringBuffer tagString = new StringBuffer();

    public StringBuffer getTagString() {
        if (tagString == null) {
            tagString = new StringBuffer();
        }
        if (tagString.length() == 0) {
            for (int i = 0; i < tags.size(); i++) {
                if (i < tags.size() - 1) {
                    tagString.append(tags.get(i)).append(" | ");
                } else {
                    tagString.append(tags.get(i));
                }

            }
        }
        return tagString;
    }
}
