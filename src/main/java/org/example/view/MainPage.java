package org.example.view;

import lombok.Data;
import org.example.view.department.DepartmentView;
import org.example.view.employee.EmployeeView;

import javax.swing.*;
import java.awt.*;

@Data
public class MainPage {
    private JPanel mainPagePanel;
    private CardLayout cardLayout;

    public MainPage() {
        cardLayout = new CardLayout();
        mainPagePanel = new JPanel(cardLayout);
        DepartmentView departmentView = new DepartmentView(this);
        EmployeeView employeeView = new EmployeeView(this,departmentView);

        mainPagePanel.add(employeeView.getEmployeePanel(),"EmployeePanel");
        mainPagePanel.add(departmentView.getDepartmentPanel(),"DepartmentPanel");
    }

    public void showEmployee (){
        cardLayout.show(mainPagePanel, "EmployeePanel");
    }

    public void showDepartment (){
        cardLayout.show(mainPagePanel, "DepartmentPanel");
    }
}
