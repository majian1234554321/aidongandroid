/*
 *  Copyright (C) 2017 Bilibili
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.aidong.module.photopicker.boxing.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The base entity for media.
 *
 * @author ChenSL
 */
public abstract class BaseMedia implements Parcelable {
    public enum TYPE {
        IMAGE, VIDEO
    }

    protected String path;
    protected String size;
    protected String id;


    public BaseMedia() {
    }

    public BaseMedia(String id, String path) {
        this.id = id;
        this.path = path;
    }

    public abstract TYPE getType();

    public String getId() {
        return id;
    }

    public long getSize() {
        try {
            long result = Long.valueOf(size);
            return result > 0 ? result : 0;
        }catch (NumberFormatException size) {
            return 0;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public String getPath(){
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.id);
        dest.writeString(this.size);
    }

    protected BaseMedia(Parcel in) {
        this.path = in.readString();
        this.id = in.readString();
        this.size = in.readString();
    }

}
