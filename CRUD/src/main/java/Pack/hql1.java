package Pack;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class hql1 {
    public static void main(String[] args) {
        Configuration cf = new Configuration().configure("hibernate.cfg.xml");
        cf.addAnnotatedClass(Employee.class);

        SessionFactory sf = cf.buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        // Correct HQL update query with matching case-sensitive table and field names
        String hql = "update Employee set salary = :salary where name = :name";
        Query query = session.createQuery(hql);
        query.setParameter("name", "Akanksha");
        query.setParameter("salary", 50000);

        int modifications = query.executeUpdate();

        transaction.commit();
        session.close();
        sf.close();

        System.out.println(modifications + " record(s) updated.");
    }

	
	
}
