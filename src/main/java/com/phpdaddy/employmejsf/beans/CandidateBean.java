package com.phpdaddy.employmejsf.beans;

import com.phpdaddy.employmejsf.dao.UsersDAO;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class CandidateBean implements Serializable {
    private int age;
    private int yearsOfExperience;
    private String education;
    private String area;
    private String languages;
    private String place;

    @PostConstruct
    public void init() {
        CandidateBean candidate = UsersDAO.getCandidate(SessionBean.getBean().getUserId());
        if (candidate != null) {
            this.setAge(candidate.getAge());
            this.setYearsOfExperience(candidate.getYearsOfExperience());
            this.setEducation(candidate.getEducation());
            this.setArea(candidate.getArea());
            this.setLanguages(candidate.getLanguages());
            this.setPlace(candidate.getPlace());
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
