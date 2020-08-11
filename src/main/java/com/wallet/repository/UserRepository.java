/*
package com.wallet.repository;

import com.wallet.domain.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

*/
/**
 * Добавить ошибку получения пользователя.
 *//*

@Repository
public class UserRepository {
    final static Logger LOGGER = Logger.getLogger(UserRepository.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public User getUser(Long userId) {
        User result = null;
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction transaction = session.beginTransaction();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("userId"), userId.toString()));
            result = session.createQuery(query).getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            LOGGER.info("session closed");
            session.close();
        }
        return result;
    }



}
*/
