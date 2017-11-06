package com.phpdaddy.employmejsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {

    public HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public String getUsername() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null && session.getAttribute("username") != null)
            return session.getAttribute("username").toString();
        return null;
    }

    public int getUserId() {
        HttpSession session = getSession();
        if (session != null && session.getAttribute("id") != null) {
            return (int) session.getAttribute("id");
        } else
            return -1;
    }

    public String getUserRole() {
        HttpSession session = getSession();
        if (session != null && session.getAttribute("role") != null) {
            return (String) session.getAttribute("role");
        }
        return null;
    }

    public static SessionBean getBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        SessionBean bean = context.getApplication().evaluateExpressionGet(context, "#{sessionBean}", SessionBean.class);
        return bean;
    }
}