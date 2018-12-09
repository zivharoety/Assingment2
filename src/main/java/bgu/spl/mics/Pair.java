package bgu.spl.mics;

public class Pair {
    private String first;
    private int second;

    public Pair(String name, int num){
        first = name;
        this.second = num;
    }

    public String getFirst(){
        return first;
    }

    public int getSecond(){
        return second;
    }

}
