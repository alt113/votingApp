package com.example.apple.votingapp.classes;

import java.io.Serializable;

public class Policy implements Serializable {

    private String title;
    private String description;
    private Long count1;
    private Long count2;
    private Long count3;
    private Long option1;
    private Long option2;
    private Long option3;

    public Policy() {
    }

    public Policy(String title, String description, Long count1, Long count2, Long count3, Long option1, Long option2, Long option3) {
        this.title = title;
        this.description = description;
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
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

    public Long getOption1() {
        return option1;
    }

    public void setOption1(Long option1) {
        this.option1 = option1;
    }

    public Long getOption2() {
        return option2;
    }

    public void setOption2(Long option2) {
        this.option2 = option2;
    }

    public Long getOption3() {
        return option3;
    }

    public void setOption3(Long option3) {
        this.option3 = option3;
    }
}
