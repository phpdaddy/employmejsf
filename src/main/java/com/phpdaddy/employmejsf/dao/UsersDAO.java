package com.phpdaddy.employmejsf.dao;

import com.mysql.jdbc.Statement;
import com.phpdaddy.employmejsf.DataConnect;
import com.phpdaddy.employmejsf.beans.CandidateBean;
import com.phpdaddy.employmejsf.beans.EmployerBean;
import com.phpdaddy.employmejsf.beans.RegistrationBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {

    public static boolean validate(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            if (con == null)
                return false;
            ps = con.prepareStatement(
                    "SELECT username, password, id FROM user WHERE username = ? AND password = ? ");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }


    public static boolean register(RegistrationBean register, Object userBean) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT username, password FROM user where username = ?");
            ps.setString(1, register.getUsername());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("User -->" + register.getUsername() + " already exists");
                return false;
            }

            ps = con.prepareStatement("INSERT INTO user" + "(username, password) values" + "(?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, register.getUsername());
            ps.setString(2, register.getMd5Password());

            int executeUpdate = ps.executeUpdate();

            if (executeUpdate == 0) {
                System.out.println("Register error --> executeUpdate=0");
                return false;
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            int lastId;
            if (generatedKeys.next()) {
                lastId = generatedKeys.getInt(1);
            } else {
                return false;
            }
            if (userBean instanceof CandidateBean) {
                String sql = "";
                sql += "INSERT INTO candidate ";
                sql += "(id, ";
                sql += "age, ";
                sql += "years_of_experience, ";
                sql += "education, ";
                sql += "area, ";
                sql += "languages )";
                sql += "role )";
                sql += "VALUES ";
                sql += " (";
                sql += "?,?,?,?,?,?,? ";
                sql += " )";

                ps = con.prepareStatement(sql);
                ps.setInt(1, lastId);

                CandidateBean candidateBean = (CandidateBean) userBean;
                ps.setInt(2, candidateBean.getAge());
                ps.setInt(3, candidateBean.getYearsOfExperience());
                ps.setString(4, candidateBean.getEducation());
                ps.setString(5, candidateBean.getArea());
                ps.setString(6, candidateBean.getLanguages());
                ps.setString(8, "ROLE_CANDIDATE");

                executeUpdate = ps.executeUpdate();

                if (executeUpdate == 0) {
                    System.out.println("Register error --> executeUpdate=0");
                    return false;
                }

            }
            if (userBean instanceof EmployerBean) {
                String sql = "";
                sql += "INSERT INTO employer ";
                sql += "(id,  ";
                sql += "(role  ";
                sql += "  )";
                sql += "VALUES ";
                sql += " (";
                sql += "?, ";
                sql += "? ";
                sql += " )";

                ps = con.prepareStatement(sql);
                ps.setInt(1, lastId);
                ps.setString(2, "ROLE_EMPLOYER");

                executeUpdate = ps.executeUpdate();

                if (executeUpdate == 0) {
                    System.out.println("Register error --> executeUpdate=0");
                    return false;
                }
            }
            return true;
        } catch (SQLException ex) {
            System.out.println("Register error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
    }

    public static CandidateBean getCandidate(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        CandidateBean e = null;
        try {
            e = new CandidateBean();
            con = DataConnect.getConnection();
            String sql = "";
            sql += "SELECT ";
            sql += "age, ";
            sql += "years_of_experience, ";
            sql += "education, ";
            sql += "area, ";
            sql += "languages  ";
            sql += "FROM candidate ";
            sql += "WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                e.setAge(rs.getInt("age"));
                e.setYearsOfExperience(rs.getInt("years_of_experience"));
                e.setEducation(rs.getString("education"));
                e.setArea(rs.getString("area"));
                e.setLanguages(rs.getString("languages"));
                return e;
            }
            return null;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            DataConnect.close(con);
        }
        return e;
    }

    public static EmployerBean getEmployer(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        EmployerBean e = null;
        try {
            e = new EmployerBean();
            con = DataConnect.getConnection();
            String sql = "";
            sql += "SELECT ";
            sql += "FROM employer ";
            sql += "WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return e;
            }
            return null;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            DataConnect.close(con);
        }
        return e;
    }


    public static boolean update(int userId, Object userBean) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            if (userBean instanceof CandidateBean) {
                CandidateBean candidateBean = (CandidateBean) userBean;

                String sql = "UPDATE candidate SET ";
                sql += "age=?, ";
                sql += "years_of_experience=?, ";
                sql += "education=?, ";
                sql += "area=?, ";
                sql += "languages=?  ";
                sql += "WHERE id = ? ";

                ps = con.prepareStatement(sql);
                ps.setInt(1, candidateBean.getAge());
                ps.setInt(2, candidateBean.getYearsOfExperience());
                ps.setString(3, candidateBean.getEducation());
                ps.setString(4, candidateBean.getArea());
                ps.setString(5, candidateBean.getLanguages());
                ps.setInt(6, userId);

                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }
                return false;
            }
            if (userBean instanceof EmployerBean) {
                EmployerBean employerBean = (EmployerBean) userBean;
                return true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            DataConnect.close(con);
        }
        return false;
    }

    public static int getUserId(String username) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            if (con == null)
                return -1;
            ps = con.prepareStatement(
                    "SELECT  id FROM user WHERE username = ?  ");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return -1;
        } finally {
            DataConnect.close(con);
        }
        return -1;
    }

    public static String getUserRole(String username) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            if (con == null)
                return null;
            ps = con.prepareStatement(
                    "SELECT  role FROM user WHERE username = ?  ");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }
        return null;
    }
}