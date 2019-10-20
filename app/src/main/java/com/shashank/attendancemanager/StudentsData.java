package com.shashank.attendancemanager;

public class StudentsData {
    private String mName;
    private String mSubject;
    private String mLecturesAttended;
    private String mTotalLectures;
    private String mAttendancePercent;

    public StudentsData() {

    }

    public StudentsData(String mName, String mSubject) {
        this.mName = mName;
        this.mSubject = mSubject;
        mLecturesAttended="0";
        mTotalLectures="0";
        mAttendancePercent="0";
    }

    public StudentsData(String mName, String mLecturesAttended, String mTotalLectures, String mAttendancePercent) {
        this.mName = mName;
        this.mLecturesAttended = mLecturesAttended;
        this.mTotalLectures = mTotalLectures;
        this.mAttendancePercent = mAttendancePercent;
    }

    public String getmAttendancePercent() {
        return  mAttendancePercent;
    }

    public void setmAttendancePercent(String mAttendancePercent) {
        this.mAttendancePercent = mAttendancePercent;
    }

    public String getmLecturesAttended() {
        return mLecturesAttended;
    }

    public void setmLecturesAttended(String mLecturesAttended) {
        this.mLecturesAttended = mLecturesAttended;
    }

    public String getmTotalLectures() {
        return mTotalLectures;
    }

    public void setmTotalLectures(String mTotalLectures) {
        this.mTotalLectures = mTotalLectures;
    }

    public String getmName() {
        return mName;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }
}
