package com.qazlearn.a1.qazlearn;

/**
 * Created by 1 on 07.04.2017.
 */

public class Extra {
    private int id;
    private String kazakh;
    private String russian;
    private String english;
    private String uri;
    public Extra(int id, String kazakh, String russian, String english, String uri) {
        this.id = id;
        this.uri = uri;
        this.kazakh = kazakh;
        this.russian = russian;
        this.english = english;
    }

    public int getId() {
        return id;
    }
    public String getUri(){return uri;}
    public void setUri(String uri){
        this.uri = uri;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getKazakh() {
        return kazakh;
    }
    public void setKazakh(String kazakh) {
        this.kazakh = kazakh;
    }
    public String getRussian() {
        return russian;
    }
    public void setRussian(String russian) {
        this.russian= russian;
    }
    public String getEnglish() {
        return english;
    }
    public void setEnglish(String english) {
        this.english = english;
    }
}
