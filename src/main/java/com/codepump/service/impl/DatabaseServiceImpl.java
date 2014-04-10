package com.codepump.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.codepump.controller.ServerController;
import com.codepump.data.CodeItem;
import com.codepump.data.User;
import com.codepump.service.DatabaseService;
import com.codepump.tempobject.MyStuffListItem;
import com.codepump.tempobject.RecentItem;
import com.codepump.tempobject.UserLanguageStatisticsItem;
import com.codepump.util.HibernateUtil;

public class DatabaseServiceImpl implements DatabaseService {
	private Session session;
	private final boolean USE_DATABASE = ServerController.USE_DATABASE;

	public DatabaseServiceImpl() {
		if(USE_DATABASE)
			session = HibernateUtil.currentSession();
	}

	@Override
	public void deleteCodeItem(int codeId) {
		session.getTransaction().begin();
		session.createSQLQuery("delete from codeitem where code_id =:id")
				.setParameter("id", codeId).executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public CodeItem findCodeItemById(int codeId) {
		@SuppressWarnings("unchecked")
		List<CodeItem> dataset = session
				.createQuery("from CodeItem where CODE_ID=:id")
				.setParameter("id", codeId).list();
		if (dataset.size() == 1) {
			return dataset.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<CodeItem> getAllCodeItems() {
		@SuppressWarnings("unchecked")
		List<CodeItem> dataset = session
				.createQuery(
						"from CodeItem where PRIVACY='Public' order by created_date desc")
				.list();
		return dataset;
	}

	@Override
	public List<MyStuffListItem> getAllUserItems(int userId) {
		Query q = session.getNamedQuery("thisUserCodeByID");
		q.setParameter("t_id", userId);
		@SuppressWarnings("unchecked")
		List<MyStuffListItem> dataset = q.list();
		return dataset;
	}

	@Override
	public List<RecentItem> getRecentItems() {
		@SuppressWarnings("unchecked")
		List<RecentItem> results = session.getNamedQuery(
				"findRecentItemsInOrder").list();
		return results;
	}

	@Override
	public void updateCodeItem(CodeItem code) {
		session.getTransaction().begin();
		session.update(code);
		session.getTransaction().commit();

	}

	@Override
	public void saveCodeItem(CodeItem code) {
		session.getTransaction().begin();
		session.save(code);
		session.getTransaction().commit();
		session.clear();

	}

	@Override
	public User findUserById(int userId) {
		@SuppressWarnings("unchecked")
		List<User> dataset = session.createQuery("from User where USER_ID=:id")
				.setParameter("id", userId).list();
		if (dataset.size() == 1) {
			return dataset.get(0);
		} else {
			return null;
		}
	}

	@Override
	public User findUserByEmail(String email) {
		@SuppressWarnings("unchecked")
		List<User> dataset = session
				.createQuery("from User where USER_EMAIL = :email")
				.setParameter("email", email).list();
		if (dataset.size() == 1) {
			return dataset.get(0);
		} else {
			return null;
		}
	}

	@Override
	public User findUserByName(String username) {
		@SuppressWarnings("unchecked")
		List<User> dataset = session
				.createQuery("from User where USER_NAME = :userName")
				.setParameter("userName", username).list();
		if (dataset.size() == 1) {
			return dataset.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<UserLanguageStatisticsItem> findUserLanguageStatistics(
			int userId) {
		Query q = session.getNamedQuery("thisUserLanguageStatistics");
		q.setParameter("t_id", userId);
		@SuppressWarnings("unchecked")
		List<UserLanguageStatisticsItem> dataset = q.list();
		return dataset;
	}

	@Override
	public void saveUser(User user) {
		session.getTransaction().begin();
		session.save(user);
		session.getTransaction().commit();
	}

}
