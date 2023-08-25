package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private Role role;
    private Department department;

    public Employee(String firstName, String lastName, Role role, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
    }

    public Employee(int id, String firstName, String lastName, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    @Override
    public String toString() {
        return
                "id : " + id +
                ", firstName : '" + firstName +
                ", lastName : '" + lastName +
                ", role : " + role;
    }
}
