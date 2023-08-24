package org.example.view.employee;

import org.example.dao.DepartmentDao;
import org.example.dao.EmployeeDao;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.view.department.DepartmentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddEmployee extends JDialog{

    private JPanel mainPage;
    private EmployeeDao employeeDao;
    private DepartmentDao departmentDao;
    private JDialog jDialog;

    public AddEmployee(JPanel mainPage) {
        super();
        this.mainPage = mainPage;
        employeeDao = new EmployeeDao();
        departmentDao = new DepartmentDao();
        jDialog = this;
    }

    public void start() {
        this.setSize(800,300);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths= new int[2];
        gridBagLayout.rowHeights = new int[5];
        this.setLayout(gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();


        JLabel firstNameLabel = new JLabel("FirstName :");
        firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        JTextField firstNameTextField = new JTextField(20);

        JLabel lastNameLabel = new JLabel("LastName :");
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        JTextField lastNameTextField = new JTextField(20);

        Container container = new Container();
        container.setLayout(new FlowLayout());

        JLabel roleLabel = new JLabel("Role :");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JRadioButton manager = new JRadioButton("Manager");
        manager.setFont(new Font("Arial", Font.PLAIN, 15));
        manager.setSelected(true);
        manager.setSize(75, 20);;
        container.add(manager);

        JRadioButton employee = new JRadioButton("Employee");
        employee.setFont(new Font("Arial", Font.PLAIN, 15));
        employee.setSelected(false);
        employee.setSize(80, 20);
        container.add(employee);

        JRadioButton rh = new JRadioButton("RH");
        rh.setFont(new Font("Arial", Font.PLAIN, 15));
        rh.setSelected(false);
        rh.setSize(80, 20);
        container.add(rh);

        ButtonGroup role = new ButtonGroup();
        role.add(manager);
        role.add(employee);
        role.add(rh);

        JLabel departementLabel = new JLabel("Departement :");
        departementLabel.setFont(new Font("Arial", Font.PLAIN, 20));



        JComboBox<String> departementList = new JComboBox<>(findDepartment().toArray(new String[0]));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee employee = new Employee();
                if(employeeDao.add(employee)){
                    dispose();
                    JOptionPane.showMessageDialog(jDialog,"Department add");

                }
                else{
                    JOptionPane.showMessageDialog(jDialog,"Error !");
                }
            }
        });
        c.gridx=0;
        c.gridy=0;
        this.add(firstNameLabel,c);
        c.gridx=1;
        c.gridy=0;
        this.add(firstNameTextField,c);

        c.gridx=0;
        c.gridy=1;
        this.add(lastNameLabel,c);
        c.gridx=1;
        c.gridy=1;
        this.add(lastNameTextField,c);

        c.gridx=0;
        c.gridy=2;
        this.add(roleLabel,c);
        c.gridx=1;
        c.gridy=2;
        this.add(container,c);

        c.gridx=0;
        c.gridy=3;
        this.add(departementLabel,c);
        c.gridx=1;
        c.gridy=3;
        this.add(departementList,c);

        c.gridx=0;
        c.gridy=4;
        c.gridwidth = 2;
        this.add(addButton,c);


        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private List<String> findDepartment (){
        List<Department> departments = departmentDao.findAll();
        List<String> departmentName = new ArrayList<>();
        departments.forEach(d -> {
            departmentName.add(d.getName());
        });
        return departmentName;
    }
}
