package DB;

public class Users {
	private String username;
	private int level;
	private double correct;
	private double not_correct;
	private double tasu;
	private String testdate;
	
	public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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

/*
create database TypingPractice;

use TypingPractice;

create table users (
username varchar(45) NOT NULL,
level int NOT NULL,
correct double NOT NULL,
not_correct double NOT NULL,
tasu double NOT NULL,
testdate varchar(45) NOT NULL,
primary key (testdate)
);
alter table users convert to charset utf8;
*/
