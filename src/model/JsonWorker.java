package model;

import java.math.BigDecimal;

public class JsonWorker {
    private String worker;
    private long time;
    private long lastSeen;
    private BigDecimal reportedHashrate;
    private BigDecimal currentHashrate;
    private int validShares;
    private int invalidShares;
    private int staleShares;
    private BigDecimal averageHashrate;

    public JsonWorker() {

    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public BigDecimal getReportedHashrate() {
        return reportedHashrate;
    }

    public void setReportedHashrate(BigDecimal reportedHashrate) {
        this.reportedHashrate = reportedHashrate;
    }

    public BigDecimal getCurrentHashrate() {
        return currentHashrate;
    }

    public void setCurrentHashrate(BigDecimal currentHashrate) {
        this.currentHashrate = currentHashrate;
    }

    public int getValidShares() {
        return validShares;
    }

    public void setValidShares(int validShares) {
        this.validShares = validShares;
    }

    public int getInvalidShares() {
        return invalidShares;
    }

    public void setInvalidShares(int invalidShares) {
        this.invalidShares = invalidShares;
    }

    public int getStaleShares() {
        return staleShares;
    }

    public void setStaleShares(int staleShares) {
        this.staleShares = staleShares;
    }

    public BigDecimal getAverageHashrate() {
        return averageHashrate;
    }

    public void setAverageHashrate(BigDecimal averageHashrate) {
        this.averageHashrate = averageHashrate;
    }

    @Override
    public String toString() {
        return "JsonWorker{" +
                "worker='" + worker + '\'' +
                ", time=" + time +
                ", lastSeen=" + lastSeen +
                ", reportedHashrate=" + reportedHashrate +
                ", currentHashrate=" + currentHashrate +
                ", validShares=" + validShares +
                ", invalidShares=" + invalidShares +
                ", staleShares=" + staleShares +
                ", averageHashrate=" + averageHashrate +
                '}';
    }
}