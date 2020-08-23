package com.rew3.common.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.application.AuthenticatedUser;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.*;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class HibernateUtilV2 {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    private static Session s = null;
    private static boolean debug = true;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
                // so destroy it manually.
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        // A SessionFactory is set up once for an application!

        return sessionFactory;
    }

    public static boolean isSessionOpen() {
        System.out.println("=> Check Session Open");
        return (s != null && s.isOpen());
    }

    public static Session openSession() {
        System.out.println("=> Get Session");
        SessionFactory sf = getSessionFactory();
        if (!HibernateUtilV2.isSessionOpen()) {
            if (debug) {
                System.out.println("=> Session Opened");
            }
            s = sf.openSession();
        }

        return s;
    }

    public static boolean closeSession() {
        boolean done = false;
        if (s.isOpen()) {
            if (debug) {
                System.out.println("=> Session Closed");
            }
            s.close();
            done = true;
        }
        return done;
    }

    public static Transaction getTransaction() {
        if (debug) {
            System.out.println("=> Get Transaction");
        }
        Transaction trx = s.getTransaction();
        return trx;
    }

    public static Transaction beginTransaction() {
        System.out.println("Begin Transaction");
        Transaction trx = getTransaction();
        if (trx == null || !trx.isActive()) {
            if (debug) {
                System.out.println("=> New Transaction Created");
            }
            trx = s.beginTransaction();
        }

        return trx;
    }

    public static boolean rollbackTransaction(Transaction trx) {
        boolean done = false;
        trx.rollback();
        done = true;
        if (debug) {
            System.out.println("Tried Rollback Transaction: " + done);
        }
        return done;
    }

    public static boolean commitTransaction(Transaction trx) {
        boolean done = false;
        trx.commit();
        done = true;
        if (debug) {
            System.out.println("Tried Commit Transaction: " + done);
        }
        return done;
    }

    public static synchronized List<Object> select(String hql, Map<String, Object> params, Integer limit, Integer offset) {

        List<Object> results = null;
        try {

            Query query = createQuery(hql, params, limit, offset);
            results = query.list();


            // For ACL security purposes
            /*
            results = results.stream().map(c -> (AbstractEntity) c).
                    filter(c -> c.hasReadPermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())
                            & c.getStatus() == Flags.EntityStatus.ACTIVE.toString()).collect(Collectors.toList());
            */

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return results;
    }

    public static synchronized List select(String hql, Map<String, Object> params, Integer limit) {
        return select(hql, params, limit, null);
    }

    public static synchronized List select(String hql, Map<String, Object> params) {
        return select(hql, params, null, null);
    }

    public static synchronized List select(String hql) {
        return select(hql, null, null, null);
    }

    public static synchronized List selectSQL(String sql, Map<String, Object> params, Integer limit, Integer offset) {
        List results = null;
        try {
            Query query = createSQLQuery(sql, params, limit, offset);
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return results;
    }

    public static synchronized List selectSQL(String sql, Map<String, Object> params, Integer limit) {
        return selectSQL(sql, params, limit, null);
    }

    public static synchronized List selectSQL(String sql, Map<String, Object> params) {
        return selectSQL(sql, params, null, null);
    }

    public static synchronized List selectSQL(String sql) {
        return selectSQL(sql, null, null, null);
    }

    public static synchronized boolean query(String hql, Map<String, Object> params, Transaction aTrx) {
        boolean success = false;

        Session session = null;
        Transaction trx = aTrx;
        try {
            Query query = createQuery(hql, params, null, null);
            success = (query.executeUpdate() > 0);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
        return success;
    }

    public static synchronized boolean query(String hql, Map<String, Object> params) {
        return query(hql, params, null);
    }

    public static synchronized Object save(Object obj) throws JsonProcessingException {
        return save(obj, null);
    }

    public static synchronized Object save(Object obj, Transaction aTrx) throws JsonProcessingException {


        Session session=HibernateUtilV2.openSession();
        Transaction tx = null;
        try {
           // session = HibernateUtilV2.openSession();
            Transaction trx = session.beginTransaction();
            AbstractEntity entity = (AbstractEntity) obj;
            entity.setDefaultAcl();
            entity.setVersion();
            entity.setMember(Authentication.getMemberId());
            session.merge(obj);
            session.flush();
            trx.commit();
            session.close();
            return obj;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e; // or display error message
        } finally {
            session.close();
        }
    }

    public static synchronized Object defaultSave(Object obj, Transaction aTrx) throws JsonProcessingException {


        Session session = null;
        try {
            session = HibernateUtilV2.openSession();

            session.saveOrUpdate(obj);


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {

        }
        return obj;
    }

    public static synchronized Object defaultSave(Object obj) throws JsonProcessingException {


        // Session session = null;
        try {
            // session = HibernateUtilV2.openSession();

            s.saveOrUpdate(obj);


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {

        }
        return obj;
    }


    public static synchronized Object save(Object obj, Transaction aTrx, boolean isNew) throws
            JsonProcessingException {


        Session session = null;
        Transaction trx = aTrx;
        try {
            session = HibernateUtilV2.openSession();
            AbstractEntity entity = (AbstractEntity) obj;
            entity.setDefaultAcl();


            //saving meta info's
            AuthenticatedUser user = Authentication.getAuthenticatedUser();
            if (isNew) {
                entity.setCreatedAt(Rew3Date.convertToUTC(Instant.now().toString()));
                entity.setCreatedById(user.getId());
                entity.setCreatedByFirstName(user.getFirstName());
                entity.setCreatedByLastName(user.getLastName());
            } else {
                entity.setLastModifiedAt(Rew3Date.convertToUTC(Instant.now().toString()));
                entity.setModifiedById(user.getId());
                entity.setModifiedByFirstName(user.getFirstName());
                entity.setModifiedByLastName(user.getLastName());
            }
            entity.setVersion();
            entity.setMember(Authentication.getMemberId());
            session.saveOrUpdate(obj);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return obj;
    }

    public static synchronized Object save(Object obj, boolean isNew) throws JsonProcessingException {

        Session session=HibernateUtilV2.openSession();
        Transaction tx = null;
        try {
            // session = HibernateUtilV2.openSession();
            Transaction trx = session.beginTransaction();
            AbstractEntity entity = (AbstractEntity) obj;
            entity.setDefaultAcl();
            AuthenticatedUser user = Authentication.getAuthenticatedUser();
            if (isNew) {
                entity.setCreatedAt(Rew3Date.convertToUTC(Instant.now().toString()));
                entity.setCreatedById(user.getId());
                entity.setCreatedByFirstName(user.getFirstName());
                entity.setCreatedByLastName(user.getLastName());
            } else {
                entity.setLastModifiedAt(Rew3Date.convertToUTC(Instant.now().toString()));
                entity.setModifiedById(user.getId());
                entity.setModifiedByFirstName(user.getFirstName());
                entity.setModifiedByLastName(user.getLastName());
            }
            entity.setVersion();
            entity.setMember(Authentication.getMemberId());
            session.merge(obj);
            session.flush();
            trx.commit();
            session.close();
            return obj;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e; // or display error message
        } finally {
            session.close();
        }
    }

    public static synchronized boolean delete(Object obj, Transaction aTrx) {
        boolean success = false;

        Session session = null;
        Transaction trx = aTrx;
        try {
            session = HibernateUtilV2.openSession();
            trx = HibernateUtilV2.beginTransaction();

            session.delete(obj);
            session.flush();
            commitTransaction(trx);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction(trx);
            throw e;
        } finally {
            closeSession();
        }
        return success;
    }

    public static synchronized boolean delete(Object obj) {
        return delete(obj, null);
    }

    public static synchronized Object get(Class cls, String id) throws CommandException {
        boolean isNewSession = !HibernateUtilV2.isSessionOpen();
        Session session = null;
        Object obj = null;

        if (id != null) {
            try {
                session = HibernateUtilV2.openSession();

                obj = session.get(cls, id);
                AbstractEntity entity = (AbstractEntity) obj;
               /* if (entity == null) {
                    throw new CommandException("Not found");
                }*/

                /*    if (!entity.hasReadPermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    throw new CommandException("Access denied");
                }*/

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return obj;
    }

    public static Query createQuery(String hql, Map<String, Object> params, Integer limit, Integer offset)
            throws HibernateException {
        Session session = openSession();

        Query query = session.createQuery(hql);

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof List) {
                    // query.setParameterList(entry.getKey(), (List<String>) entry.getValue());
                    query.setParameterList(entry.getKey(), (Collection) entry.getValue());

                } else {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }

        }
        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }
        query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query;
    }

    public static SQLQuery createSQLQuery(String sql, Map<String, Object> params, Integer limit, Integer offset)
            throws HibernateException {
        Session session = openSession();
        SQLQuery query = session.createSQLQuery(sql);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }
        return query;

    }

    public static String s(String value) {
        String quote = "'";
        String formatted = String.format("'%s'", value.replaceAll(quote, quote + quote));
        return formatted;
    }

    public static List<Object> select(String
                                              hql, HashMap<String, Object> params, HashMap<String, Object> requestParams, int limit, int offset) {
        boolean isNewSession = !HibernateUtilV2.isSessionOpen();
        Session session = null;
        List<Object> results = null;
        Transaction tx = null;

        try {
            session = openSession();
            tx = session.beginTransaction();

            if (requestParams.size() > 0) {

                hql = filter(hql, requestParams, params);
            }

            Query query = createQuery(hql, params, limit, offset);
            results = query.list();
            tx.commit();
            //filter datas with active status and with read permisssion
           /* results = results.stream().map(c -> (AbstractEntity) c).filter(c -> c.hasReadPermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId()))
                    .collect(Collectors.toList());*/

        } catch (Exception e) {
            e.printStackTrace();
            closeSession();
            tx.rollback();

        } finally {

        }
        return results;
    }

    public static List<Object> selects(String
                                               hql, HashMap<String, Object> params, HashMap<String, String> filterParams, HashMap<String, Object> requestParams) {
        boolean isNewSession = !HibernateUtilV2.isSessionOpen();
        Session session = null;
        List<Object> results = null;
        Transaction tx = null;

        try {
            session = openSession();
            tx = session.beginTransaction();

            if (requestParams.size() > 0) {

                hql = filter(hql, requestParams, params);
            }

            Query query = createQuery(hql, params, null, null);
            results = query.list();
            //filter datas with active status and with read permisssion
            results = results.stream().map(c -> (AbstractEntity) c).filter(c -> c.hasReadPermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            closeSession();
            tx.rollback();
        } finally {
            if (isNewSession) {
                closeSession();


            }
        }
        return results;
    }


    private static String filter(String
                                         hql, HashMap<String, Object> requestParams, HashMap<String, Object> sqlParams) throws ParseException {

        HashMap<String, Object> keymap = Rew3StringBuiler.getMetaMapping();


        for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
            String requestValue = (String) entry.getValue();


            String requestKey = entry.getKey();

            //  String filteredKey=Rew3StringBuiler.filterTheKey(key,keymap,entryValue);


            Matcher numberRangeMatcher = PatternMatcher.numberRangeMatch(requestValue);
            if (numberRangeMatcher.find()) {

                String filteredKey = Rew3StringBuiler.getFilteredKey(requestKey).getValue();
                hql += " AND " + filteredKey + " BETWEEN :value1 AND :value2";
                sqlParams.put("value1", Double.parseDouble(numberRangeMatcher.group(1)));
                sqlParams.put("value2", Double.parseDouble(numberRangeMatcher.group(2)));


            }
            Matcher startsWithMatcher = PatternMatcher.startsWithMatch(requestKey);
            if (startsWithMatcher.find()) {
                String field = startsWithMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();
                hql += " AND " + filteredKey + " LIKE " + HibernateUtilV2.s(requestValue + "%");
            }

            Matcher endsWithMatcher = PatternMatcher.endsWithMatch(requestKey);
            if (endsWithMatcher.find()) {
                String field = endsWithMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();

                hql += " AND " + filteredKey + " LIKE" + HibernateUtilV2.s("%" + requestValue);

            }

            Matcher containsMatcher = PatternMatcher.containsMatch(requestKey);
            if (containsMatcher.find()) {
                String field = containsMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();
                hql += " AND " + filteredKey + " LIKE " + HibernateUtilV2.s("%" + requestValue + "%");
            }

            Matcher notContainMatcher = PatternMatcher.notContainMatch(requestKey);
            if (notContainMatcher.find()) {

                String field = notContainMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();
                hql += " AND " + filteredKey + " NOT LIKE " + HibernateUtilV2.s("%" + requestValue + "%");
            }
            Matcher specificDateMatcher = PatternMatcher.specificDateMatch(requestValue);
            if (specificDateMatcher.find()) {

                String val = specificDateMatcher.group(1);
                System.out.println(val);
                Flags.SpecificDateType type = Flags.SpecificDateType.valueOf(val.toUpperCase());

                Rew3DateRange range = Rew3Date.getTimestamp(type);
                String filteredKey = Rew3StringBuiler.getFilteredKey(requestKey).getValue();


                hql += " AND " + filteredKey + " BETWEEN " + HibernateUtilV2.s(range.getStartDate().toString()) + " AND " + HibernateUtilV2.s(range.getEndDate().toString());

            }

            Matcher isNotEqualMatcher = PatternMatcher.isNotEqualMatch(requestValue);
            if (isNotEqualMatcher.find()) {
                String val = isNotEqualMatcher.group(2);

                String filteredKey = Rew3StringBuiler.getFilteredKey(requestKey).getValue();


                hql += " AND " + filteredKey + " != " + val;

            }


            Matcher emptyMatcher = PatternMatcher.emptyMatch(requestKey);
            if (emptyMatcher.find()) {
                String field = emptyMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();

                hql += " AND " + filteredKey + " is  NULL";
            }
            Matcher nonEmptyMatcher = PatternMatcher.nonEmptyMatch(requestKey);
            if (nonEmptyMatcher.find()) {
                String field = nonEmptyMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();

                hql += " AND " + filteredKey + " is NOT NULL";
            }

            Matcher dueMatcher = PatternMatcher.dueMatch(requestKey);
            if (dueMatcher.find()) {
                String field = dueMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();

                Matcher validTimeUnit = PatternMatcher.hasValidTimeUnit(requestValue);

                if (validTimeUnit.matches()) {

                    Integer due = Integer.parseInt(validTimeUnit.group(1));
                    String timeUnit = validTimeUnit.group(2);

                    Rew3TimeUnit rew3TimeUnit = Rew3TimeUnit.valueOf(timeUnit.toUpperCase());

                    org.joda.time.DateTime current = new org.joda.time.DateTime();


                    due = getValue(due, rew3TimeUnit);


                    Timestamp currentTimestamp = new Timestamp(current.getMillis());

                    Timestamp dueInTimestamp = new Timestamp(current.plusDays(due).getMillis());
                    hql += " AND " + filteredKey + " BETWEEN date(:current) AND date(:dueIn)";
                    sqlParams.put("current", currentTimestamp.toString());
                    sqlParams.put("dueIn", dueInTimestamp.toString());

                }

            }

            Matcher overdueMatcher = PatternMatcher.overdueMatch(requestKey);
            if (overdueMatcher.find()) {

                String field = overdueMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();


                Matcher validTimeUnit = PatternMatcher.hasValidTimeUnit(requestValue);
                if (validTimeUnit.matches()) {
                    Integer overdue = Integer.parseInt(validTimeUnit.group(1));
                    String timeUnit = validTimeUnit.group(2);

                    Rew3TimeUnit rew3TimeUnit = Rew3TimeUnit.valueOf(timeUnit.toUpperCase());

                    org.joda.time.DateTime current = new org.joda.time.DateTime();
                    overdue = getValue(overdue, rew3TimeUnit);
                    Timestamp currentTimestamp = new Timestamp(current.getMillis());

                    Timestamp overdueTimestamp = new Timestamp(current.minusDays(overdue).getMillis());
                    hql += " AND " + filteredKey + " BETWEEN date(:overdueBy) AND date(:current)";
                    sqlParams.put("current", currentTimestamp.toString());
                    sqlParams.put("overdueBy", overdueTimestamp.toString());
                }


            }


            Matcher inMatcher = PatternMatcher.inMatch(requestKey);
            if (inMatcher.find()) {

                String list = requestValue;
                String csvs = list.substring(1, list.length() - 1);
                String field = requestKey.split("-")[0];
                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();
                List<Object> objects = Arrays.asList(csvs.split(","));
                hql += " AND " + filteredKey + " IN (:values)";
                sqlParams.put("values", objects);


            }
            Matcher notInMatcher = PatternMatcher.notInMatch(requestKey);
            if (notInMatcher.find()) {
                String list = requestValue;
                String csvs = list.substring(1, list.length() - 1);
                String field = requestKey.split("-")[0];
                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();

                List<Object> objects = Arrays.asList(csvs.split(","));
                hql += " AND t." + filteredKey + " NOT IN (:values)";
                sqlParams.put("values", objects);


            }


            Rew3DateRange range = PatternMatcher.dateRangeMatch(requestValue);
            if (range.isMatches()) {
                String filteredKey = Rew3StringBuiler.getFilteredKey(requestKey).getValue();

                hql += " AND " + filteredKey + " BETWEEN date(:start) AND date(:end)";
                sqlParams.put("start", range.getStartDate().toString());
                sqlParams.put("end", range.getEndDate().toString());
            }


            Matcher keyMatcher = PatternMatcher.keyMatch(requestKey);


            boolean operatorMatch = keyMatcher.find();
            Matcher dateMatcher = PatternMatcher.dateFormatMatch(requestValue);
            boolean dateFormatMatch = dateMatcher.find();

            if (operatorMatch) {
                String field = keyMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();
                String operator = keyMatcher.group(2);
                String op = Parser.getOperator(operator);
                hql += " AND " + filteredKey + op + HibernateUtilV2.s(requestValue);

            }

            if (operatorMatch && dateFormatMatch) {
                String field = keyMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();
                String operator = keyMatcher.group(2);
                String queryDate = dateMatcher.group(1);
                String op = Parser.getOperator(operator);
                hql += " AND " + filteredKey + op + "date(" + HibernateUtilV2.s(queryDate);

            }
        }
        hql = sort(hql, requestParams);

        return hql;
    }


    private static Integer getValue(Integer due, Rew3TimeUnit timeunit) {
        if (timeunit == Rew3TimeUnit.DAYS) {
            due = due;
        } else if (timeunit == Rew3TimeUnit.MONTHS) {
            due = due * 30;
        } else if (timeunit == Rew3TimeUnit.WEEKS) {
            due = due * 7;
        }
        return due;
    }

    private static String sort(String hql, HashMap<String, Object> filterParams) {
        if (filterParams.containsKey("sort")) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = new HashMap<String, String>();
            try {
                map = mapper.readValue((String) filterParams.get("sort"), new TypeReference<Map<String, String>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (map != null) {
                hql += " ORDER BY ";
                int count = map.size();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String field = entry.getKey();
                    String filteredKey = Rew3StringBuiler.getFilteredKey(field).getValue();
                    if (entry.getValue().equals("1")) {


                        hql += filteredKey + " ASC";

                    }
                    if (entry.getValue().equals("-1")) {

                        hql += filteredKey + " DESC";
                    }
                    if (count > 1) {
                        hql += ",";
                    }
                    count--;

                }
            }
        }
        return hql;
    }

    private static String filterEmptyOrNonEmpty(String hql, Set<String> keys) {
        if (keys != null) {

            for (String s : keys) {
                if (s.contains("-non-empty")) {
                    String value = s.split("-non-empty")[0];
                    hql += " AND " + value + " is NOT NULL";
                    break;
                }
                if (s.contains("-empty")) {
                    String value = s.split("-empty")[0];
                    hql += " AND " + value + " is NULL";
                    break;
                }
            }
        }
        return hql;
    }

    public static Object createQuery(String hql, Map<String, Object> params)
            throws HibernateException {
        Session session = openSession();

        Query query = session.createQuery(hql);

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

        }

        return query.uniqueResult();
    }


    public static synchronized Object saveAsDeleted(List obj, Transaction aTrx) throws
            JsonProcessingException, CommandException {

        boolean success = false;
        boolean isCommitable = false;
        if (aTrx == null) {
            isCommitable = true;
        }

        Session session = null;
        Transaction trx = aTrx;
        try {
            session = HibernateUtilV2.openSession();
            if (isCommitable) {
                trx = HibernateUtilV2.beginTransaction();
            }
            AuthenticatedUser user = Authentication.getAuthenticatedUser();
            for (Object o : obj) {
                AbstractEntity entity = (AbstractEntity) o;
                entity.setStatus(Flags.EntityStatus.DELETED);
                entity.setDeletedAt(Rew3Date.convertToUTC(Instant.now().toString()));
                entity.setDeletedById(user.getId());
                entity.setDeletedByFirstName(user.getFirstName());
                entity.setDeletedByLastName(user.getLastName());
                entity.setVersion();

                if (entity.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    session.saveOrUpdate(o);
                } else {
                    throw new CommandException("Permission Denied");
                }
            }


            session.flush();

            if (isCommitable) {
                commitTransaction(trx);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (isCommitable) {
                rollbackTransaction(trx);
            }
            throw e;
        } finally {

            if (isCommitable) {
                closeSession();
            }
        }
        return obj;
    }

    public static synchronized Object saveAsDeleted(Object obj, Transaction aTrx) throws
            JsonProcessingException, CommandException {


        Session session = null;
        try {
            session = HibernateUtilV2.openSession();

            AbstractEntity entity = (AbstractEntity) obj;
            entity.setStatus(Flags.EntityStatus.DELETED);

            AuthenticatedUser user = Authentication.getAuthenticatedUser();
            //saving meta info
            entity.setVersion();
            entity.setDeletedAt(Rew3Date.convertToUTC(Instant.now().toString()));
            entity.setDeletedById(user.getId());
            entity.setDeletedByFirstName(user.getFirstName());
            entity.setDeletedByLastName(user.getLastName());

            if (entity.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                session.saveOrUpdate(obj);
            } else {
                throw new CommandException("Permission Denied");
            }

        } catch (Exception e) {
            e.printStackTrace();


        }
        return obj;
    }

    public static synchronized Object saveAsDeleted(Object obj) throws
            JsonProcessingException, CommandException {


        Session session = null;
        try {
            session = HibernateUtilV2.openSession();
            AbstractEntity entity = (AbstractEntity) obj;
            entity.setStatus(Flags.EntityStatus.DELETED);

            AuthenticatedUser user = Authentication.getAuthenticatedUser();
            //saving meta info
            entity.setVersion();
            entity.setDeletedAt(Rew3Date.convertToUTC(Instant.now().toString()));
            entity.setDeletedById(user.getId());
            entity.setDeletedByFirstName(user.getFirstName());
            entity.setDeletedByLastName(user.getLastName());

            if (entity.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                session.saveOrUpdate(obj);
            } else {
                throw new CommandException("Permission Denied");
            }

        } catch (Exception e) {
            e.printStackTrace();


        }
        return obj;
    }

    public static <
            T> List<T> select(String hql, HashMap<String, Object> params, HashMap<String, Object> requestParams,
                              int limit, int offset, T t) {
        Session session = null;
        List<T> results = null;
        Transaction tx = null;

        try {

            session = openSession();
            tx = session.beginTransaction();

            if (requestParams.size() > 0) {

                hql = filter(hql, requestParams, params, t);
            }

            Query query = createQuery(hql, params, limit, offset);
            results = query.list();
            tx.commit();
            //filter datas with active status and with read permisssion
           /* results = results.stream().map(c -> (AbstractEntity) c).filter(c -> c.hasReadPermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId()))
                    .collect(Collectors.toList());*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession();
        }
        return results;
    }

    private static <T> String
    filter(String hql, HashMap<String, Object> requestParams, HashMap<String, Object> sqlParams, T t) throws
            ParseException {

        HashMap<String, Object> keymap = Rew3StringBuiler.getMetaMapping();


        for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
            String requestValue = (String) entry.getValue();


            String requestKey = entry.getKey();

            //  String filteredKey=Rew3StringBuiler.filterTheKey(key,keymap,entryValue);


            Matcher numberRangeMatcher = PatternMatcher.numberRangeMatch(requestValue);
            Matcher inMatchers = PatternMatcher.inMatch(requestKey);
            Matcher notInMatchers = PatternMatcher.notInMatch(requestKey);
            if (numberRangeMatcher.find() && !inMatchers.find() && !notInMatchers.find()) {

                String filteredKey = Rew3StringBuiler.getFilteredKey(requestKey, t).getValue();
                hql += " AND " + filteredKey + " BETWEEN :value1 AND :value2";
                sqlParams.put("value1", Double.parseDouble(numberRangeMatcher.group(1)));
                sqlParams.put("value2", Double.parseDouble(numberRangeMatcher.group(2)));


            }
            Matcher startsWithMatcher = PatternMatcher.startsWithMatch(requestKey);
            if (startsWithMatcher.find()) {
                String field = startsWithMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();
                hql += " AND " + filteredKey + " LIKE " + HibernateUtilV2.s(requestValue + "%");
            }

            Matcher endsWithMatcher = PatternMatcher.endsWithMatch(requestKey);
            if (endsWithMatcher.find()) {
                String field = endsWithMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();

                hql += " AND " + filteredKey + " LIKE" + HibernateUtilV2.s("%" + requestValue);

            }

            Matcher containsMatcher = PatternMatcher.containsMatch(requestKey);
            if (containsMatcher.find()) {
                String field = containsMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();
                hql += " AND " + filteredKey + " LIKE " + HibernateUtilV2.s("%" + requestValue + "%");
            }

            Matcher notContainMatcher = PatternMatcher.notContainMatch(requestKey);
            if (notContainMatcher.find()) {

                String field = notContainMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();
                hql += " AND " + filteredKey + " NOT LIKE " + HibernateUtilV2.s("%" + requestValue + "%");
            }
            Matcher specificDateMatcher = PatternMatcher.specificDateMatch(requestValue);
            if (specificDateMatcher.find()) {

                String val = specificDateMatcher.group(1);
                System.out.println(val);
                Flags.SpecificDateType type = Flags.SpecificDateType.valueOf(val.toUpperCase());

                Rew3DateRange range = Rew3Date.getTimestamp(type);
                String filteredKey = Rew3StringBuiler.getFilteredKey(requestKey, t).getValue();


                hql += " AND " + filteredKey + " BETWEEN " + HibernateUtilV2.s(range.getStartDate().toString()) + " AND " + HibernateUtilV2.s(range.getEndDate().toString());

            }

            Matcher emptyMatcher = PatternMatcher.emptyMatch(requestKey);
            if (emptyMatcher.find()) {
                String field = emptyMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();

                hql += " AND " + filteredKey + " is  NULL";

                //hql += " AND " + filteredKey + " is  NULL"+ " OR "+filteredKey +"= ''";
            }
            Matcher nonEmptyMatcher = PatternMatcher.nonEmptyMatch(requestKey);
            if (nonEmptyMatcher.find()) {
                String field = nonEmptyMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();

                hql += " AND " + filteredKey + " is NOT NULL";
            }

            Matcher dueMatcher = PatternMatcher.dueMatch(requestKey);
            if (dueMatcher.find()) {
                String field = dueMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();

                Matcher validTimeUnit = PatternMatcher.hasValidTimeUnit(requestValue);

                if (validTimeUnit.matches()) {

                    Integer due = Integer.parseInt(validTimeUnit.group(1));
                    String timeUnit = validTimeUnit.group(2);

                    Rew3TimeUnit rew3TimeUnit = Rew3TimeUnit.valueOf(timeUnit.toUpperCase());

                    org.joda.time.DateTime current = new org.joda.time.DateTime();


                    due = getValue(due, rew3TimeUnit);


                    Timestamp currentTimestamp = new Timestamp(current.getMillis());

                    Timestamp dueInTimestamp = new Timestamp(current.plusDays(due).getMillis() - 1);
                    hql += " AND " + filteredKey + " BETWEEN :current AND :dueIn";
                    sqlParams.put("current", currentTimestamp);
                    sqlParams.put("dueIn", dueInTimestamp);

                }

            }

            Matcher overdueMatcher = PatternMatcher.overdueMatch(requestKey);
            if (overdueMatcher.find()) {

                String field = overdueMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();


                Matcher validTimeUnit = PatternMatcher.hasValidTimeUnit(requestValue);
                if (validTimeUnit.matches()) {
                    Integer overdue = Integer.parseInt(validTimeUnit.group(1));
                    String timeUnit = validTimeUnit.group(2);

                    Rew3TimeUnit rew3TimeUnit = Rew3TimeUnit.valueOf(timeUnit.toUpperCase());

                    org.joda.time.DateTime current = new org.joda.time.DateTime();
                    overdue = getValue(overdue, rew3TimeUnit);
                    Timestamp currentTimestamp = new Timestamp(current.getMillis());

                    Timestamp overdueTimestamp = new Timestamp(current.minusDays(overdue).getMillis() - 1);
                    hql += " AND " + filteredKey + " BETWEEN :overdueBy AND :current";
                    sqlParams.put("current", currentTimestamp);
                    sqlParams.put("overdueBy", overdueTimestamp);
                }


            }


            Matcher inMatcher = PatternMatcher.inMatch(requestKey);
            if (inMatcher.find()) {

                String list = requestValue;
                String csvs = list.substring(1, list.length() - 1);
                String field = requestKey.split("-")[0];

                TypeAndValue typeAndValue = Rew3StringBuiler.getFilteredKey(field, t);

                String filteredKey = typeAndValue.getValue();

                List<Object> objects = Arrays.asList(csvs.split(","));

                if (typeAndValue.getType().equals("NUMBER")) {

                    objects = objects.stream().map(c ->
                            Parser.convertObjectToDouble(c)
                    ).collect(Collectors.toList());
                }

                hql += " AND " + filteredKey + " IN (:values)";
                sqlParams.put("values", objects);


            }
            Matcher notInMatcher = PatternMatcher.notInMatch(requestKey);
            if (notInMatcher.find()) {
                String list = requestValue;
                String csvs = list.substring(1, list.length() - 1);
                String field = requestKey.split("-")[0];
                TypeAndValue typeAndValue = Rew3StringBuiler.getFilteredKey(field, t);

                String filteredKey = typeAndValue.getValue();

                List<Object> objects = Arrays.asList(csvs.split(","));

                if (typeAndValue.getType().equals("NUMBER")) {

                    objects = objects.stream().map(c ->
                            Parser.convertObjectToDouble(c)
                    ).collect(Collectors.toList());
                }

                hql += " AND " + filteredKey + " NOT IN (:values)";
                sqlParams.put("values", objects);


            }


            Matcher isEqualMatcher = PatternMatcher.isEqualMatch(requestKey);
            if (isEqualMatcher.find()) {
                String val = isEqualMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(val, t).getValue();


                hql += " AND " + filteredKey + " =:value";
                sqlParams.put("value", requestValue);

            }
            Matcher isNotEqualMatcher = PatternMatcher.isNotEqualMatch(requestKey);
            if (isNotEqualMatcher.find()) {
                String val = isNotEqualMatcher.group(1);

                String filteredKey = Rew3StringBuiler.getFilteredKey(val, t).getValue();

                hql += " AND " + filteredKey + " !=:value";
                sqlParams.put("value", requestValue);


            }


            Rew3DateRange range = PatternMatcher.dateRangeMatch(requestValue);
            if (range.isMatches()) {
                String filteredKey = Rew3StringBuiler.getFilteredKey(requestKey, t).getValue();

                hql += " AND " + filteredKey + " BETWEEN :start AND :end)";
                sqlParams.put("start", range.getStartDate());
                sqlParams.put("end", range.getEndDate());
            }


            Matcher keyMatcher = PatternMatcher.keyMatch(requestKey);


            boolean operatorMatch = keyMatcher.find();
            Matcher dateMatcher = PatternMatcher.dateFormatMatch(requestValue);
            boolean dateFormatMatch = dateMatcher.find();

            if (operatorMatch) {
                String field = keyMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();
                String operator = keyMatcher.group(2);
                String op = Parser.getOperator(operator);
                hql += " AND " + filteredKey + op + HibernateUtilV2.s(requestValue);

            }

            if (operatorMatch && dateFormatMatch) {
                String field = keyMatcher.group(1);
                String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();
                String operator = keyMatcher.group(2);
                String queryDate = dateMatcher.group(1);
                String op = Parser.getOperator(operator);
                hql += " AND " + filteredKey + op + "date(" + HibernateUtilV2.s(queryDate);

            }
        }
        if (requestParams.containsKey("sort")) {
            hql = sort(hql, requestParams, t);
        }

        return hql;
    }

    private static String sort(String hql, HashMap<String, Object> filterParams, Object t) {
        if (filterParams.containsKey("sort")) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = new HashMap<String, String>();
            try {
                map = mapper.readValue((String) filterParams.get("sort"), new TypeReference<Map<String, String>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (map != null) {
                hql += " ORDER BY ";
                int count = map.size();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String field = entry.getKey();
                    String filteredKey = Rew3StringBuiler.getFilteredKey(field, t).getValue();
                    if (entry.getValue().equals("1")) {


                        hql += filteredKey + " ASC";

                    }
                    if (entry.getValue().equals("-1")) {

                        hql += filteredKey + " DESC";
                    }
                    if (count > 1) {
                        hql += ",";
                    }
                    count--;

                }
            }
        }
        return hql;
    }

    public static synchronized Object save(Object obj, ICommand command, boolean isNew) throws
            JsonProcessingException {


        Session session = null;
        try {
            session = HibernateUtilV2.openSession();
            AbstractEntity entity = (AbstractEntity) obj;
            entity.setDefaultAcl();

            //saving meta info's
            setMetaInfo(command, isNew, entity);
            session.saveOrUpdate(obj);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


        }
        return obj;


    }

    private static void setMetaInfo(ICommand command, boolean isNew, AbstractEntity entity) {
        AuthenticatedUser user = Authentication.getAuthenticatedUser();

        if (command.has("owner")) {

            HashMap<String, Object> owner = (HashMap<String, Object>) command.get("owner");

            if (owner.containsKey("_id")) {
                entity.setOwnerId(owner.get("_id").toString());
            }
            if (owner.containsKey("firstName")) {
                entity.setOwnerFirstName(owner.get("firstName").toString());
            }
            if (owner.containsKey("lastName")) {
                entity.setOwnerLastName(owner.get("lastName").toString());
            }
        }
        if (command.has("visibility")) {

            Flags.VisibilityType visibilityType = Flags.VisibilityType.valueOf(command.get("visibility").toString());
            entity.setVisibility(visibilityType);
        }
        if (command.has("status")) {

            Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(command.get("status").toString());
            entity.setStatus(entityStatus);
        }


        if (isNew) {
            entity.setCreatedAt(Rew3Date.convertToUTC(Instant.now().toString()));
            entity.setCreatedById(user.getId());
            entity.setCreatedByFirstName(user.getFirstName());
            entity.setCreatedByLastName(user.getLastName());


            entity.setMetaOwnerId(user.getId());
            entity.setMetaOwnerFirstName(user.getFirstName());
            entity.setMetaOwnerLastName(user.getLastName());
        } else {
            HashMap<String, Object> metaMap = (HashMap<String, Object>) command.get("meta");
            if (metaMap != null) {
                if (metaMap.get("_modifiedBy") != null) {

                    HashMap<String, Object> modifiedBy = (HashMap<String, Object>) metaMap.get("_modifiedBy");
                    if (modifiedBy.get("firstName") != null) {
                        entity.setModifiedByFirstName(modifiedBy.get("firstName").toString());
                    }
                    if (modifiedBy.get("lastName") != null) {
                        entity.setModifiedByLastName(modifiedBy.get("lastName").toString());
                    }
                    if (modifiedBy.get("_id") != null) {
                        entity.setModifiedByLastName(modifiedBy.get("_id").toString());
                    }
                }
                if (metaMap.get("_owner") != null) {

                    HashMap<String, Object> _metaOwner = (HashMap<String, Object>) metaMap.get("_owner");
                    if (_metaOwner.get("firstName") != null) {
                        entity.setMetaOwnerFirstName(_metaOwner.get("firstName").toString());
                    }
                    if (_metaOwner.get("lastName") != null) {
                        entity.setMetaOwnerLastName(_metaOwner.get("lastName").toString());
                    }
                    if (_metaOwner.get("_id") != null) {
                        entity.setMetaOwnerId(_metaOwner.get("_id").toString());
                    }
                }

                if (metaMap.get("_lastModified") != null) {
                    entity.setLastModifiedAt(Rew3Date.convertToUTC(metaMap.get("_lastModified").toString()));
                }
                if (metaMap.get("_master") != null) {
                    entity.setMaster(metaMap.get("_master").toString());
                }
                if (metaMap.get("_entity") != null) {
                    entity.setEntity(metaMap.get("_entity").toString());
                }
                if (metaMap.get("_module") != null) {
                    entity.setModule(metaMap.get("_module").toString());
                }
            }

        }
        entity.setVersion();
        entity.setMember(Authentication.getMemberId());
    }

    public static <T> Object
    count(String hql, HashMap<String, Object> params, HashMap<String, Object> requestParams, int limit,
          int offset, T t) {
        boolean isNewSession = !HibernateUtilV2.isSessionOpen();
        Session session = null;
        Object o = null;
        Transaction tx = null;

        try {
            session = openSession();
            //session.flush();
            tx = session.beginTransaction();

            if (requestParams.size() > 0) {

                hql = filter(hql, requestParams, params, t);
            }

            Query query = createQuery(hql, params, limit, offset);
            o = query.uniqueResult();
            tx.commit();
            //filter datas with active status and with read permisssion
           /* results = results.stream().map(c -> (AbstractEntity) c).filter(c -> c.hasReadPermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId()))
                    .collect(Collectors.toList());*/

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            closeSession();
            tx.rollback();

        } finally {

            //session.clear();
        }
        return o;
    }


    public static <T> Long
    count(String hql, HashMap<String, Object> params, HashMap<String, Object> requestParams, T t) {
        Session session = null;
        Object o = null;
        Transaction tx = null;

        try {
            session = openSession();
            //session.flush();
            tx = session.beginTransaction();

            if (requestParams.size() > 0) {

                hql = filter(hql, requestParams, params, t);
            }

            Query query = createQuery(hql, params, 0, 0);
            o = query.uniqueResult();
            tx.commit();
            //filter datas with active status and with read permisssion
           /* results = results.stream().map(c -> (AbstractEntity) c).filter(c -> c.hasReadPermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId()))
                    .collect(Collectors.toList());*/

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            closeSession();
            tx.rollback();

        } finally {

            //session.clear();
        }
        return Parser.convertObjectToLong(o);
    }


    public static List<Object> selectAll(String hql) {
        Session session = null;
        List<Object> results = null;
        Transaction tx = null;

        try {
            session = openSession();
            tx = session.beginTransaction();


            Query query = createQuery(hql, null, null, null);
            results = query.list();
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            closeSession();
            tx.rollback();

        } finally {

            //session.clear();
        }
        return results;
    }


    public static Transaction startTransaction() {
        HibernateUtilV2.openSession();
        Transaction trx = HibernateUtilV2.beginTransaction();
        return trx;
    }


}
