package com.elcom.repository;

import com.elcom.model.User;
import com.elcom.project.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class UserCustomizeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCustomizeRepository.class);

    private SessionFactory sessionFactory;

    @Autowired
    public UserCustomizeRepository(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null)
            throw new NullPointerException("factory is not a hibernate factory");
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }
    public User findById(Long id) {

        Session session = openSession();
        try {
            User user = session.load(User.class, id);
            return user;
        }catch(EntityNotFoundException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return null;
    }

    public User findByUsername(String userName) {
        Session session = openSession();
        Object result = null;
        try {
            Query query = session.createNativeQuery("SELECT * FROM users WHERE username = ?", User.class);
            query.setParameter(1, userName);
            result = query.getSingleResult();
        }catch(NoResultException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return result != null ? (User) result : null;
    }
//    public boolean insertTest() {
//        Session session = openSession();
//        try {
//            for( int i=1; i<=4; i++ ) {
//                Query query = session.createNativeQuery(" insert into user(userID,  username, password, full_name) "
//                        + " values(:userName, :password, :fullName ) ");
//                query.setParameter("userName", "anhdv_" + i);
//                query.setParameter("password", "anhdv_pw_" + i);
//                query.setParameter("fullName", "do viet anh_" + i);
//                query.executeUpdate();
//            }
//        }catch(NoResultException ex) {
//            LOGGER.error(ex.toString());
//        } finally {
//            closeSession(session);
//        }
//        return true;
//    }
    private Session openSession() {
        Session session = this.sessionFactory.openSession();
        return session;
    }

    private void closeSession(Session session) {
        if( session.isOpen() ) {
            session.disconnect();
            session.close();
        }
    }
}
