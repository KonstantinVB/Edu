package ru.otus.hwork12.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PARAMETER_NAME = "login";
    public static final String PWD_PARAMETER_NAME = "pwd";
    public static final String AUTH_PARAMETER = "is_autorized";
    private static final String LOGIN_VARIABLE_NAME = "login";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";

    private Boolean IS_AUTORIZED;
    private String login;
    private String pwd;

    public LoginServlet(String login, String pwd, boolean is_autorized) {
        this.login = login;
        this.pwd = pwd;
        this.IS_AUTORIZED = is_autorized;
    }

    private static String getPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String requestLogin;
        String requestPwd;
        Boolean checkAuth = (Boolean) request.getSession().getAttribute(AUTH_PARAMETER);
        checkAuth = checkAuth != null ? checkAuth : false;
        if (checkAuth) {
            requestLogin = (String) request.getSession().getAttribute(LOGIN_PARAMETER_NAME);
            requestPwd = (String) request.getSession().getAttribute(PWD_PARAMETER_NAME);
        } else {
            requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
            requestPwd = request.getParameter(PWD_PARAMETER_NAME);
        }
        if (requestLogin != null && requestPwd != null && checkPwd(requestLogin, requestPwd)) {
            login = requestLogin;
            pwd = requestPwd;
            IS_AUTORIZED=true;
        } else {
            login = "unknown";
            pwd = "";
            IS_AUTORIZED=false;
        }
        saveToSession(request, login, pwd, IS_AUTORIZED); //request.getSession().getAttribute("login");
        String page = getPage(login); //save to the page
        response.getWriter().println(page);
        setOK(response);
    }

    private void saveToSession(HttpServletRequest request, String requestLogin, String requestPwd, Boolean is_autorized) {
        request.getSession().setAttribute("login", requestLogin);
        request.getSession().setAttribute("pwd", requestPwd);
        request.getSession().setAttribute("is_autorized", is_autorized);
    }

    public boolean checkPwd(String requestLogin, String requestPwd) {
        if (requestLogin.equals("admin") && requestPwd.equals("otus")) {
            return true;
        }
        return false;
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
//            saveToVariable(requestLogin);
//            saveToSession(request, requestLogin, requestPwd); //request.getSession().getAttribute("login");
//            saveToCookie(response, requestLogin, requestPwd); //request.getCookies();
//saveToServlet(request, requestLogin, requestPwd); //request.getAttribute("login");

    /*    private void saveToCookie(HttpServletResponse response, String requestLogin, String requestPwd) {
        response.addCookie(new Cookie("MyCache-login", requestLogin));
        response.addCookie(new Cookie("MyCache-pwd", requestPwd));
    }*/
/*
    private void saveToSession(HttpServletRequest request, String requestLogin,  String requestPwd) {
        request.getSession().setAttribute("login", requestLogin);
        request.getSession().setAttribute("pwd", requestPwd);
    }
*/
/*
    private void saveToServlet(HttpServletRequest request, String requestLogin, String requestPwd) {
        request.getServletContext().setAttribute("login", requestLogin);
        request.getServletContext().setAttribute("pwd", requestPwd);
    }

 */

/*    private void saveToVariable(String requestLogin) {
        login = requestLogin != null ? requestLogin : login;
    }*/
