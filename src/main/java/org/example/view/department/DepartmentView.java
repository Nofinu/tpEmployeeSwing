package org.example.view.department;

import lombok.Data;
import org.example.dao.DepartmentDao;
import org.example.model.Department;
import org.example.util.DepartmentListModel;
import org.example.view.MainPage;

import javax.swing.*;;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Data
public class DepartmentView {
    private MainPage mainPage;
    private JPanel departmentPanel;
    private DepartmentListModel departmentListModel;
    private JList<Department> departmentJList;
    private DepartmentDao departmentDao;
    private DepartmentView departmentView;

    public DepartmentView(MainPage mainPage) {
        this.mainPage = mainPage;
        this.departmentDao = new DepartmentDao();
        this.departmentView = this;
        departmentListModel = new DepartmentListModel();
        departmentPanel = new JPanel(new BorderLayout());

        departmentJList = new JList<>();
        departmentJList.setModel(departmentListModel);
        departmentPanel.add(departmentJList,BorderLayout.NORTH);

        refreshList();
        Container container = new Container();
        container.setLayout(new FlowLayout());

        JButton buttonAdd = new JButton("Add");
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDeparment addDeparment = new AddDeparment(departmentPanel,departmentView);
                addDeparment.start();
            }
        });
        JButton buttonEdit = new JButton("Edit");
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int SelectedId = departmentJList.getSelectedIndex();
                Department department = (Department) departmentListModel.getElementAt(SelectedId);
                EditDepartement editDepartement= new EditDepartement(departmentPanel,departmentView,department.getId());
                editDepartement.start();
            }
        });
        JButton buttonDelete = new JButton("Delete");
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int SelectedId = departmentJList.getSelectedIndex();
                Department department = (Department) departmentListModel.getElementAt(SelectedId);
                departmentDao.delete(department.getId());
                refreshList();
            }
        });
        JButton buttonSwitchView = new JButton("Employee");
        buttonSwitchView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPage.showEmployee();
            }
        });

        container.add(buttonAdd);
        container.add(buttonEdit);
        container.add(buttonDelete);
        container.add(buttonSwitchView);

        departmentPanel.add(container,BorderLayout.SOUTH);
    }

    public void refreshList (){
        departmentListModel.setList(departmentDao.findAll());
        departmentJList.setListData(departmentListModel.getlist().toArray(new Department[0]));
    }

}
