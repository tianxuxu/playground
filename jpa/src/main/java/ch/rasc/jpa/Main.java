package ch.rasc.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ch.rasc.jpa.config.AppConfig;
import ch.rasc.jpa.model.Employee;
import ch.rasc.jpa.repository.EmployeeRepository;

public class Main {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		// ctx.scan("ch.rasc.config");
		ctx.refresh();

		System.out.println(ctx);

		EmployeeRepository rep = ctx.getBean(EmployeeRepository.class);

		Employee newEmployee = new Employee();
		newEmployee.setEmployeeName("Schaer");
		newEmployee.setEmployeeSurname("Ralph");
		rep.update(newEmployee);

		List<Employee> employees = rep.getAllEmployees();
		for (Employee employee : employees) {
			System.out.println(employee.getEmployeeId());
		}

		EntityManagerFactory emf = ctx.getBean(EntityManagerFactory.class);
		EntityManager em = emf.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<Employee> root = query.from(Employee.class);
		query.select(builder.count(root)).distinct(true);
		Long result = em.createQuery(query).getSingleResult();

		System.out.println(result);

	}

}
