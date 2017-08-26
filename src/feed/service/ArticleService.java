package feed.service;

import java.util.List;
import java.util.Map;

import feed.beans.Article;
import feed.dao.ArticleDao;

public class ArticleService {
	private ArticleDao articleDao = new ArticleDao();
	public boolean createArticle(Article article) {
		return articleDao.createArticle(article);
	}
	
	public Map<String, List<Article>> getArticlesByUser(int userId){
		return articleDao.getArticlesByUser(userId);
	}
}
