/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwts;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author MBSL2395
 */
public class MainFilter extends OncePerRequestFilter {

    private static final String JWT_TOKEN_COOKIE_NAME = "JWT-TOKEN";
    private static final String SIGNING_KEY = "signingKey";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // VAPT CR-494
        response.addHeader("X-Frame-Options", "SAMEORIGIN");
        response.addHeader("X-Content-Type-Options", "nosniff");
        response.addHeader("X-XSS-Protection", "1; mode=block");

        //read property file
        String path = "";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("sso.properties")) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                if (input != null) {
                    path = reader.readLine();
                }
            }
        }

        //create redirect path
        String cookieData = getSubject(request, JWT_TOKEN_COOKIE_NAME, SIGNING_KEY);
        String redirectPath = new StringBuffer("http://")
                .append(request.getServerName())
                .append(":").append(request.getServerPort())
                .append(request.getRequestURI()).toString();
        Cookie[] cookies = request.getCookies();
        String sessionId = "";
        if (null != cookies) {
            for (Cookie cooky : cookies) {
                if ("JSESSIONID".equals(cooky.getName())) {
                    sessionId = cooky.getValue();
                    System.out.println("User Session --------------------  " + sessionId);
                }
            }
        }
        HttpSession session = request.getSession();
        System.out.println("Http User Session --------------------  " + session.getId());
//        if (sessionId.equals(session.getId())) {
        if (null != cookieData) {
            String[] cookieArray = cookieData.split(":");
            String username = cookieArray[0];
            String bankcode = cookieArray[1];

            if (username == null || bankcode == null) {
                clear(response, request, JWT_TOKEN_COOKIE_NAME);
                request.getRequestDispatcher(new StringBuffer(path).append(redirectPath).toString()).forward(request, response);
            } else if (null == session.getAttribute("user")) {
                //interduce more details to session
                //such as ip browser, user details
                session.setAttribute("user", username);
                System.out.println("User - " + username);
                session.setAttribute("bank", bankcode);
                setMoreDetailsToSession(session, request);
                boolean isV = false;
                String setPath = request.getRequestURI();
                System.out.println("Path--" + setPath);
                if (setPath.equals("/velocity-admin/sign_out")) {
                    isV = true;
                } else if (setPath.equals("/velocity-admin/403")) {
                    isV = true;
                } else if (setPath.equals("/velocity-admin/")) {

                    isV = true;
                }
                if (!isV) {
//                if (true) {
                    if (null != session.getAttribute("paths")) {
                        System.out.println("Inside the if----");
                        if (is_Authorized(session, setPath)) {
                            filterChain.doFilter(request, response);
                        } else {
                            String headerName = request.getHeader("x-requested-with");
                            if (null == headerName) {
                                //Synchronous request

                                String forO3 = new StringBuffer("http://")
                                        .append(request.getServerName())
                                        .append(":").append(request.getServerPort()).append("/velocity-admin/403").toString();
                                response.sendRedirect(forO3);
                            } else {
                                //Ajax Request

                                response.getWriter().write("[You Don't have permission]");
                            }
                        }
                    } else {

                        System.out.println("Inside the else");
                        String forO3 = new StringBuffer("http://")
                                .append(request.getServerName())
                                .append(":").append(request.getServerPort()).append("/velocity-admin/").toString();
                        System.out.println("Server Name---" + forO3);
                        response.sendRedirect(forO3);
                    }
                } else {
                    filterChain.doFilter(request, response);
                }
            } else if (username.equals(session.getAttribute("user"))) {

                boolean isV = false;
                String setPath = request.getRequestURI();
                if (setPath.equals("/velocity-admin/sign_out")) {
                    isV = true;
                } else if (setPath.equals("/velocity-admin/403")) {
                    isV = true;
                } else if (setPath.equals("/velocity-admin/")) {
                    isV = true;
                }
                if (!isV) {
                    if (null != session.getAttribute("paths")) {
                        if (is_Authorized(session, setPath)) {
                            filterChain.doFilter(request, response);
                        } else {

                            String headerName = request.getAuthType();//Header("x-requested-with");
                            if (null == headerName) {
                                //Synchronous request

                                String forO3 = new StringBuffer("http://")
                                        .append(request.getServerName())
                                        .append(":").append(request.getServerPort()).append("/velocity-admin/403").toString();

                                response.sendRedirect(forO3);
                            } else {
                                //Ajax Request

                                response.getWriter().write("[You Don't have permission]");
                            }
                        }
                    } else {
                        String forO3 = new StringBuffer("http://")
                                .append(request.getServerName())
                                .append(":").append(request.getServerPort()).append("/velocity-admin/").toString();
                        response.sendRedirect(forO3);
                    }
                } else {
                    filterChain.doFilter(request, response);
                }
            } else {
                clear(response, request, JWT_TOKEN_COOKIE_NAME);
                response.sendRedirect(new StringBuffer(path).append(redirectPath).toString());
            }
        } else {
            clear(response, request, JWT_TOKEN_COOKIE_NAME);
            response.sendRedirect(new StringBuffer(path).append(redirectPath).toString());
        }

    }

    /**
     *
     * @param httpServletRequest
     * @param jwtTokenCookieName
     * @param signingKey
     * @return
     */
    private String getSubject(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey) {
        String token = getValue(httpServletRequest, jwtTokenCookieName);
        if (null == token) {
            return null;
        }
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     *
     * @param httpServletRequest
     * @param name
     * @return
     */
    private String getValue(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     *
     * @param httpServletResponse
     * @param name
     */
    private void clear(HttpServletResponse httpServletResponse, HttpServletRequest hsr, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setDomain(hsr.getServerName());
        httpServletResponse.addCookie(cookie);
    }

    private void setMoreDetailsToSession(HttpSession session, HttpServletRequest hsr) {
        session.setAttribute("IP", hsr.getRemoteAddr());

        String browserDetails = hsr.getHeader("User-Agent");
        String userAgent = browserDetails;
        String user = userAgent.toLowerCase();

        String os;
        String browser = "";
        //os
        if (userAgent.toLowerCase().contains("windows")) {
            os = "Windows";
        } else if (userAgent.toLowerCase().contains("mac")) {
            os = "Mac";
        } else if (userAgent.toLowerCase().contains("x11")) {
            os = "Unix";
        } else if (userAgent.toLowerCase().contains("android")) {
            os = "Android";
        } else if (userAgent.toLowerCase().contains("iphone")) {
            os = "IPhone";
        } else {
            os = "UnKnown, More-Info: " + userAgent;
        }
        //browser
        if (user.contains("msie")) {
            String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr") || user.contains("opera")) {
            if (user.contains("opera")) {
                browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            } else if (user.contains("opr")) {
                browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
            }
        } else if (user.contains("chrome")) {
            browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6")) || (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) || (user.contains("mozilla/4.08")) || (user.contains("mozilla/3"))) {
            browser = "Netscape-?";

        } else if (user.contains("firefox")) {
            browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("rv")) {
            browser = "IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
        } else {
            browser = "UnKnown, More-Info: " + userAgent;
        }
        session.setAttribute("Browser", browser);
        session.setAttribute("OS", os);

    }

    private boolean is_Authorized(HttpSession session, String path) {
        boolean isValied = false;
        if (!path.matches(".*(css|jpg|png|gif|js|map|eot|svg|ttf|woff|woff2)$")) {
            if (session.getAttribute("Browser").toString().contains("Chrome")) {
                JsonParser jp = new JsonParser();

                JsonArray arrays = (JsonArray) jp.parse(session.getAttribute("paths").toString());

                if (null != arrays) {
                    for (int i = 0; i < arrays.size(); i++) {
                        if (arrays.get(i).getAsString().equals(path)) {
                            isValied = true;
                            break;
                        }
                    }
                }
            } else {
                isValied = false;
            }
        } else {
            isValied = true;
        }
        return isValied;
    }
}
