package org.example.dao;


import org.example.domain.Staff;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class StaffDao {
    private EntityManager em;
    public StaffDao(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    public List<Staff> find(int pageNumber, int pageSize){
        TypedQuery<Staff> query = em.createQuery(
                "SELECT s FROM Staff s ORDER by s.member.lastName ASC", Staff.class);
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

}
