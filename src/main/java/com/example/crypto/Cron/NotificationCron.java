package com.example.crypto.Cron;


import com.example.crypto.Controllers.JwtAuthenticationController;
import com.example.crypto.JWT.JwtTokenUtil;
import com.example.crypto.Logger.Logging;
import com.example.crypto.Model.FCM.Body;
import com.example.crypto.Model.FCM.Message;
import com.example.crypto.Model.FCM.NotificationModel;
import com.example.crypto.services.alertService;
import com.example.crypto.services.cmcService;
import com.example.crypto.services.fcmService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class NotificationCron {

    private Logger logger = Logging.getLogger(NotificationCron.class);

    @Autowired
    private alertService as;



    @Scheduled(cron = "0 0 13,19 * * ?")
    public void sendnotificationsJob() {

        logger.info("Running cron job at"+new Date().toString());
        this.as.sendNotifcations();
        logger.info("Cron job executed");
    }
}
