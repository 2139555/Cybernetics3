package com.example.witsonline;

public class RequestV {
    String studentFName;
    String studentLName;
    String courseName;
    String courseCode;
    String studentNumber;

    void setCourseName(String strCourse){ courseName = strCourse;}
    void setStudentFName(String strFName){ studentFName = strFName;}
    void setStudentLName(String strLName){ studentLName = strLName;}
    void setStudentNumber(String strNumber){ studentNumber = strNumber;}
    void setCourseCode(String strCourseCode){ courseCode = strCourseCode;}

    String getCourseName(){return courseName;}
    String getStudentFName(){return studentFName;}
    String getStudentLName(){return studentLName;}
    String getStudentNumber(){return studentNumber;}
    String getCourseCode(){return courseCode;}
}
