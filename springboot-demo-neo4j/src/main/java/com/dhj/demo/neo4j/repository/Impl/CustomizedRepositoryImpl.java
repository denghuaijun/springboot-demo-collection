package com.dhj.demo.neo4j.repository.Impl;

import com.dhj.demo.neo4j.repository.CustomizedRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomizedRepositoryImpl<T> implements CustomizedRepository<T> {

    private final static Logger log = LoggerFactory.getLogger(CustomizedRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<T> someCustomMethod() {
        return null;
    }

    @Override
    public Iterable<T> findByNodeId(Class<T> objectType, Integer id) {
        Iterable<T> iterable=null;
        Session session = sessionFactory.openSession();
        try(Transaction transaction=session.beginTransaction()){
            //String cql = "MATCH (n:Movie)<-[]-(s) WHERE n.id= $id return n";
            String cql="MATCH (n) WHERE id(n)=$id return n";
            Map<String,Integer> reqParamMap=new HashMap<>();
            reqParamMap.put("id",id);
            iterable= session.query(objectType, cql, reqParamMap);
            log.info("CustomizedRepositoryImpl。findByNodeId  return iterable==:{}",iterable);
            transaction.commit();
        }catch (Exception e){

            e.printStackTrace();
        }
        return iterable;
    }
}
