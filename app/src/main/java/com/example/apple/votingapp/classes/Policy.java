package com.example.apple.votingapp.classes;

import java.io.Serializable;

public class Policy implements Serializable {

    private String title;
    private String description;
    private Long count1;
    private Long count2;
    private Long count3;
    private Long count4;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public Policy() {
    }

    public Policy(String title, String description, Long count1, Long count2, Long count3, Long count4, String option1, String option2, String option3, String option4) {
        this.title = title;
        this.description = description;
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
        this.count4 = count4;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCount1() {
        return count1;
    }

    public void setCount1(Long count1) {
        this.count1 = count1;
    }

    public Long getCount2() {
        return count2;
    }

    public void setCount2(Long count2) {
        this.count2 = count2;
    }

    public Long getCount3() {
        return count3;
    }

    public void setCount3(Long count3) {
        this.count3 = count3;
    }

    public Long getCount4() {
        return count4;
    }

    public void setCount4(Long count4) {
        this.count4 = count4;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }
}
