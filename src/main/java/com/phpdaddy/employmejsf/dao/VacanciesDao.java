package com.phpdaddy.employmejsf.dao;

import com.phpdaddy.employmejsf.DataConnect;
import com.phpdaddy.employmejsf.beans.SessionBean;
import com.phpdaddy.employmejsf.beans.VacancyBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class VacanciesDao {

    public static ArrayList<VacancyBean> getVacancies() {
        ArrayList<VacancyBean> vacancyBeans = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            if (con == null)
                return null;
            ps = con.prepareStatement("SELECT * FROM vacancy v ");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // SessionBean.getBean().getSession().setAttribute("id",
                // rs.getInt("id"));
                VacancyBean v = new VacancyBean();

                if (v != null) {
                    v.setId(rs.getInt("id"));
                    v.setEmployerId(rs.getInt("employer_id"));
                    v.setName(rs.getString("name"));
                    v.setDescription(rs.getString("description"));
                    v.setEducation(rs.getString("education"));
                    v.setArea(rs.getString("area"));
                    v.setLanguages(rs.getString("languages"));
                    v.setAgeMin(rs.getInt("age_min"));
                    v.setAgeMax(rs.getInt("age_max"));
                    v.setExperienceMin(rs.getInt("experience_min"));
                    v.setSalary(rs.getInt("salary"));
                    v.setPlace(rs.getString("place"));
                    v.setPublishedAt(rs.getDate("published_at"));
                }

                vacancyBeans.add(v);
            }
            return vacancyBeans;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Vacancies list retrieve error -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }
    }

    public static ArrayList<VacancyBean> getFavorites() {
        ArrayList<VacancyBean> vacancyBeans = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            if (con == null)
                return null;
            String sql = "SELECT * FROM vacancy v ";
            sql += " LEFT JOIN favorite_vacancies ev ";
            sql += " ON  v.id=ev.vacancy_id ";
            sql += " LEFT JOIN candidate e ";
            sql += " ON e.id=ev.candidate_id ";
            sql += " WHERE  e.id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, SessionBean.getBean().getUserId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VacancyBean v = new VacancyBean();

                if (v != null) {
                    v.setId(rs.getInt("id"));
                    v.setEmployerId(rs.getInt("employer_id"));
                    v.setName(rs.getString("name"));
                    v.setDescription(rs.getString("description"));
                    v.setEducation(rs.getString("education"));
                    v.setArea(rs.getString("area"));
                    v.setLanguages(rs.getString("languages"));
                    v.setAgeMin(rs.getInt("age_min"));
                    v.setAgeMax(rs.getInt("age_max"));
                    v.setExperienceMin(rs.getInt("experience_min"));
                    v.setSalary(rs.getInt("salary"));
                    v.setPlace(rs.getString("place"));
                    v.setPublishedAt(rs.getDate("published_at"));
                }

                vacancyBeans.add(v);
            }
            return vacancyBeans;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Favorites list retrieve error -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }
    }

    public static boolean addToFavorites(int vacancyId) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();

            String sql = "";
            sql += "INSERT INTO favorite_vacancies ";
            sql += "( candidate_id, vacancy_id  ";
            sql += " )";
            sql += "VALUES ";
            sql += " (";
            sql += "?, ? ";
            sql += " )";

            ps = con.prepareStatement(sql);

            ps.setInt(1, SessionBean.getBean().getUserId());
            ps.setInt(2, vacancyId);
            int executeUpdate = ps.executeUpdate();

            if (executeUpdate == 0) {
                System.out.println("Add to favorites error --> executeUpdate=0");
                return false;
            }

            return true;
        } catch (SQLException ex) {
            System.out.println("Add to favorites error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
    }

    public static boolean removeFromFavorites(int vacancyId) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();

            String sql = "";
            sql += "DELETE FROM favorite_vacancies ";
            sql += "WHERE ";
            sql += " candidate_id=? AND vacancy_id= ? ";

            ps = con.prepareStatement(sql);

            ps.setInt(1, SessionBean.getBean().getUserId());
            ps.setInt(2, vacancyId);
            int executeUpdate = ps.executeUpdate();

            if (executeUpdate == 0) {
                System.out.println("Remove from favorites error --> executeUpdate=0");
                return false;
            }

            return true;
        } catch (SQLException ex) {
            System.out.println("Remove from favorites error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
    }

    public static VacancyBean getVacancy(int id) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            if (con == null)
                return null;
            ps = con.prepareStatement("SELECT * FROM vacancy v WHERE id =?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                VacancyBean v = new VacancyBean();

                if (v != null) {
                    v.setId(rs.getInt("id"));
                    v.setEmployerId(rs.getInt("employer_id"));
                    v.setName(rs.getString("name"));
                    v.setDescription(rs.getString("description"));
                    v.setEducation(rs.getString("education"));
                    v.setArea(rs.getString("area"));
                    v.setLanguages(rs.getString("languages"));
                    v.setAgeMin(rs.getInt("age_min"));
                    v.setAgeMax(rs.getInt("age_max"));
                    v.setExperienceMin(rs.getInt("experience_min"));
                    v.setSalary(rs.getInt("salary"));
                    v.setPlace(rs.getString("place"));
                    v.setPublishedAt(rs.getDate("published_at"));
                }
                return v;
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Vacancies list retrieve error -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }
    }

    public static ArrayList<VacancyBean> getMyVacancies() {
        ArrayList<VacancyBean> vacancyBeans = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            if (con == null)
                return null;
            String sql = "SELECT * FROM vacancy v ";
            sql += " LEFT JOIN employer e ";
            sql += " ON e.id=v.employer_id ";
            sql += " WHERE  e.id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, SessionBean.getBean().getUserId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VacancyBean v = new VacancyBean();

                if (v != null) {
                    v.setId(rs.getInt("id"));
                    v.setEmployerId(rs.getInt("employer_id"));
                    v.setName(rs.getString("name"));
                    v.setDescription(rs.getString("description"));
                    v.setEducation(rs.getString("education"));
                    v.setArea(rs.getString("area"));
                    v.setLanguages(rs.getString("languages"));
                    v.setAgeMin(rs.getInt("age_min"));
                    v.setAgeMax(rs.getInt("age_max"));
                    v.setExperienceMin(rs.getInt("experience_min"));
                    v.setSalary(rs.getInt("salary"));
                    v.setPlace(rs.getString("place"));
                    v.setPublishedAt(rs.getDate("published_at"));
                }

                vacancyBeans.add(v);
            }
            return vacancyBeans;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Favorites list retrieve error -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }
    }

    public static boolean addVacancy() {
        Connection con = null;
        PreparedStatement ps = null;

        VacancyBean v = VacancyBean.getBean();
        try {
            con = DataConnect.getConnection();

            String sql = "";
            sql += "INSERT INTO vacancy ";
            sql += "( employer_id,"
                    + " name,"
                    + " description,"
                    + " education,"
                    + " languages,"
                    + " age_min,"
                    + " age_max,"
                    + " area,"
                    + " experience_min,"
                    + " salary,"
                    + " place,"
                    + " published_at";
            sql += " )";
            sql += "VALUES ";
            sql += " (";
            sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ";
            sql += " )";

            ps = con.prepareStatement(sql);

            ps.setInt(1, SessionBean.getBean().getUserId());
            ps.setString(2, v.getName());
            ps.setString(3, v.getDescription());
            ps.setString(4, v.getEducation());
            ps.setString(5, v.getLanguages());
            ps.setInt(6, v.getAgeMin());
            ps.setInt(7, v.getAgeMax());
            ps.setString(8, v.getArea());
            ps.setInt(9, v.getExperienceMin());
            ps.setInt(10, v.getSalary());
            ps.setString(11, v.getPlace());
            ps.setDate(12, new Date(Calendar.getInstance().getTime().getTime()));
            int executeUpdate = ps.executeUpdate();

            if (executeUpdate == 0) {
                System.out.println("Add vacancy error --> executeUpdate=0");
                return false;
            }

            return true;
        } catch (SQLException ex) {
            System.out.println("Add vacancy error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
    }

    public static boolean editVacancy() {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            VacancyBean vacancyBean = (VacancyBean) VacancyBean.getBean();
            String sql = "UPDATE vacancy SET ";
            sql += "`name`=?, ";
            sql += "description=?, ";
            sql += "education=?, ";
            sql += "age_min=?, ";
            sql += "age_max=?, ";
            sql += "area=?, ";
            sql += "experience_min=?, ";
            sql += "salary=?, ";
            sql += "place=?, ";
            sql += "published_at=? ";
            sql += "WHERE id = ? ";

            ps = con.prepareStatement(sql);
            ps.setString(1, vacancyBean.getName());
            ps.setString(2, vacancyBean.getDescription());
            ps.setString(3, vacancyBean.getEducation());
            ps.setInt(4, vacancyBean.getAgeMin());
            ps.setInt(5, vacancyBean.getAgeMax());
            ps.setString(6, vacancyBean.getArea());
            ps.setInt(7, vacancyBean.getExperienceMin());
            ps.setInt(8, vacancyBean.getSalary());
            ps.setString(9, vacancyBean.getPlace());
            ps.setDate(10, vacancyBean.getPublishedAt());
            ps.setInt(11, vacancyBean.getId());

            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
            return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            DataConnect.close(con);
        }
        return false;
    }

    public static boolean removeVacancy(int id) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();

            String sql = "";
            sql += "DELETE FROM vacancy ";
            sql += "WHERE ";
            sql += " id=?  ";

            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            int executeUpdate = ps.executeUpdate();

            if (executeUpdate == 0) {
                System.out.println("Remove from vacancies error --> executeUpdate=0");
                return false;
            }

            return true;
        } catch (SQLException ex) {
            System.out.println("Remove from vacancies error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
    }
}
