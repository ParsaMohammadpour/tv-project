package Model.Program;

import Model.Time.Date;
import Model.Time.Time;

import Model.Person.User;

import Model.Person.Presenter;

import java.io.Serializable;
import java.util.ArrayList;

public class News extends Program implements Serializable {
    private Presenter presenter;

    public News(String name, Time startTime, Time endTime, Time programTime, Presenter presenter){
        super(name, startTime, endTime, programTime,null);
        setPresenter(presenter);
    }

    public News(String name,Time startTime,Time endTime,Presenter presenter){
        super(name, startTime, endTime,(Date)null,null);
        Time programTime =endTime.timeBetweenTime(startTime);
        this.setProgramTime(programTime);
        setPresenter(presenter);
    }

    public News(String name, Time startTime, Time endTime, Presenter presenter,ArrayList<Integer> votes){
        super(name, startTime, endTime,(Date) null,null);
        Time programTime =endTime.timeBetweenTime(startTime);
        this.setProgramTime(programTime);
        this.setRates(votes);
        calculateAverage();
        setPresenter(presenter);
    }

    public News(String name,Time startTime,Time endTime ,Time programTime,Presenter presenter,ArrayList<Integer>votes){
        super(name, startTime, endTime, programTime,null);
        this.setRates(votes);
        calculateAverage();
        setPresenter(presenter);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
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
}
