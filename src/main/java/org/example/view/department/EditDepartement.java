package org.example.view.department;

import org.example.dao.DepartmentDao;
import org.example.model.Department;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditDepartement extends JDialog{
    private JPanel mainPage;
    private DepartmentDao departmentDao;
    private JDialog jDialog;
    private DepartmentView departmentView;
    private int id;
    public EditDepartement(JPanel mainPage,DepartmentView departmentView,int id) {
        super();
        this.mainPage = mainPage;
        this.departmentView = departmentView;
        departmentDao = new DepartmentDao();
        jDialog = this;
        this.id = id;
    }

    public void start() {
        this.setSize(800,300);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths= new int[1];
        gridBagLayout.rowHeights = new int[3];
        this.setLayout(gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();


        JLabel nameLabel = new JLabel("Name :");
        JTextField nameTextField = new JTextField(20);
        Department department = departmentDao.findById(id);
        nameTextField.setText(department.getName());
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Department department = new Department(id,nameTextField.getText());
                if(departmentDao.update(department)){
                    dispose();
                    departmentView.refreshList();
                    JOptionPane.showMessageDialog(jDialog,"Department update");
                }
                else{
                    JOptionPane.showMessageDialog(jDialog,"Error !");
                }
            }
        });
        c.gridx=0;
        c.gridy=0;
        this.add(nameLabel,c);
        c.gridx=0;
        c.gridy=1;
        this.add(nameTextField,c);
        c.gridx=0;
        c.gridy=2;
        this.add(updateButton,c);


        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
