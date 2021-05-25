package com.example.crypto.services;

import com.example.crypto.DAO.AlertDao;
import com.example.crypto.Model.AlertModel;
import com.example.crypto.Model.CMC.SymbolInfo;
import com.example.crypto.Model.FCM.Body;
import com.example.crypto.Model.FCM.Message;
import com.example.crypto.Model.FCM.NotificationModel;
import com.example.crypto.Model.PairModel;
import com.example.crypto.mongorepo.connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class alertService implements AlertDao {

    private MongoOperations mo = connection.get();

    @Autowired
    private fcmService fs;

    @Autowired
    private cmcService cmcservice;

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
        q.addCriteria(Criteria.where("pair").regex("/(\\w+\\b)(?!.*\\1\\b)/"));
        return this.mo.find(q,AlertModel.class);
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

    public void savePair(PairModel p){
        Query q = new Query();
        q.addCriteria(Criteria.where("pair").is(p.getPair()));

        if(this.mo.find(q,PairModel.class).stream().count() > 0){

        }
        else{
            this.mo.save(p);
        }
    }

    public List<PairModel> getPairs(){
        return this.mo.findAll(PairModel.class);
    }


    public void sendNotifcations(){
        NotificationModel nm = new NotificationModel();
        Message m = new Message();
        Body d = new Body();
        this.getPairs().forEach(x -> {
            System.out.println(x.getPair());
            //for getting change
            String[] pair = x.getPair().split("-");
            Mono<SymbolInfo> data = cmcservice.getSymbolChange(pair[0],pair[1]);
            DecimalFormat df = new DecimalFormat("#.####");
            String change = "";
            String quoteprice = "";

            Map<String, Map<String,Object>> quote = null;
            try{
                quote = (Map) data.block().getData().get(pair[0]).get("quote");
                quoteprice = df.format((Double) quote.get(pair[1]).get("price"));
                change = df.format((Double) quote.get(pair[1]).get("percent_change_1h"));

            }
            catch (Exception e){

            }
            // end fetching data
            d.setTitle("Crypto Alert");
            d.setBody(x.getPair().split("-")[0]+" is up "+change+"% to $"+quoteprice);
            m.setNotification(d);
            m.setTopic("cryptassist-"+x.getPair());
            nm.setMessage(m);
            try {
                fs.SendNotificationToTopic(nm);
                System.out.println("Notification sent to "+"cryptassist-"+x.getPair());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error while sending notification to "+"cryptassist-"+x.getPair());
            }
        });

    }
}
