package com.phpdaddy.employmejsf.beans;

import com.phpdaddy.employmejsf.dao.VacanciesDao;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

@ManagedBean
@SessionScoped
public class VacancyBean implements Serializable {
    private int id;
    private int employerId;
    private String name;
    private String description;
    private String education;
    private String area;
    private String languages;
    private int ageMin;
    private int ageMax;
    private int experienceMin;
    private int salary;
    private String place;
    private Date publishedAt;
    private ArrayList<VacancyBean> favorites;
    private String containingList;

    @PostConstruct
    public void init() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employer_id) {
        this.employerId = employer_id;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int age_min) {
        this.ageMin = age_min;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int age_max) {
        this.ageMax = age_max;
    }

    public int getExperienceMin() {
        return experienceMin;
    }

    public void setExperienceMin(int experience_min) {
        this.experienceMin = experience_min;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionShort() {
        int max = 400;
        int maxLength = (description.length() < max) ? description.length() : max;
        return description.substring(0, maxLength) + ((description.length() > max) ? " ..." : "");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date date) {
        this.publishedAt = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int vacancy_id) {
        this.id = vacancy_id;
    }

    public ArrayList<VacancyBean> getVacancies() {
        return VacanciesDao.getVacancies();
    }

    public ArrayList<VacancyBean> getMyVacancies() {
        return VacanciesDao.getMyVacancies();
    }

    public ArrayList<VacancyBean> getFavorites() {
        favorites = VacanciesDao.getFavorites();
        return favorites;
    }

    public String addToFavorites() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        VacanciesDao.addToFavorites(Integer.parseInt(params.get("id")));
        return NavigationBean.getBean().getCurrentPage();
    }

    public String removeFromFavorites() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        VacanciesDao.removeFromFavorites(Integer.parseInt(params.get("id")));
        getBean().getFavorites();
        return NavigationBean.getBean().getCurrentPage();
    }

    public boolean isInFavorites() {
        for (VacancyBean vacancyBean : getBean().favorites) {
            if (vacancyBean.getId() == getId())
                return true;
        }
        return false;
    }

    public static VacancyBean getBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        VacancyBean bean = context.getApplication().evaluateExpressionGet(context, "#{vacancyBean}", VacancyBean.class);
        return bean;
    }

    public String goToVacancy() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        VacancyBean v = VacanciesDao.getVacancy(Integer.parseInt(params.get("id")));
        String list = params.get("list");
        this.setId(v.getId());
        this.setEmployerId(v.getEmployerId());
        this.setName(v.getName());
        this.setDescription(v.getDescription());
        this.setEducation(v.getEducation());
        this.setArea(v.getArea());
        this.setLanguages(v.getLanguages());
        this.setAgeMin(v.getAgeMin());
        this.setAgeMax(v.getAgeMax());
        this.setExperienceMin(v.getExperienceMin());
        this.setSalary(v.getSalary());
        this.setPlace(v.getPlace());
        this.setPublishedAt(v.getPublishedAt());
        this.setContainingList(list);

        return "vacancy-detail?faces-redirect=true";
    }

    public String goToEditVacancy() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        VacancyBean v = VacanciesDao.getVacancy(Integer.parseInt(params.get("id")));
        String list = params.get("list");
        this.setId(v.getId());
        this.setEmployerId(v.getEmployerId());
        this.setName(v.getName());
        this.setDescription(v.getDescription());
        this.setEducation(v.getEducation());
        this.setArea(v.getArea());
        this.setLanguages(v.getLanguages());
        this.setAgeMin(v.getAgeMin());
        this.setAgeMax(v.getAgeMax());
        this.setExperienceMin(v.getExperienceMin());
        this.setSalary(v.getSalary());
        this.setPlace(v.getPlace());
        this.setPublishedAt(v.getPublishedAt());
        this.setContainingList(list);

        return "edit-vacancy?faces-redirect=true";
    }

    public String getContainingList() {
        return containingList;
    }

    public void setContainingList(String containingList) {
        this.containingList = containingList;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String addVacancy() {

        if (VacanciesDao.addVacancy()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Vacancy was added!", ""));
            return "my-vacancies";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "All fields are required!", ""));
            return "add-vacancy";
        }
    }

    public String editVacancy() {

        if (VacanciesDao.editVacancy()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Vacancy was edited!", ""));
            return "edit-vacancy";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "All fields are required!", ""));
            return "edit-vacancy";
        }
    }

    public String removeVacancy() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        VacancyBean v = VacanciesDao.getVacancy(Integer.parseInt(params.get("id")));
        VacanciesDao.removeVacancy(v.getId());
        return "vacancies?faces-redirect=true";
    }
}
