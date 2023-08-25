package org.example.view.employee;

import org.example.dao.DepartmentDao;
import org.example.dao.EmployeeDao;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EditEmployee extends JDialog {
    private JPanel mainPage;
    private EmployeeDao employeeDao;
    private DepartmentDao departmentDao;
    private JDialog jDialog;
    private List<String> departementNameList;
    private EmployeeView employeeView;
    private int id;

    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JRadioButton manager;
    private JRadioButton employee;
    private JRadioButton rh;
    private JComboBox<String> departementList;

    public EditEmployee(JPanel mainPage,EmployeeView employeeView,int id) {
        super();
        this.mainPage = mainPage;
        employeeDao = new EmployeeDao();
        departmentDao = new DepartmentDao();
        jDialog = this;
        this.employeeView = employeeView;
        this.id = id;
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
        firstNameTextField = new JTextField(20);

        JLabel lastNameLabel = new JLabel("LastName :");
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lastNameTextField = new JTextField(20);

        Container container = new Container();
        container.setLayout(new FlowLayout());

        JLabel roleLabel = new JLabel("Role :");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        manager = new JRadioButton("Manager");
        manager.setFont(new Font("Arial", Font.PLAIN, 15));
        manager.setSelected(true);
        manager.setSize(75, 20);;
        container.add(manager);

        employee = new JRadioButton("Employee");
        employee.setFont(new Font("Arial", Font.PLAIN, 15));
        employee.setSelected(false);
        employee.setSize(80, 20);
        container.add(employee);

        rh = new JRadioButton("RH");
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


        departementNameList = findDepartment();
        departementList = new JComboBox<>(departementNameList.toArray(new String[0]));


        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Role roleSelected;
                if(manager.isSelected()){
                    roleSelected = Role.MANAGER;
                }
                else if (employee.isSelected()) {
                    roleSelected = Role.EMPLOYEE;
                }
                else {
                    roleSelected=Role.RH;
                }
                Employee employee = new Employee(id,firstNameTextField.getText(),lastNameTextField.getText(),roleSelected,departmentDao.findByName(departementNameList.get(departementList.getSelectedIndex())));
                if(employeeDao.update(employee)){
                    dispose();
                    JOptionPane.showMessageDialog(jDialog,"Employee add");
                    employeeView.refreshTab();
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

        setValueEntry();

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private java.util.List<String> findDepartment (){
        java.util.List<Department> departments = departmentDao.findAll();
        List<String> departmentName = new ArrayList<>();
        departments.forEach(d -> {
            departmentName.add(d.getName());
        });
        return departmentName;
    }

    private void setValueEntry (){
        Employee employeefind =  employeeDao.findById(id);
        firstNameTextField.setText(employeefind.getFirstName());
        lastNameTextField.setText(employeefind.getLastName());
        if(employeefind.getRole().toString().equals("MANAGER")){
            manager.setSelected(true);
        }else if (employeefind.getRole().toString().equals("EMPLOYEE")){
            employee.setSelected(true);
        }else{
            rh.setSelected(true);
        }
        String departementName = departmentDao.findById(employeefind.getDepartment().getId()).getName();
        departementList.setSelectedItem(departementName);
    }
}
