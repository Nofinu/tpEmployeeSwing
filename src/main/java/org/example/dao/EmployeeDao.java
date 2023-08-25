package org.example.dao;

import org.example.connexion.ConnectionUtil;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    private Connection con;

    private PreparedStatement ps;

    private ResultSet resultSet;

    private DepartmentDao departmentDao;

    public EmployeeDao() {
       departmentDao = new DepartmentDao();
    }

    public boolean add(Employee employee) {
        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("INSERT INTO employee(`firstname`,`lastname`,`role`,`department_id`) VALUES(?,?,?,?) ");
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getRole().toString());
            ps.setInt(4, employee.getDepartment().getId());
            int n = ps.executeUpdate();
            return n>0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean update (Employee employee){
        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("UPDATE employee SET firstname = ?,lastname = ?,role = ?,department_id = ? where id = ?");
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getRole().toString());
            ps.setInt(4, employee.getDepartment().getId());
            ps.setInt(5, employee.getId());
            int n = ps.executeUpdate();
            return n>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete (int id){
        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("DELETE FROM employee WHERE id = ? ");
            ps.setInt(1, id);
            int n = ps.executeUpdate();
            return n>0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee findById(int id) {
        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("SELECT id,firstname,lastname,role,department_id FROM employee where id = ? ");
            ps.setInt(1, id);
            resultSet = ps.executeQuery();
            if(resultSet.next()){
                return  new Employee(resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("role").equals("MANAGER")? Role.MANAGER :resultSet.getString("role").equals("EMPLOYEE")? Role.EMPLOYEE : Role.RH,
                        departmentDao.findById(resultSet.getInt("department_id")));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employee> findAll (){
        con = ConnectionUtil.getConnection();
        List<Employee> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT id,firstname,lastname,role,department_id FROM employee ");
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                list.add(new Employee(resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("role").equals("MANAGER")? Role.MANAGER :resultSet.getString("role").equals("EMPLOYEE")? Role.EMPLOYEE : Role.RH,
                        departmentDao.findById(resultSet.getInt("department_id"))));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
