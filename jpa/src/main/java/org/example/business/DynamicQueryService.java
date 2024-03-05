package org.example.business;

import org.example.dao.CourseDao;
import org.example.domain.Course;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicQueryService {

    private CourseDao courseDao;

    private EntityManagerFactory emf;

    private EntityManager em;

    public DynamicQueryService(CourseDao courseDao, EntityManagerFactory emf) {
        this.courseDao = courseDao;
        this.em = emf.createEntityManager();
    }

    public List<Course> findCoursesByCriteria(CourseFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);
        Root<Course> root = cq.from(Course.class);
        List<Predicate> predicates = new ArrayList<>();
        filter.getDepartment().ifPresent(d ->
                predicates.add(cb.equal(root.get("department"), d)));
        filter.getCredits().ifPresent(c ->
                predicates.add(cb.equal(root.get("credits"), c)));
        filter.getInstructor().ifPresent(i ->
                predicates.add(cb.equal(root.get("instructor"), i)));
        cq.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        return courseDao.findByCriteria(cq);
    }

}
