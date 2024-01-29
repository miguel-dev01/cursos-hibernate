package com.hibernate.model.manytomany;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity(name = "user_projects")
public class UserProjects {

    @Id
    @Column(name = "project_id", insertable = false, updatable = false)
    private Long projectId;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    private Role role;

    private LocalDate initDate;
    private LocalDate endDate;
    
    public UserProjects() {
    }

    public UserProjects(Project project1, User user1, Role role, LocalDate initDate,
            LocalDate endDate) {
        this.projectId = project1.getId();
        this.userId = user1.getId();
        this.role = role;
        this.initDate = initDate;
        this.endDate = endDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "UserProjects [projectId=" + projectId + ", userId=" + userId + ", role=" + role + ", initDate="
                + initDate + ", endDate=" + endDate + "]";
    }

}
