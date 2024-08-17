package Model.Time;

import java.io.Serializable;

public class Date implements Serializable {
    private int year;
    private int month;
    private int day;

    public Date(int year,int month,int day)throws Exception{
        setYear(year);
        if(((month >= 7)&&(day > 30))||((month == 12)&&(day == 30))){
            throw new Exception("LOGICAL ERROR FOR MONTH & DAY");
        }
        setMonth(month);
        setDay(day);
    }

    public void setDay(int day) throws Exception{
        if(day > 31){
            Exception e =new Exception("DAY MUST BE LESS THAN 31");
            throw e;
        }
        this.day = day;
    }

    public void setMonth(int month) throws Exception{
        if(month > 12){
            throw new Exception("MONTH MUST BE LESS THAN 12");
        }
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean isLOlder(Date date){
        if(this.year > date.year)return false;
        if(this.year < date.year)return true;
        if(this.month > date.month)return false;
        if(this.month < date.month)return true;
        if(this.day > date.day)return false;
        if(this.day < date.day)return true;
        return true;
    }

    private void increaseOneDaye(){
        this.day++;
        switch (this.month){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:{
                this.month =this.month + Math.floorDiv(this.day,32);
                if(this.day == 32)this.day=1;
            }break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:{
                this.month =this.month + Math.floorDiv(this.day,31);
                if(this.day==31)this.day=1;
            }break;
            case 12:{
                if(this.day == 30){
                    this.day=1;
                    this.month=1;
                    this.year++;
                }
            }break;
        }
    }

      public Date betweenDate(Date date){
        if(this.isLOlder(date)){
            try {
                Date copyOfOlder =new Date(this.getYear(),this.getMonth(),this.getDay());
                Date distance =new Date(0,0,0);
                while (!copyOfOlder.equals(date)){
                    distance.increaseOneDaye();
                    copyOfOlder.increaseOneDaye();
                }
                return distance;
            }catch (Exception e){
                //it never happen
            }
        }
        else{
            try {
                Date copyOfOlder =new Date(date.getYear(),date.getMonth(),date.getDay());
                Date distance =new Date(0,0,0);
                while (!this.equals(copyOfOlder)){
                    distance.increaseOneDaye();
                    copyOfOlder.increaseOneDaye();
                }
                return distance;
            }catch (Exception e){
                //it never happen
            }
        }
        //it never happen
        return null;
    }

    public Day getDayOfWeek(){
        try {
            Date date =new Date(1399,2,20);
            int number =0;
            if(date.isLOlder(this)){
                while (!date.equals(this)){
                    System.out.println("$$$$$$$$$$$$$$$$$");
                    number++;
                    number%=7;
                    date.increaseOneDaye();
                }
                switch (number){
                    case 0:return Day.SATURDAY;
                    case 1:return Day.SUNDAY;
                    case 2:return Day.MONDAY;
                    case 3:return Day.TUSEDAY;
                    case 4:return Day.WEDNESDAY;
                    case 5:return Day.THURSEDAY;
                    case 6:return Day.FRIDAY;
                }
            }
            else {
                Date copyOfOlder =new Date(this.getYear(),this.getMonth(),this.getDay());
                while (!copyOfOlder.equals(date)){
                    System.out.println("$$$$$$");
                    copyOfOlder.increaseOneDaye();
                    number++;
                    number%=7;
                }
                switch (number){
                    case 0:return Day.SATURDAY;
                    case 1:return Day.FRIDAY;
                    case 2:return Day.THURSEDAY;
                    case 3:return Day.WEDNESDAY;
                    case 4:return Day.TUSEDAY;
                    case 5:return Day.MONDAY;
                    case 6:return Day.SUNDAY;
                }
            }
        }catch (Exception e){
            //it never happen
        }
        //it never happen
        return null;
    }
}
