package models;

import java.util.Date;

public class Reduction {

    private int reductionId;
    private int userId;
    private double reductionPercentage;
    private Date validUntil;
    private String status;

    public Reduction(int reductionId, int userId, double reductionPercentage, Date validUntil, String status) {
        this.reductionId = reductionId;
        this.userId = userId;
        this.reductionPercentage = reductionPercentage;
        this.validUntil = validUntil;
        this.status = status;
    }

    public int getReductionId() {
        return reductionId;
    }

    public void setReductionId(int reductionId) {
        this.reductionId = reductionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getReductionPercentage() {
        return reductionPercentage;
    }

    public void setReductionPercentage(double reductionPercentage) {
        this.reductionPercentage = reductionPercentage;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reduction{" +
                "reductionId=" + reductionId +
                ", userId=" + userId +
                ", reductionPercentage=" + reductionPercentage +
                ", validUntil=" + validUntil +
                ", status='" + status + '\'' +
                '}';
    }
}
