package Model.Time;

import java.io.Serializable;

public class Time implements Serializable {
    private int hour;
    private int minuet;
    private int second;

    public Time(int hour, int minuet, int second) {
        setSecond(second);
        setMinuet(minuet);
        setHour(hour);
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setMinuet(int minuet) {
        this.minuet = minuet;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getSecond() {
        return second;
    }

    public int getMinuet() {
        return minuet;
    }

    public int getHour() {
        return hour;
    }

    public boolean isLater(Time time) {
        if (time.getHour() > this.hour) return false;
        if (time.getHour() < this.hour) return true;
        if (time.getMinuet() > this.minuet) return false;
        if (time.getMinuet() < this.getMinuet()) return true;
        if (time.getSecond() > this.second) return false;
        return true;
    }

    private void addSecond() {
        if (this.second < 59) {
            this.second++;
        } else {
            this.second = 0;
            if (this.minuet < 59) {
                this.minuet++;
            } else {
                this.minuet++;
                this.hour++;
            }
        }
    }

    private void decreaseSecond() {
        if (this.second > 0) {
            this.second--;
        } else {
            this.second = 59;
            if (this.minuet > 0) {
                this.minuet--;
            } else {
                this.minuet = 59;
                if (this.hour > 0) {
                    this.hour--;
                } else {
                    this.hour = 23;
                }
            }
        }
    }

    public Time timeBetweenTime(Time time) {
        int second = 0;
        int minuet = 0;
        int hour = 0;
        hour = this.hour - time.getHour();
        minuet = this.minuet - time.getMinuet();
        second = this.second - time.getSecond();
        if(hour<0){
            hour-=hour;
            minuet-=minuet;
            second-=second;
        }
        if (second < 0) {
            minuet--;
            second += 60;
        }
        if (minuet < 0) {
            hour--;
            minuet += 60;
        }
        return new Time(hour, minuet, second);
    }

    public Time addTime(Time time) {
        int hour=0;
        int minuet=0;
        int second=0;
        second=this.second+time.getSecond();
        minuet=this.minuet+time.getMinuet();
        hour=this.hour+time.getHour();
        if (second>=60){
            minuet++;
            second-=60;
        }
        if (minuet>=60){
            hour++;
            minuet-=60;
        }
        if (hour>=24){
            hour-=24;
        }
        return new Time(hour,minuet,second);
    }

    public long programTimeInSecond() {
        long inSec=0;
        inSec=this.second;
        inSec+=this.minuet*60;
        inSec+=this.hour*3600;
        return inSec;
    }

    public int programTimeInMinuet() {
        long programInSecond = this.programTimeInSecond();
        int programInMinuet = (int) (programInSecond / 60);
        return programInMinuet;
    }
}