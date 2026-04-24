package gonzaga.cpsc331.highfidelity.model;

import java.math.BigDecimal;

public class Goal {

    private String name;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    public Goal(String name, BigDecimal goalAmount, BigDecimal currentAmount){
        this.name = name;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
    }

    public String getName(){
        return this.name;
    }
    public BigDecimal getGoalAmount(){
        return this.goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount){
        this.goalAmount = goalAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void addAmount(BigDecimal amount) {
        currentAmount = currentAmount.add(amount);
    }
}
