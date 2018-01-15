package businesslogic;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Worker {
    private String worker;
    private LocalDateTime time;
    private LocalDateTime lastSeen;
    private BigDecimal reportedHashrate;
    private BigDecimal currentHashrate;
    private int validShares;
    private int invalidShares;
    private int staleShares;
    private BigDecimal averageHashrate;

    public Worker(String worker, LocalDateTime time, LocalDateTime lastSeen, BigDecimal reportedHashrate, BigDecimal currentHashrate, int validShares, int invalidShares, int staleShares, BigDecimal averageHashrate) {
        this.worker = worker;
        this.time = time;
        this.lastSeen = lastSeen;
        this.reportedHashrate = reportedHashrate;
        this.currentHashrate = currentHashrate;
        this.validShares = validShares;
        this.invalidShares = invalidShares;
        this.staleShares = staleShares;
        this.averageHashrate = averageHashrate;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
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
        return "Worker{" +
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
