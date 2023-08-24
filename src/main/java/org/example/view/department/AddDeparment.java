package org.example.view.department;

import org.example.dao.DepartmentDao;
import org.example.model.Department;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDeparment extends JDialog {

    private JPanel mainPage;
    private DepartmentDao departmentDao;
    private JDialog jDialog;
    private DepartmentView departmentView;
    public AddDeparment(JPanel mainPage,DepartmentView departmentView) {
        super();
        this.mainPage = mainPage;
        this.departmentView = departmentView;
        departmentDao = new DepartmentDao();
        jDialog = this;
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
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Department department = new Department(nameTextField.getText());
                if(departmentDao.add(department)){
                    dispose();
                    departmentView.refreshList();
                    JOptionPane.showMessageDialog(jDialog,"Department add");

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
        this.add(addButton,c);


        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
