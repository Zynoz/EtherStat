package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Worker {
    private int id;
    private String worker;
    private BigDecimal reportedHashrate;
    private BigDecimal currentHashrate;
    private int validShares;
    private int invalidShares;
    private int staleShares;
    private BigDecimal averageHashrate;
    private Timestamp timest;

    public Worker(int id, String worker, BigDecimal reportedHashrate, BigDecimal currentHashrate, int validShares, int invalidShares, int staleShares, BigDecimal averageHashrate, Timestamp timest) {
        setId(id);
        setWorker(worker);
        setReportedHashrate(reportedHashrate);
        setCurrentHashrate(currentHashrate);
        setValidShares(validShares);
        setInvalidShares(invalidShares);
        setStaleShares(staleShares);
        setAverageHashrate(averageHashrate);
        setTimest(timest);
    }

    public Timestamp getTimest() {
        return timest;
    }

    public void setTimest(Timestamp timest) {
        this.timest = timest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public BigDecimal getReportedHashrate() {
        return reportedHashrate;
    }

    public void setReportedHashrate(BigDecimal reportedHashrate) {
        if (reportedHashrate != null) {
            this.reportedHashrate = reportedHashrate;
        } else {
            this.reportedHashrate = BigDecimal.valueOf(0);
        }
    }

    public BigDecimal getCurrentHashrate() {
        return currentHashrate;
    }

    public void setCurrentHashrate(BigDecimal currentHashrate) {
        if (currentHashrate != null) {
            this.currentHashrate = currentHashrate;
        } else {
            this.currentHashrate = BigDecimal.valueOf(0);
        }
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
        if (averageHashrate != null) {
            this.averageHashrate = averageHashrate;
        } else {
            this.averageHashrate = BigDecimal.valueOf(0);
        }
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", worker='" + worker + '\'' +
                ", reportedHashrate=" + reportedHashrate +
                ", currentHashrate=" + currentHashrate +
                ", validShares=" + validShares +
                ", invalidShares=" + invalidShares +
                ", staleShares=" + staleShares +
                ", averageHashrate=" + averageHashrate +
                ", timest=" + timest +
                '}';
    }
}