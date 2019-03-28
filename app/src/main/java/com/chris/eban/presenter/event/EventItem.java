package com.chris.eban.presenter.event;

import android.os.Parcel;
import android.os.Parcelable;

public class EventItem implements Parcelable {
    public static final Creator<EventItem> CREATOR = new Creator<EventItem>() {
        @Override
        public EventItem createFromParcel(Parcel in) {
            return new EventItem(in);
        }

        @Override
        public EventItem[] newArray(int size) {
            return new EventItem[size];
        }
    };
    public String title;
    public String content;

    public EventItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public long id;

    public EventItem(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    protected EventItem(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
    }
}
