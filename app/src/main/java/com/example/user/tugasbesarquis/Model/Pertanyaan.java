package com.example.user.tugasbesarquis.Model;

public class Pertanyaan {
    private String Jawaban,JawabanA,JawabanB,JawabanC,JawabanD,JawabanBenar,CategorId,IsImageQuestion;

    public Pertanyaan() {

    }
    public Pertanyaan(String jawaban, String jawabanA, String jawabanB, String jawabanC, String jawabanD, String jawabanBenar, String categorId, String isImageQuestion) {
        Jawaban = jawaban;
        JawabanA = jawabanA;
        JawabanB = jawabanB;
        JawabanC = jawabanC;
        JawabanD = jawabanD;
        JawabanBenar = jawabanBenar;
        CategorId = categorId;
        IsImageQuestion = isImageQuestion;
    }

    public String getJawaban() {
        return Jawaban;
    }

    public void setJawaban(String jawaban) {
        Jawaban = jawaban;
    }

    public String getJawabanA() {
        return JawabanA;
    }

    public void setJawabanA(String jawabanA) {
        JawabanA = jawabanA;
    }

    public String getJawabanB() {
        return JawabanB;
    }

    public void setJawabanB(String jawabanB) {
        JawabanB = jawabanB;
    }

    public String getJawabanC() {
        return JawabanC;
    }

    public void setJawabanC(String jawabanC) {
        JawabanC = jawabanC;
    }

    public String getJawabanD() {
        return JawabanD;
    }

    public void setJawabanD(String jawabanD) {
        JawabanD = jawabanD;
    }

    public String getJawabanBenar() {
        return JawabanBenar;
    }

    public void setJawabanBenar(String jawabanBenar) {
        JawabanBenar = jawabanBenar;
    }

    public String getCategorId() {
        return CategorId;
    }

    public void setCategorId(String categorId) {
        CategorId = categorId;
    }

    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        IsImageQuestion = isImageQuestion;
    }
}
