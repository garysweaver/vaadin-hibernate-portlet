package com.vaadin.demo.workoutlog;

import java.util.Calendar;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.classic.Session;
import org.hibernate.dialect.HSQLDialect;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static Type defaultType;

    static {
        try {
            AnnotationConfiguration cnf = new AnnotationConfiguration();
            cnf.setProperty(Environment.DRIVER, "org.hsqldb.jdbcDriver");
            cnf.setProperty(Environment.URL, "jdbc:hsqldb:mem:Workout");
            cnf.setProperty(Environment.USER, "sa");
            cnf.setProperty(Environment.DIALECT, HSQLDialect.class.getName());
            cnf.setProperty(Environment.SHOW_SQL, "true");
            cnf.setProperty(Environment.HBM2DDL_AUTO, "create-drop");
            cnf
                    .setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS,
                            "thread");
            cnf.addAnnotatedClass(Workout.class);
            cnf.addAnnotatedClass(Type.class);
            sessionFactory = cnf.buildSessionFactory();

            insertExampleTypes();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void insertExampleTypes() {
        Session sess = getSessionFactory().getCurrentSession();
        sess.beginTransaction();
        Type type;

        type = new Type();
        type.setTitle("Running");
        sess.save(type);

        defaultType = type;
        System.err.println("Default type id : " + defaultType.getId());

        type = new Type();
        type.setTitle("MTB");
        sess.save(type);

        type = new Type();
        type.setTitle("Trecking");
        sess.save(type);

        type = new Type();
        type.setTitle("Swimming");
        sess.save(type);

        type = new Type();
        type.setTitle("Orienteering");
        sess.save(type);

        type = new Type();
        type.setTitle("Football");
        sess.save(type);

    }

    public static void insertExampleData(int trainingsToLoad) {
        Session sess = getSessionFactory().getCurrentSession();
        sess.beginTransaction();

        // insert some sample data
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);

        String[] titles = new String[] { "A short easy one", "intervals",
                "very long", "just shaking legs after work",
                "long one with Paul", "test run" };

        c.add(Calendar.DATE, -trainingsToLoad);

        Random rnd = new Random();

        Workout r;

        for (int i = 0; i < trainingsToLoad; i++) {
            r = new Workout();
            c.set(Calendar.HOUR_OF_DAY,
                    12 + (rnd.nextInt(11) - rnd.nextInt(11)));
            r.setDate(c.getTime());
            r.setTitle(titles[rnd.nextInt(titles.length)]);
            r.setKilometers(Math.round(rnd.nextFloat() * 30));
            r.setTrainingType(defaultType);
            sess.save(r);
            c.add(Calendar.DATE, 1);
        }

    }
}
