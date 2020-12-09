package com.wolfsea.newaidlipcdemo.bean;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author liuliheng
 * @desc  实体类
 * @time 2020/11/30  18:36
 **/
public class MyData implements Parcelable {

    private int data1;
    private int data2;

    public MyData() {}

    protected MyData(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<MyData> CREATOR = new Creator<MyData>() {
        @Override
        public MyData createFromParcel(Parcel in) {
            return new MyData(in);
        }

        @Override
        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *@desc 将数据写入到Parcel
     *@author:liuliheng
     *@time: 2020/11/30 18:40
    **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(data1);
        dest.writeInt(data2);
    }

    @Override
    public String toString() {
        return "com.wolfsea.newaidlipcdemo.bean.MyData{" +
                "data1=" + data1 +
                ", data2=" + data2 +
                '}';
    }

    /**
     *@desc 从Parcel中读取数据
     *@author:liuliheng
     *@time: 2020/11/30 18:39
    **/
    public void readFromParcel(Parcel parcel) {

        data1 = parcel.readInt();
        data2 = parcel.readInt();
    }

    public int getData1() {
        return data1;
    }

    public void setData1(int data1) {
        this.data1 = data1;
    }

    public int getData2() {
        return data2;
    }

    public void setData2(int data2) {
        this.data2 = data2;
    }
}
