package com.codepump.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.codepump.controller.ServerController;
import com.codepump.data.User;
import com.codepump.service.UserService;
import com.codepump.tempobject.UserStatisticsItem;
import com.codepump.util.HibernateUtil;

public class UserServiceImpl implements UserService {
	public static Map<Integer, User> users;
	private int userCounter;

	// Database connection stuff
	private final boolean USE_DATABASE = ServerController.USE_DATABASE;
	private Session session;

	public UserServiceImpl() {
		if (USE_DATABASE) {
			session = HibernateUtil.currentSession();

		} else {
			users = new HashMap<>();
			users.put(1, new User(1, "User", "fuckoff@gmail.com", "12345"));
			users.put(2, new User(2, "test", "the1whokn0cks@gmail.com",
					"qwerty"));
			userCounter = 3;
			for (User user : users.values()) {
				user.hashPassword();
			}
			System.out.println("Users are: " + users.toString());

		}

	}

	@Override
	public void addUser(User item) {
		if (USE_DATABASE) {
			session.getTransaction().begin();
			session.save(item);
			session.getTransaction().commit();
		} else {
			item.setId(userCounter); // temp hack
			users.put(userCounter, item);
			userCounter++;
			System.out.println("Added user. List now contains:");
			System.out.println(users);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserById(int id) {
		if (USE_DATABASE) {
			// TODO Guard from SQL injection
			List<User> dataset = session.createQuery(
					"from User where USER_ID='" + Integer.toString(id) + "'")
					.list();
			return dataset.get(0);
		} else {
			return users.get(id);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserStatisticsItem findUserStatistics(String SID) {
		int userID = ServerController.authenticationServer.getUserWithSID(SID);
		// userID will be set to -1 if no such SID can be found. This is the
		// public user and as such Statistics should not work
		if (userID == -1) {
			return null;
		}
		if (USE_DATABASE) {
			// Creating a query and setting a parameter after.
			Query q = session.getNamedQuery("thisUserStatistics");
			q.setParameter("t_id", userID);
			List<UserStatisticsItem> dataset = q.list();
			return dataset.get(0);
		} else {
			// TODO for someone who cares about it
			return null;
		}
		
	}

}