package com.phpdaddy.employmejsf.beans;

import com.phpdaddy.employmejsf.dao.UsersDAO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class ProfileBean implements Serializable {

	public String updateCandidate() {
		FacesContext context = FacesContext.getCurrentInstance();
		CandidateBean e = context.getApplication().evaluateExpressionGet(context, "#{candidateBean}", CandidateBean.class);
		if (UsersDAO.update(SessionBean.getBean().getUserId(), e)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile update was successful", ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating profile", ""));
		}
		return "profile";
	}

	public String updateEmployer() {
		FacesContext context = FacesContext.getCurrentInstance();
		EmployerBean e = context.getApplication().evaluateExpressionGet(context, "#{employerBean}", EmployerBean.class);
		if (UsersDAO.update(SessionBean.getBean().getUserId(), e)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile update was successful", ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating profile", ""));
		}
		return "profile";
	}
}
