package ch.rasc.envers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.exception.RevisionDoesNotExistException;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.google.common.collect.Sets;
import com.mysema.query.jpa.hibernate.HibernateQuery;

public class Main {

	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		configuration.setProperty(AvailableSettings.DRIVER, "org.h2.Driver");
		configuration.setProperty(AvailableSettings.URL, "jdbc:h2:./db/test");
		configuration.setProperty(AvailableSettings.USER, "sa");
		configuration.setProperty(AvailableSettings.PASS, "");
		configuration.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
		configuration.setProperty(AvailableSettings.SHOW_SQL, "true");

		configuration.addAnnotatedClass(Firma.class);
		configuration.addAnnotatedClass(Mitarbeiter.class);

		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		Session session = sessionFactory.openSession();

		// // Revision 1
		// session.beginTransaction();
		// insert(session);
		// session.getTransaction().commit();
		//
		// // Update Firma Revision 2
		// session.beginTransaction();
		// Firma firma = updateFirma(session);
		// session.getTransaction().commit();
		//
		// // Neuer Mitarbeiter Revision 3
		// session.beginTransaction();
		// addMitarbeiter(firma);
		// session.getTransaction().commit();
		//
		// // Mitarbeiter updaten Revision 4
		// session.beginTransaction();
		// updateMitarbeiter(session);
		// session.getTransaction().commit();
		//
		// // Mitarbeiter löschen Revision 5
		// session.beginTransaction();
		// deleteMitarbeiter(session);
		// session.getTransaction().commit();

		// Queries
		AuditReader reader = AuditReaderFactory.get(session);
		Firma oldFirma = reader.find(Firma.class, 1, 1);

		System.out.println(oldFirma.getStrasse()); // Output: street
		System.out.println(oldFirma.getName()); // Output: null

		List<Number> revisions = reader.getRevisions(Firma.class, 1);
		for (Number rev : revisions) {
			System.out.println(rev);
		}

		System.out.println(reader.getRevisionDate(1));

		try {
			Calendar cal = new GregorianCalendar(2012, Calendar.NOVEMBER, 3);
			System.out.println(reader.getRevisionNumberForDate(cal.getTime()));
		} catch (RevisionDoesNotExistException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		AuditQuery query = reader.createQuery().forEntitiesAtRevision(Mitarbeiter.class, 1);
		query.add(AuditEntity.relatedId("firma").eq(1));

		List<Mitarbeiter> mitarbeiterList = query.getResultList();
		for (Mitarbeiter mitarbeiter : mitarbeiterList) {
			System.out.println(mitarbeiter.getName());
		}

		query = reader.createQuery().forRevisionsOfEntity(Mitarbeiter.class, false, true);
		List<Object[]> rersults = query.getResultList();
		for (Object[] result : rersults) {
			Mitarbeiter mitarbeiter = (Mitarbeiter) result[0];
			DefaultRevisionEntity revEntity = (DefaultRevisionEntity) result[1];
			RevisionType revType = (RevisionType) result[2];

			System.out.println("Revision     : " + revEntity.getId());
			System.out.println("Revision Date: " + revEntity.getRevisionDate());
			System.out.println("Type         : " + revType);
			System.out.println("Mitarbeiter  : " + mitarbeiter.getName());

			System.out.println("------------------------------------------------");
		}

		session.close();
		sessionFactory.close();
	}

	private static void insert(Session session) {
		Firma firma = new Firma();
		firma.setName("Company A");
		firma.setOrt("Zurich");
		firma.setStrasse("Street");

		Set<Mitarbeiter> mitarbeiterSet = Sets.newHashSet();

		Mitarbeiter mitarbeiter = new Mitarbeiter();
		mitarbeiter.setFirma(firma);
		mitarbeiter.setName("Muster");
		mitarbeiter.setVorname("Felix");
		mitarbeiter.setOrt("Zurich");
		mitarbeiter.setStrasse("Seestrasse 1");
		mitarbeiterSet.add(mitarbeiter);

		mitarbeiter = new Mitarbeiter();
		mitarbeiter.setFirma(firma);
		mitarbeiter.setName("Meier");
		mitarbeiter.setVorname("Jolanda");
		mitarbeiter.setOrt("Bern");
		mitarbeiter.setStrasse("Bahnhofstrasse 10");
		mitarbeiterSet.add(mitarbeiter);

		firma.setMitarbeiter(mitarbeiterSet);

		session.save(firma);
	}

	private static Firma updateFirma(Session session) {
		Firma firma = new HibernateQuery(session).from(QFirma.firma).where(QFirma.firma.name.eq("Company A"))
				.singleResult(QFirma.firma);
		firma.setStrasse("Neue Strasse");
		return firma;
	}

	private static void addMitarbeiter(Firma firma) {
		Mitarbeiter mitarbeiter = new Mitarbeiter();
		mitarbeiter.setFirma(firma);
		mitarbeiter.setName("Müller");
		mitarbeiter.setVorname("Andreas");
		mitarbeiter.setOrt("Olten");
		mitarbeiter.setStrasse("Rheinstrasse 10");
		firma.getMitarbeiter().add(mitarbeiter);
	}

	private static void updateMitarbeiter(Session session) {
		Mitarbeiter mitarbeiter = new HibernateQuery(session).from(QMitarbeiter.mitarbeiter)
				.where(QMitarbeiter.mitarbeiter.vorname.eq("Felix").and(QMitarbeiter.mitarbeiter.name.eq("Muster")))
				.singleResult(QMitarbeiter.mitarbeiter);

		mitarbeiter.setStrasse("Hauptstrasse 10");
		mitarbeiter.setOrt("Biel");
	}

	private static void deleteMitarbeiter(Session session) {

		Mitarbeiter mitarbeiter = new HibernateQuery(session).from(QMitarbeiter.mitarbeiter)
				.where(QMitarbeiter.mitarbeiter.vorname.eq("Jolanda").and(QMitarbeiter.mitarbeiter.name.eq("Meier")))
				.singleResult(QMitarbeiter.mitarbeiter);

		mitarbeiter.getFirma().getMitarbeiter().remove(mitarbeiter);
		session.delete(mitarbeiter);
	}

}
