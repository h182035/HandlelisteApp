package com.example.handleliste;

import java.util.Objects;

public class Vare {
    private String navn;

    //1 is green, two is yellow and three is red
    //green means i have that item, yellow means i'm running low and red means empty
    private int status;

    public Vare(){
    }
    public Vare(String navn, int status){
        this.navn = navn;
        this.status = status;
    }
    public Vare(String navn){
        this.navn = navn;
        this.status = 1;
    }
    public void downGrade(){
        if(status == 3){
            status = 1;
        }else{
            status++;
        }
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public String toString(){
        return navn + " " + status;
    }

    @Override
    public boolean equals(Object o) {
        Vare a = (Vare) o;
        if(navn.equals(a.getNavn())){
            return true;
        }else{
            return false;
        }
    }

}
