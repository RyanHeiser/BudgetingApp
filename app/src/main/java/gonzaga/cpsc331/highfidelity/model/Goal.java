package gonzaga.cpsc331.highfidelity.model;

import java.math.BigDecimal;

public class Goal {
    private BudgetRow category;
    private BigDecimal progress;
    private boolean isAchieved;
    public Goal(BudgetRow category){
        this.category = category;

        this.isAchieved = false;
    }

    public BigDecimal getProgress(){
        return this.progress;
    }
    public <bigDecimal> void incrementProgressBar(BigDecimal increment){
    this.progress = this.progress.add(increment);
    }
    public boolean isAchieved(){
        if(progress.compareTo(this.category.getAmount()) >= 0){
            return true;
        }
        else{
            return false;
        }
    }


}
