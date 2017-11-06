package com.phpdaddy.employmejsf.beans;

import com.phpdaddy.employmejsf.dao.UsersDAO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

    private String password;
    private String message;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // validate login
    public String login() {
        boolean valid = UsersDAO.validate(getUsername(), getPassword());
        if (valid) {
            HttpSession session = SessionBean.getBean().getSession();
            int id = UsersDAO.getUserId(getUsername());
            String role = UsersDAO.getUserRole(getUsername());
            session.setAttribute("id", id);
            session.setAttribute("role", role);
            session.setAttribute("username", getUsername());
            return "profile?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter correct username and password", ""));
            return "login";
        }
    }

    // logout event, invalidate session
    public String logout() {
        HttpSession session = SessionBean.getBean().getSession();
        session.invalidate();
        return "login?faces-redirect=true";
    }

}