package Model.Program;

import Model.Time.Date;
import Model.Time.Time;

import Model.Person.Presenter;

import Model.Person.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Entertainment extends Program implements Serializable {
    private Presenter presenter;

    public Entertainment(String name, Time startTime, Time endTime, Time replayTimeStart
            , Time programTime, Presenter presenter, Date date,Date replyDate){
        super(name, startTime, endTime, programTime,date);
        setReplayTimeStart(replayTimeStart);
        setPresenter(presenter);
        setReplyDate(replyDate);
    }

    public Entertainment(String name, Time startTime, Time endTime
                        , Time replayTimeStart,Presenter presenter,Date date,Date replyDate){
        super(name, startTime, endTime,date,replyDate);
        Time programTime =endTime.timeBetweenTime(startTime);
        this.setProgramTime(programTime);
        setPresenter(presenter);
        setReplayTimeStart(replayTimeStart);
        setReplyDate(replyDate);
    }

    public Entertainment(String name, Time startTime, Time endTime, Time replayTimeStart
            , Presenter presenter,ArrayList<Integer> votes,Date date,Date replyDate){
        super(name, startTime, endTime,date,replyDate);
        Time programTime =endTime.timeBetweenTime(startTime);
        this.setProgramTime(programTime);
        this.setRates(votes);
        setReplayTimeStart(replayTimeStart);
        setPresenter(presenter);
        calculateAverage();
        setReplyDate(replyDate);
    }

    public Entertainment(String name,Time startTime,Time endTime,Time replayTimeStart,Time programTime
            ,Presenter presenter,ArrayList<Integer>votes,Date date,Date replyDate){
        super(name, startTime, endTime, programTime,date);
        this.setRates(votes);
        setReplayTimeStart(replayTimeStart);
        setPresenter(presenter);
        calculateAverage();
        setReplyDate(replyDate);
    }

    public void setReplayTimeStart(Time replayTimeStart) {
        this.replayTimeStart = replayTimeStart;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public Time getReplayTimeStart() {
        return replayTimeStart;
    }

    public Date getReplyDate() {
        return replyDate;
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

/*    public Date dateBetweenReply(){
        return this.replyDate.betweenDate(this.date);
    }*/

    public Time EndOfReplyTime(){
        return this.replayTimeStart.addTime(this.programTime);
    }
}