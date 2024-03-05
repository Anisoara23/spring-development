package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaQuery;
import org.example.domain.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDao {
    private EntityManager em;

    public CourseDao(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    public List<Course> findByCriteria(CriteriaQuery<Course> criteria) {
        return em.createQuery(criteria).getResultList();
    }
}