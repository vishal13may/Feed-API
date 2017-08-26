package feed.service;

import java.util.Date;

import feed.beans.Subscription;
import feed.dao.SubscriberDao;

public class SubscriberService {
	SubscriberDao subscriberDao = new SubscriberDao();
	
	public boolean subscribe(Subscription subscription){
		subscription.setActive(true);
		subscription.setStartDate(new Date());
		return subscriberDao.subscribe(subscription);
	}
	
	public boolean unsubscribe(Subscription subscription){
		subscription.setActive(false);
		subscription.setEndDate(new Date());
		return subscriberDao.unsubscribe(subscription);
	}
}
