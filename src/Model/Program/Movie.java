package Model.Program;

import Model.Time.Date;
import Model.Time.Time;

import Model.Person.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie extends Program implements Serializable {
    public Movie(String name,Time startTime,Time endTime,Time replayTimeStart,Time programTime,Date date,Date replyDate){
        super(name, startTime, endTime, programTime,date);
        setReplayTimeStart(replayTimeStart);
        setReplyDate(replyDate);
    }

    public Movie(String name,Time startTime,Time endTime,Time replayTimeStart,Date date,Date replyDate){
        super(name, startTime, endTime,date,replyDate);
        Time programTime =endTime.timeBetweenTime(startTime);
        this.setProgramTime(programTime);
        setReplayTimeStart(replayTimeStart);
        setReplyDate(replyDate);
    }

    public Movie(String name, Time startTime, Time endTime, Time replayTimeStart
            , ArrayList<Integer>votes,Date date,Date replyDate){
        super(name, startTime, endTime,date,replyDate);
        Time programTime =endTime.timeBetweenTime(startTime);
        this.setProgramTime(programTime);
        this.setRates(votes);
        setReplayTimeStart(replayTimeStart);
        calculateAverage();
        setReplyDate(replyDate);
    }

    public Movie(String name,Time startTime,Time endTime,Time replayTimeStart
            ,Time programTime,ArrayList<Integer>votes,Date date,Date replyDate){
        super(name, startTime, endTime, programTime,date);
        this.setRates(votes);
        setReplayTimeStart(replayTimeStart);
        calculateAverage();
        setReplyDate(replyDate);
    }

    public void setReplayTimeStart(Time replayTimeStart) {
        this.replayTimeStart = replayTimeStart;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public Time getReplayTimeStart() {
        return replayTimeStart;
    }

    private void calculateAverage(){
        long sum =0;
        int number =0;
        for (int i = 0; i < this.rates.size(); i++) {
            if(this.rates.get(i) != null){
                number =(int)this.rates.get(i);
                sum +=number;
            }
        }
        double avrege =(((double)sum) / ((double) this.rates.size()));
        this.rate =avrege;
    }

    public void vote(User user,int number)throws Exception{
        for (int i = 0; i < this.users.size(); i++) {
            if((this.users.get(i) == null)||(this.users.get(i).equals(user))){
                throw new Exception("USER HAS VOTED FOR THIS PROGRAM!");
            }
        }
        this.users.add(user);
        Integer num = new Integer(number);
        this.rates.add(num);
        this.calculateAverage();
    }

    public Time EndOfReplyTime(){
        return this.startTime.addTime(this.programTime);
    }

    public Date betweenReply(){
        return this.replyDate.betweenDate(this.date);
    }
}