package gonzaga.cpsc331.highfidelity.model;

import java.math.BigDecimal;

public class Goal {

    private String Name;
    private BudgetRow budgetrow;

    private BigDecimal Amount;
    private boolean isAchieved;
    public Goal(String name, BudgetRow category, BigDecimal amount){
        this.Name = name;
        this.budgetrow = category;
        this.Amount = amount;
        this.isAchieved = false;
    }

    public String getName(){
        return this.Name;
    }
    public BigDecimal getAmount(){
        return this.Amount;
    }

    public void setAmount( BigDecimal amount){
        this.Amount = amount;
    }

    public BudgetRow getBudgetRow(){
        return this.budgetrow;
    }
    public <bigDecimal> void incrementProgressBar(BigDecimal increment){
    this.Amount = this.Amount.add(increment);
    }
    public boolean isAchieved(){
        if(Amount.compareTo(this.budgetrow.getAmount(BigDecimal.ZERO)) >= 0){
            return true;
        }
        else{
            return false;
        }
    }


}
