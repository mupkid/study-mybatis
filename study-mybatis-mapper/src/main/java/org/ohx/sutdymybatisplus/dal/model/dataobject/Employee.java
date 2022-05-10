package org.ohx.sutdymybatisplus.dal.model.dataobject;

import io.mybatis.provider.Entity;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author haoxian, ou
 * @date 2022/1/11 22:15
 */
@Data
@Entity.Table(value = "employees")
public class Employee {
    @Entity.Column(value = "employee_id", id = true, updatable = false, insertable = false)
    private Long employeeId;

    @Entity.Column(value = "first_name")
    private String firstName;

    @Entity.Column(value = "last_name")
    private String lastName;

    @Entity.Column(value = "email")
    private String email;

    @Entity.Column(value = "phone_number")
    private String phoneNumber;

    @Entity.Column(value = "hire_date")
    private LocalDate hireDate;

    @Entity.Column(value = "job_id")
    private String jobId;

    @Entity.Column(value = "salary")
    private Double salary;

    @Entity.Column(value = "commission_pct")
    private Double commissionPct;

    @Entity.Column(value = "manager_id")
    private Long managerId;

    @Entity.Column(value = "department_id")
    private Long departmentId;
}
