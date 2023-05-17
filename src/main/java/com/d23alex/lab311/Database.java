package com.d23alex.lab311;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Model;
import jakarta.persistence.*;

@Model
@ApplicationScoped
public class Database {
    public static void save(AreaChecking.Check check) {
        final Consumer<EntityManager> savingAction = entityManager -> {
            CheckORM ormCheck = toPersistenceType(check);
            entityManager.persist(ormCheck);
        };
        performORMAction(savingAction);
    }

    public static List<AreaChecking.Check> getNLast(int n) {
        return getAllSortedByDate().stream().limit(n).toList();
    }

    public static List<AreaChecking.Check> getAllSortedByDate() {
        final List<AreaChecking.Check> checks = new ArrayList<>();
        final Consumer<EntityManager> ormAction = entityManager -> {
            TypedQuery<CheckORM> query = entityManager.createNamedQuery("checks.allSortedByDate", CheckORM.class);
            query.getResultList().forEach(ormCheck -> checks.add(toCheck(ormCheck)));
        };
        performORMAction(ormAction);
        return checks;
    }

    private static CheckORM toPersistenceType(AreaChecking.Check check) {
        final CheckORM result = new CheckORM();
        result.setX(check.userInputs().point().x());
        result.setY(check.userInputs().point().y());
        result.setR(check.userInputs().r());
        result.setAreaContainsPoint(check.areaContainsPoint());
        result.setRequestDate(new java.sql.Date(check.requestDate().getTime()));
        result.setCalculationTime(check.calculationTime());
        return result;
    }

    private static AreaChecking.Check toCheck(CheckORM checkORM) {
        return new AreaChecking.Check(
                new AreaChecking.UserInputs(new AreaChecking.Point(checkORM.getX(), checkORM.getY()), checkORM.getR()),
                checkORM.getAreaContainsPoint(),
                checkORM.getRequestDate(),
                checkORM.getCalculationTime()
        );
    }

    private static void performORMAction(Consumer<EntityManager> action) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            action.accept(entityManager);
            transaction.commit();
        } finally {
            if (transaction.isActive())
                transaction.rollback();
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
