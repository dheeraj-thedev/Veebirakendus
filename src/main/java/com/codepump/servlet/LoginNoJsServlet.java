package com.codepump.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codepump.controller.ServerController;
import com.codepump.data.User;
import com.codepump.response.AuthenticationResponse;
import com.codepump.service.AuthenicationService;


@WebServlet(value = "/loginnojs")
public class LoginNoJsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static AuthenicationService authServ;

	@Override
	public void init() throws ServletException {
		super.init();
		authServ = ServerController.authenticationServer;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// For NOJS
		User user = new User("", req.getParameter("email"),
				req.getParameter("password"));
		user.hashPassword();
		AuthenticationResponse r = authServ.checkPassword(user);
		if (r.getResponse() == 0) {
			resp.sendRedirect("/login.html?nojs=true&result=nouser");
			return;
		} else if (r.getResponse() == 2) {
			resp.sendRedirect("/login.html?nojs=true&result=wrongpass");
			return;
		}
		
		// I AM THE COOKIEMONSTER
		Cookie cookie = new Cookie("SID", r.getSID());
		cookie.setMaxAge(24 * 60 * 60); // 24 h
		resp.addCookie(cookie);

		if (req.getParameter("nojs") != null) {
			resp.sendRedirect("/index.html?nojs=true");
		} else {
			resp.sendRedirect("/index.html");
		}
		return;
	}

}