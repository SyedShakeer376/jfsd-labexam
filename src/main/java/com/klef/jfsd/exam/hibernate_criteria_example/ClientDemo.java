package com.klef.jfsd.exam.hibernate_criteria_example;


import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Configure Hibernate
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class);
        SessionFactory factory = cfg.buildSessionFactory();

        // Insert Records
        insertProjects(factory);

        // Perform Aggregate Functions
        performAggregateFunctions(factory);

        factory.close();
    }

    private static void insertProjects(SessionFactory factory) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Project p1 = new Project();
        p1.setProjectName("AI Development");
        p1.setDuration(12);
        p1.setBudget(500000);
        p1.setTeamLead("Alice");

        Project p2 = new Project();
        p2.setProjectName("Cloud Migration");
        p2.setDuration(18);
        p2.setBudget(750000);
        p2.setTeamLead("Bob");

        Project p3 = new Project();
        p3.setProjectName("Web Application");
        p3.setDuration(6);
        p3.setBudget(200000);
        p3.setTeamLead("Charlie");

        session.save(p1);
        session.save(p2);
        session.save(p3);

        tx.commit();
        session.close();
        System.out.println("Projects inserted successfully.");
    }

    private static void performAggregateFunctions(SessionFactory factory) {
        Session session = factory.openSession();

        // Count
        Criteria countCriteria = session.createCriteria(Project.class);
        countCriteria.setProjection(Projections.rowCount());
        System.out.println("Total Projects: " + countCriteria.uniqueResult());

        // Max Budget
        Criteria maxBudgetCriteria = session.createCriteria(Project.class);
        maxBudgetCriteria.setProjection(Projections.max("budget"));
        System.out.println("Maximum Budget: " + maxBudgetCriteria.uniqueResult());

        // Min Budget
        Criteria minBudgetCriteria = session.createCriteria(Project.class);
        minBudgetCriteria.setProjection(Projections.min("budget"));
        System.out.println("Minimum Budget: " + minBudgetCriteria.uniqueResult());

        // Sum of Budgets
        Criteria sumBudgetCriteria = session.createCriteria(Project.class);
        sumBudgetCriteria.setProjection(Projections.sum("budget"));
        System.out.println("Total Budget: " + sumBudgetCriteria.uniqueResult());

        // Average Budget
        Criteria avgBudgetCriteria = session.createCriteria(Project.class);
        avgBudgetCriteria.setProjection(Projections.avg("budget"));
        System.out.println("Average Budget: " + avgBudgetCriteria.uniqueResult());

        session.close();
    }
}
