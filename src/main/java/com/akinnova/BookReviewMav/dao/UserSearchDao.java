//package com.akinnova.BookReviewMav.dao;
//
//import com.akinnova.BookReviewMav.entity.UserEntity;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class UserSearchDao {
//    private EntityManager entityManager;
//
//    public List<UserEntity> findAllBySimpleQuery(String firstName, String lastName, String email){
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
//
//        //Select * from userEntity class
//        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
//
//        //Prepare WHERE clause
//        //WHERE firstname like '%sam%'
//        Predicate firstnamePredicate = criteriaBuilder
//                .like(root.get("firstname"), "%" + firstName + "%");
//
//        Predicate lastnamePredicate = criteriaBuilder
//                .like(root.get("lastname"), "%" + lastName + "%");
//
//        Predicate emailPredicate = criteriaBuilder
//                .like(root.get("firstname"), "%" + email + "%");
//
//        Predicate firstnameOrLastnamePredicate = criteriaBuilder.or(
//                firstnamePredicate,
//                lastnamePredicate
//
//        );
//
//        //final query ==> select * from users where firstname like %sam%, or lastname like 'ade' or email
//        // like %email%
//        var andEmailPredicate = criteriaBuilder.and(firstnameOrLastnamePredicate, emailPredicate);
//        criteriaQuery.where(andEmailPredicate);
//
//        TypedQuery<UserEntity> query = entityManager.createQuery(criteriaQuery);
//
//        return query.getResultList();
//    }
//}
