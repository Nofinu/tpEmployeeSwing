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

public class DepartmentDao {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet resultSet;


    public boolean add(Department department) {
        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("INSERT INTO `department`(`name`) VALUES(?) ");
            ps.setString(1, department.getName());
            int n = ps.executeUpdate();
            return n > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean update(Department department) {
        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("UPDATE department SET name = ? where id = ?");
            ps.setString(1, department.getName());
            ps.setInt(2, department.getId());
            int n = ps.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("DELETE FROM department WHERE id = ? ");
            ps.setInt(1, id);
            int n = ps.executeUpdate();
            return n > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Department findById(int id) {
        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("SELECT id,name FROM department where id = ? ");
            ps.setInt(1, id);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Department(resultSet.getInt("id"), resultSet.getString("name"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Department findByName(String name) {
        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("SELECT id,name FROM department where name = ? ");
            ps.setString(1, name);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Department(resultSet.getInt("id"), resultSet.getString("name"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Department> findAll() {
        con = ConnectionUtil.getConnection();
        List<Department> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT id,name FROM department ");
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new Department(resultSet.getInt("id"), resultSet.getString("name"),findEmlployeeAllByDepartmentId(resultSet.getInt("id"))));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employee> findEmlployeeAllByDepartmentId (int id){
        con = ConnectionUtil.getConnection();
        List<Employee> list = new ArrayList<>();
        ResultSet resultSetEmployee;
        try {
            ps = con.prepareStatement("SELECT id,firstname,lastname,role FROM employee where department_id = ?");
            ps.setInt(1,id);
            resultSetEmployee = ps.executeQuery();
            while(resultSetEmployee.next()){
                list.add(new Employee(resultSetEmployee.getInt("id"),
                        resultSetEmployee.getString("firstname"),
                        resultSetEmployee.getString("lastname"),
                        resultSetEmployee.getString("role").equals("MANAGER")? Role.MANAGER :resultSetEmployee.getString("role").equals("EMPLOYEE")? Role.EMPLOYEE : Role.RH
                ));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}