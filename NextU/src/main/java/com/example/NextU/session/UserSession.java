package com.example.NextU.session;

import org.springframework.stereotype.Component;

import com.example.NextU.models.User;

import jakarta.servlet.http.HttpSession;

@Component
public class UserSession {
    private static final String USER_SESSION_ATTRIBUTE = "user";

    public void setUserSession(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_ATTRIBUTE, user);
    }

    public User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
    }

    public void invalidateSession(HttpSession session) {
        session.invalidate();
    }
}