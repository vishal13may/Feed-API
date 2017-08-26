package feed.service;

import java.util.List;

import feed.beans.Feed;
import feed.dao.FeedDao;

public class FeedService {
	FeedDao feedDao = new FeedDao();

	public List<Feed> getUserFeeds(int userId) {
		return feedDao.getUserFeeds(userId);
	}
}
