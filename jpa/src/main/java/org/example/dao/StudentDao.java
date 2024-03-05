package org.example.dao;

import org.example.domain.Student;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDao {
    EntityManager em;

    public StudentDao(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    List<Student> findByFullTime(boolean fullTime){
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s WHERE s.fullTime = :fullTime" , Student.class);
        return query.setParameter("fullTime", fullTime).getResultList();
    }

    List<Student> findByAge(Integer age) {
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s WHERE s.age = :age" , Student.class);
        return query.setParameter("age", age).getResultList();
    }

    List<Student> findByLastName(String lastName){
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s WHERE s.attendee.lastName = :lastName" , Student.class);
        return query.setParameter("lastName", lastName).getResultList();
    }


    Optional<Student> findOldest() {
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s ORDER BY age DESC" , Student.class);
        return Optional.ofNullable(query.setMaxResults(1).getSingleResult());
    }

    List<Student> findByFirstAndLastName(String firstName, String lastName){
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s WHERE s.attendee.firstName = :firstName and s.attendee.lastName = :lastName" , Student.class);
        return query.setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    List<Student> findByAgeLessThan(int age) {
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s where s.age < :age" , Student.class);
        return query.setParameter("age", age)
                .getResultList();
    }

    List<Student> findSimilarLastName(String nameCriteria) {
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s where s.attendee.lastName like :nameCriteria" , Student.class);
        return query.setParameter("nameCriteria", nameCriteria)
                .getResultList();
    }

    Optional<Student> findFirstInAlphabet(){
        TypedQuery<Student> query = em.createQuery(
                "Select s FROM Student  s ORDER BY  s.attendee.lastName ASC" , Student.class);
        return Optional.ofNullable(query.setMaxResults(1).getSingleResult());
    }

    List<Student> find3Oldest() {
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s ORDER BY age DESC" , Student.class);
        return query.setMaxResults(3).getResultList();
    }
}
