package com.phpdaddy.employmejsf.beans;

import com.phpdaddy.employmejsf.dao.UsersDAO;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class EmployerBean implements Serializable {
	@PostConstruct
	public void init() {
		if (SessionBean.getBean().getUserId() > -1) {
			EmployerBean e = UsersDAO.getEmployer(SessionBean.getBean().getUserId());
			if (e != null) {

			}
		}
	}
}
