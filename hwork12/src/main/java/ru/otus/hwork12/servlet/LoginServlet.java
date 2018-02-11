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

    public LoginServlet(String login, boolean is_autorized) {
        this.login = login;
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
                       HttpServletResponse response) throws IOException {
        String requestLogin=request.getParameter(LOGIN_PARAMETER_NAME);
        String requestPwd=request.getParameter(PWD_PARAMETER_NAME);
        Boolean checkAuth = (Boolean) request.getSession().getAttribute(AUTH_PARAMETER);
        checkAuth = checkAuth != null ? checkAuth : false;
        if (!checkAuth && requestLogin != null && requestPwd != null) {
            login = "unknown";
            IS_AUTORIZED=false;
            if (checkPwd(requestLogin, requestPwd)) {
                login = requestLogin;
                IS_AUTORIZED=true;
            }
        }
        saveToSession(request, login, IS_AUTORIZED); //request.getSession().getAttribute("login");
        String page = getPage(login); //save to the page
        response.getWriter().println(page);
        setOK(response);
    }

    private void saveToSession(HttpServletRequest request, String requestLogin, Boolean is_autorized) {
        request.getSession().setAttribute("login", requestLogin);
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
