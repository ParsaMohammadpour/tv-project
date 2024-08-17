package Model.Program;

import Model.Time.Date;
import Model.Time.Time;

import Model.Person.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Series extends Program implements Serializable {
    private int episode;
    private int session;


    public Series(String name, Time startTime, Time endTime, Time replayTimeStart
            , Time programTime, int session, int episode, Date date,Date replyDate){
        super(name, startTime, endTime, programTime,date);
        setReplayTimeStart(replayTimeStart);
        setEpisode(episode);
        setSession(session);
        setReplyDate(replyDate);
    }

    public Series(String name,Time startTime,Time endTime,Time replayTimeStart
            ,int session,int episode,Date date,Date replyDate){
        super(name, startTime, endTime,date,replyDate);
        Time programTime =endTime.timeBetweenTime(startTime);
        this.setProgramTime(programTime);
        setReplayTimeStart(replayTimeStart);
        setSession(session);
        setEpisode(episode);
        setReplyDate(replyDate);
    }

    public Series(String name, Time startTime, Time endTime, Time replayTimeStart
            , ArrayList<Integer> votes,int session,int episode,Date date,Date replyDate){
        super(name, startTime, endTime,date,replyDate);
        Time programTime =endTime.timeBetweenTime(startTime);
        this.setProgramTime(programTime);
        this.setRates(votes);
        setReplayTimeStart(replayTimeStart);
        calculateAverage();
        setEpisode(episode);
        setSession(session);
        setReplyDate(replyDate);
    }

    public Series(String name,Time startTime,Time endTime,Time replayTimeStart,Time programTime
            ,ArrayList<Integer>votes,int session,int episode,Date date,Date replyDate){
        super(name, startTime, endTime, programTime,date);
        this.setRates(votes);
        setReplayTimeStart(replayTimeStart);
        calculateAverage();
        setSession(session);
        setEpisode(episode);
        setReplyDate(replyDate);
    }

    public void setReplayTimeStart(Time replayTimeStart) {
        this.replayTimeStart = replayTimeStart;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public int getEpisode() {
        return episode;
    }

    public int getSession() {
        return session;
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

    public Time endOfReply(){
        return this.replayTimeStart.addTime(this.programTime);
    }

    public Date DateBetweenReply(){
        return this.replyDate.betweenDate(date);
    }
}