package Model.Program.Advertisement;

import Model.Program.Program;

import Model.Time.Date;
import Model.Time.Time;

import Model.Person.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Advertisement extends Program implements Serializable {
    private boolean isBetweenPrograms;
    private long price;
    private Company company;

    public Advertisement(String name, Time startTime, Time endTime, Time replayTimeStart, Time programTime
            , Company company, long price, Date date){
        super(name, startTime, endTime, programTime,date);
        setReplayTimeStart(replayTimeStart);
        setCompany(company);
        setPrice(price);
    }

    public Advertisement(String name, Time startTime, Time endTime, Time replayTimeStart
            ,Company company,long price,Date date){
        super(name, startTime, endTime,date,null);
        Time programTime =endTime.timeBetweenTime(startTime);
        this.setProgramTime(programTime);
        setReplayTimeStart(replayTimeStart);
        setCompany(company);
        setPrice(price);
    }

    public void setReplayTimeStart(Time replayTimeStart) {
        this.replayTimeStart = replayTimeStart;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setBetweenPrograms(boolean betweenPrograms) {
        isBetweenPrograms = betweenPrograms;
    }

    public boolean isBetweenPrograms() {
        return isBetweenPrograms;
    }

    public long getPrice() {
        return price;
    }

    public Company getCompany() {
        return company;
    }

    public Time getReplayTimeStart() {
        return replayTimeStart;
    }

    @Override
    public ArrayList<Integer> getRates() {
        return null;
    }

    @Override
    public double getRate() {
        return -1.0;
    }

    @Override
    public void vote(User user, int number) {
        return;
    }
}
