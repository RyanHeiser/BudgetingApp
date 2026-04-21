package gonzaga.cpsc331.highfidelity.model;

public class Goal {
    private double goal;

    private double progress;

    private String name;

    private boolean isAchieved;
    public Goal(double goal, String name){
        this.goal = goal;
        this.name = name;
        this.progress = 0.0;
        this.isAchieved = false;
    }

    public String getName(){
        return this.name;
    }
    public double getGoal(){
        return this.goal;
    }
    public double getProgress(){
        return this.progress;
    }
    public void incrementProgressBar(double increment){
        this.progress += increment;
    }
    public boolean isAchieved(){
        if(progress >= goal){
            return true;
        }
        else{
            return false;
        }
    }


}
