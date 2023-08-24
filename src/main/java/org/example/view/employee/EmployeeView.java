package org.example.view.employee;

import lombok.Data;
import org.example.Main;
import org.example.dao.EmployeeDao;
import org.example.model.Employee;
import org.example.view.MainPage;
import org.example.view.department.DepartmentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Data
public class EmployeeView {
    private MainPage mainPage;
    private JPanel employeePanel;
    private DepartmentView departmentView;
    private EmployeeDao employeeDao;
    private static String[] columnNames = {"Id","FirstName","LastName","Role"};
    private DefaultTableModel dtm = new DefaultTableModel(null, columnNames) {

        @Override
        public Class<?> getColumnClass(int col) {
            return getValueAt(0, col).getClass();
        }
    };

    public EmployeeView(MainPage mainPage, DepartmentView departmentView) {
        employeeDao = new EmployeeDao();
        this.mainPage = mainPage;
        employeePanel = new JPanel(new BorderLayout());
        JTable table = new JTable(dtm);
        employeePanel.add(table,BorderLayout.NORTH);

        Container container = new Container();
        container.setLayout(new FlowLayout());

        JButton buttonAdd = new JButton("Add");
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployee addEmployee = new AddEmployee(employeePanel);
                addEmployee.start();
            }
        });
        JButton buttonEdit = new JButton("Edit");
        JButton buttonDelete = new JButton("Delete");
        JButton buttonSwitchView = new JButton("Departement");
        buttonSwitchView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                departmentView.refreshList();
                mainPage.showDepartment();
            }
        });

        container.add(buttonAdd);
        container.add(buttonEdit);
        container.add(buttonDelete);
        container.add(buttonSwitchView);

        refreshTab();
        employeePanel.add(container,BorderLayout.SOUTH);
    }

    public void refreshTab (){
        List<Employee> employees = employeeDao.findAll();
        dtm = new DefaultTableModel(null, columnNames) {
            @Override
            public Class<?> getColumnClass(int col) {
                return getValueAt(0, col).getClass();
            }
        };
        dtm.addRow(new Object[]{"id","FirstName","LastName","Rôle"});
        employees.forEach(e -> {
            dtm.addRow(new Object[]{e.getId(),e.getFirstName(),e.getLastName(),e.getRole().toString()});
        });
    }
}
