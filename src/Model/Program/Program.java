package Model.Program;

import Model.Program.Advertisement.Advertisement;
import Model.Time.Date;
import Model.Time.Time;

import Model.Person.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Program implements Serializable {
    protected String name;
    protected double rate = 0.0;
    protected ArrayList<Integer> rates;
    protected ArrayList<User> users;
    protected Time startTime;
    protected Time endTime;
    protected Time programTime;
    protected Date date;
    protected Time replayTimeStart;
    protected Date replyDate;

    public Program(String name, Time startTime, Time endTime, Time programTime, Date date) {
        rates = new ArrayList<>();
        users = new ArrayList<>();
        setName(name);
        setEndTime(endTime);
        setProgramTime(programTime);
        setStartTime(startTime);
        setDate(date);
    }

    public Program(String name, Time startTime, Time endTime, Date date, Date date2) {
        users = new ArrayList<>();
        rates = new ArrayList<>();
        setName(name);
        setStartTime(startTime);
        setEndTime(endTime);
        setEndTime(endTime);
        Time programTime = endTime.timeBetweenTime(startTime);
        setProgramTime(programTime);
        setDate(date);
        setReplyDate(date2);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setRates(ArrayList<Integer> rates) {
        this.rates = rates;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setProgramTime(Time programTime) {
        this.programTime = programTime;
    }

    public void setReplayTimeStart(Time replayTimeStart) {
        this.replayTimeStart = replayTimeStart;
    }

    public String getName() {
        return name;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public Time getReplayTimeStart() {
        return replayTimeStart;
    }

    public Date getDate() {
        return date;
    }

    public double getRate() {
        return rate;
    }

    public ArrayList<Integer> getRates() {
        return rates;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Time getProgramTime() {
        return programTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public boolean hasTimeCrash(Program program) {
        if (this.date.equals(program.getDate())) {
            if (this.startTime.isLater(program.getEndTime())) {

            } else {
                return true;
            }
            if (program.getStartTime().isLater(this.endTime)) {

            } else {
                return true;
            }
        }
        if (program.getReplyDate() != null) {
            if (this.date.equals(program.getReplyDate())) {
                if (this.startTime.isLater(program.getReplayTimeStart().addTime(program.getProgramTime()))) {

                } else {
                    return true;
                }
                if (program.getReplayTimeStart().isLater(this.endTime)) {

                } else {
                    return true;
                }
            }
        }
        if (! (this instanceof Advertisement)) {
            if (this.replyDate.equals(program.getDate())) {
                if (this.replayTimeStart.isLater(program.getEndTime())) {

                } else {
                    return true;
                }
                if (program.getStartTime().isLater(this.replayTimeStart.addTime(this.programTime))) {

                } else {
                    return true;
                }
            }
        }
        if (!(this instanceof Advertisement)) {
            if (this.replyDate.equals(program.getReplyDate())) {
                if (this.replayTimeStart.isLater(program.getReplayTimeStart().addTime(program.getProgramTime()))) {

                } else {
                    return true;
                }
                if (program.getReplayTimeStart().isLater(this.replayTimeStart.addTime(this.programTime))) {

                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public void vote(User user, int number) throws Exception {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getUsername().equals(user.getUsername())) {
                throw new Exception("USER HAS VOTED FOR THIS PROGRAM!");
            }
        }
        users.add(user);
        this.rates.add((Integer) number);
        this.rate = ((double) ((this.rate * (this.rates.size() - 1)) + number)) / ((double) this.rates.size());
    }

    public boolean hasProgram(ArrayList<Program> programs) {
        for (int i = 0; i < programs.size(); i++) {
            if (this.equals(programs.get(i))) {
                return true;
            }
        }
        return false;
    }
}
