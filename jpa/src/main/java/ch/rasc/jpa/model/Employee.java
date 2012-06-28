package ch.rasc.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long employeeId;

	@NotBlank
	@Length(max = 30)
	private String employeeName;

	@NotBlank
	@Length(max = 30)
	private String employeeSurname;

	@Length(max = 50)
	private String job;

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(final long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(final String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeSurname() {
		return employeeSurname;
	}

	public void setEmployeeSurname(final String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}

	public String getJob() {
		return job;
	}

	public void setJob(final String job) {
		this.job = job;
	}
}
