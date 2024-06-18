package main;

public class Record {
	private String id;
    private int level;
    private double correct;
    private double not_correct;
    private double tasu;
    private String testdate;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
        
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public double getCorrect() {
        return correct;
    }
    public void setCorrect(double correct) {
        this.correct = correct;
    }
    
    public double getNot_correct() {
        return not_correct;
    }
    public void setNot_correct(double not_correct) {
        this.not_correct = not_correct;
    }
    
    public double getTasu() {
        return tasu;
    }
    public void setTasu(double tasu) {
        this.tasu = tasu;
    }
    
    public String getTestdate() {
        return testdate;
    }
    public void setTestdate(String testdate) {
        this.testdate = testdate;
    }
}