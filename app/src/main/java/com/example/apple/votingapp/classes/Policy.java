package com.example.apple.votingapp.classes;

public class Policy {

    private String title;
    private String description;
    private Long vote;

    public Policy() {
    }

    public Policy(String title, String description, Long vote) {
        this.title = title;
        this.description = description;
        this.vote = vote;
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

    public Long getVote() {
        return vote;
    }

    public void setVote(Long vote) {
        this.vote = vote;
    }
}
