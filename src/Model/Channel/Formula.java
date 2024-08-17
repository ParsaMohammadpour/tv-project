package Model.Channel;

import Model.Time.Time;

import Model.Program.Advertisement.Advertisement;

import java.io.Serializable;

public class Formula implements Serializable {
    //Formula : prefactorOfTime*(program time ^ pow)*GildTimeFactor*betweenProgram;
    private int preFactorOfTime = 1;
    private int powerOfTime = 1;
    private Time firstOfGoldTime;
    private Time endOfGoldTime;
    private int goldTimeFactor = 1;
    private int betweenProgramsFactor = 1;

    public Formula(int preFactorOfTime, int powerOfTime, Time firstOfGoldTime, Time endOfGoldTime, int goldTimeFactor) {
        setEndOfGoldTime(endOfGoldTime);
        setFirstOfGoldTime(firstOfGoldTime);
        setPowerOfTime(powerOfTime);
        setPreFactorOfTime(preFactorOfTime);
        setGoldTimeFactor(goldTimeFactor);
    }

    public Formula(int preFactorOfTime, int powerOfTime, Time firstOfGoldTime, Time endOfGoldTime, int goldTimeFactor
            , int betweenProgramsFactor) {
        this(preFactorOfTime, powerOfTime, firstOfGoldTime, endOfGoldTime, goldTimeFactor);
        setBetweenProgramsFactor(betweenProgramsFactor);
    }

    public void setBetweenProgramsFactor(int betweenProgramsFactor) {
        this.betweenProgramsFactor = betweenProgramsFactor;
    }

    public void setGoldTimeFactor(int goldTimeFactor) {
        this.goldTimeFactor = goldTimeFactor;
    }

    public void setPowerOfTime(int powerOfTime) {
        this.powerOfTime = powerOfTime;
    }

    public void setEndOfGoldTime(Time endOfGoldTime) {
        this.endOfGoldTime = endOfGoldTime;
    }

    public void setFirstOfGoldTime(Time firstOfGoldTime) {
        this.firstOfGoldTime = firstOfGoldTime;
    }

    public void setPreFactorOfTime(int preFactorOfTime) {
        this.preFactorOfTime = preFactorOfTime;
    }

    public int getBetweenProgramsFactor() {
        return betweenProgramsFactor;
    }

    public Time getEndOfGoldTime() {
        return endOfGoldTime;
    }

    public Time getFirstOfGoldTime() {
        return firstOfGoldTime;
    }

    public int getGoldTimeFactor() {
        return goldTimeFactor;
    }

    public int getPowerOfTime() {
        return powerOfTime;
    }

    public int getPreFactorOfTime() {
        return preFactorOfTime;
    }

    public long price(Advertisement advertisement, boolean betweenPrograms) {
        long totalPrice;
        totalPrice = (long) StrictMath.round(StrictMath.pow(advertisement.getProgramTime().programTimeInSecond(), this.powerOfTime));
        totalPrice *= this.preFactorOfTime;
        if ((advertisement.getStartTime().isLater(this.firstOfGoldTime)) && (this.endOfGoldTime.isLater(advertisement.getEndTime()))) {
            totalPrice *= this.goldTimeFactor;
        }
        if (betweenPrograms) {
            totalPrice *= this.betweenProgramsFactor;
        }
        return totalPrice;
    }
}