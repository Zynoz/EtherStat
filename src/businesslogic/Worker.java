package businesslogic;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Worker {
    private int id;
    private String worker;
    private LocalDateTime time;
    private LocalDateTime lastSeen;
    private BigDecimal reportedHashrate;
    private BigDecimal currentHashrate;
    private int validShares;
    private int invalidShares;
    private int staleShares;
    private BigDecimal averageHashrate;

    public Worker(int id, String worker, LocalDateTime time, LocalDateTime lastSeen, BigDecimal reportedHashrate, BigDecimal currentHashrate, int validShares, int invalidShares, int staleShares, BigDecimal averageHashrate) {
        setId(id);
        setWorker(worker);
        setTime(time);
        setLastSeen(lastSeen);
        setReportedHashrate(reportedHashrate);
        setCurrentHashrate(currentHashrate);
        setValidShares(validShares);
        setInvalidShares(invalidShares);
        setStaleShares(staleShares);
        setAverageHashrate(averageHashrate);
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        if (time != null) {
            this.time = time;
        } else {
            this.time = LocalDateTime.now();
        }
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        if (lastSeen != null) {
            this.lastSeen = lastSeen;
        } else {
            this.lastSeen = LocalDateTime.now();
        }
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
