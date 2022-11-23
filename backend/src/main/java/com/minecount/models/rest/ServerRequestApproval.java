package com.minecount.models.rest;

public class ServerRequestApproval {
    private Long id;
    private Boolean approved;
    private String name;
    private String notes;

    public Long getId() {
        return id;
    }

    public Boolean getApproved() {
        return approved;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ServerRequestApproval{" +
                "id=" + id +
                ", approved=" + approved +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
