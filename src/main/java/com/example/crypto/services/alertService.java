package com.example.crypto.services;

import com.example.crypto.DAO.AlertDao;
import com.example.crypto.Model.AlertModel;
import com.example.crypto.mongorepo.connection;
import com.mongodb.client.DistinctIterable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class alertService implements AlertDao {

    private MongoOperations mo = connection.get();

    @Override
    public AlertModel save(AlertModel am) {
        return this.mo.save(am);
    }

    @Override
    public List<AlertModel> getAll(String id) {

        Query q = new Query();
        q.addCriteria(Criteria.where("userId").is(id));
        return this.mo.find(q,AlertModel.class);

    }

    @Override
    public List<AlertModel> getAllDistinct(){
        Query q = new Query();
        return this.mo.findDistinct("pair",AlertModel.class,AlertModel.class);
    }

    @Override
    public List<AlertModel> Existed(String id,String curr,String quote){
        Query q = new Query();
        q.addCriteria(Criteria.where("userId").is(id).and("currencyName").is(curr).and("currencyPair").is(quote));
        return this.mo.find(q,AlertModel.class);
    }

    @Override
    public void Delete(String id, String pair) {
        Query q = new Query();
        q.addCriteria(Criteria.where("userId").is(id).and("pair").is(pair));
        this.mo.findAndRemove(q,AlertModel.class);
    }
}
