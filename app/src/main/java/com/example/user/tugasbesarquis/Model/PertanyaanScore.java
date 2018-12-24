package com.example.user.tugasbesarquis.Model;

public class PertanyaanScore {
    private String Pertanyaan_Score;
    private String User;
    private String Score;

    public PertanyaanScore() {
    }

    public PertanyaanScore(String pertanyaan_Score, String user, String score) {
        Pertanyaan_Score = pertanyaan_Score;
        User = user;
        Score = score;
    }

    public String getPertanyaan_Score() {
        return Pertanyaan_Score;
    }

    public void setPertanyaan_Score(String pertanyaan_Score) {
        Pertanyaan_Score = pertanyaan_Score;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
