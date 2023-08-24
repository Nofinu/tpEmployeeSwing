package org.example.util;

import org.example.model.Department;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentListModel extends AbstractListModel {

    private List<Department> departmentList;

    public DepartmentListModel() {
        this.departmentList = new ArrayList<>();
    }

    public void addTodo(Department department) {
        departmentList.add(department);
        System.out.println(departmentList);
    }

    public void setList (List<Department> departmentList){
        this.departmentList = departmentList;
    }

    public List<Department> getlist (){
        return this.departmentList;
    }

    public void remove (int index){
        this.departmentList.remove(index);
    }

    @Override
    public int getSize() {
        return departmentList.size();
    }
    @Override
    public Object getElementAt(int index) {
        Department result = null;
        if (index < getSize()) {
            result = departmentList.get(index);
        }
        return result;
    }
}