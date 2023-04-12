package com.mbsl.controller;

import com.google.gson.JsonArray;
import com.mbsl.service.CommonService;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableAutoConfiguration
public class WebController {

    private static final String JWT_TOKEN_COOKIE_NAME = "JWT-TOKEN";

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/add_user", method = RequestMethod.GET)
    public String addUser(HttpSession session) {
        if (load(session)) {
            return "addUser";
        } else {
            return "403";
        }
    }

    @RequestMapping("/")
    String root(HttpSession session) {
        
        if (load(session)) {
            return "index";
        } else {
            return "403";
        }
    }

    @RequestMapping("user_transfer")
    public String userTransfer(HttpSession session) {
        if (load(session)) {
            return "userTransfer";
        } else {
            return "403";
        }
    }

    @RequestMapping("repot_gen")
    public String generateReport(HttpSession session) {
        if (load(session)) {
            return "generateReport";
        } else {
            return "403";
        }
    }

@RequestMapping("t4s_access")
    public String t4sAccess(HttpSession session) {
        if (load(session)) {
            return "t4sAccess";
        } else {
            return "403";
        }
    }

    @RequestMapping("create_email")
    public String createEmail(HttpSession session) {
        if (load(session)) {
            return "createEmail";
        } else {
            return "403";
        }
    }

    @RequestMapping("hr_table_update")
    public String hrUpdate(HttpSession session) {
        if (load(session)) {
            return "hrUpdate";
        } else {
            return "403";
        }
    }

@RequestMapping("user_inactivation")
    public String userInactivation (HttpSession session) {
        if (load(session)) {
            return "userInactivation";
        } else {
            return "403";
        }
    }

@RequestMapping("temp_access")
    public String tempAccess (HttpSession session) {
        if (load(session)) {
            return "tempAccess";
        } else {
            return "403";
        }
    }

@RequestMapping("access_config")
    public String accessConfig (HttpSession session) {
        if (load(session)) {
            return "accessCofig";
        } else {
            return "403";
        }
    }

@RequestMapping("aprove_reqst")
    public String aproveReqst (HttpSession session) {
        if (load(session)) {
            return "approveReqst";
        } else {
            return "403";
        }
    }

@RequestMapping("aprove_path")
    public String approvalPath (HttpSession session) {
        if (load(session)) {
            return "approvePath";
        } else {
            return "403";
        }
    }

@RequestMapping("profile_create")
    public String profileCreate (HttpSession session) {
        if (load(session)) {
            return "createProfile";
        } else {
            return "403";
        }
    }

@RequestMapping("teller_create")
    public String tellerCreate (HttpSession session) {
        if (load(session)) {
            return "createTeller";
        } else {
            return "403";
        }
    }

@RequestMapping("designation_create")
    public String designationCreate (HttpSession session) {
        if (load(session)) {
            return "createDesignation";
        } else {
            return "403";
        }
    }

    @RequestMapping("/index")
    String home(HttpSession session) {
        if (load(session)) {
            return "index";
        } else {
            return "403";
        }
    }

    @RequestMapping("/403")
    String errorPage(HttpSession session) {
        return "403";
    }

    @RequestMapping("/sign_out")
    String invalied(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        session.invalidate();
        Cookie cookie = new Cookie(JWT_TOKEN_COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setDomain(request.getServerName());
        response.addCookie(cookie);

        return "redirect:/index";
    }

    private boolean load(HttpSession session) {
        session.setMaxInactiveInterval(90 * 60);
        if (null != session.getAttribute("user") && null != session.getAttribute("bank")) {
            if (null == session.getAttribute("branch")) {
                String userName = session.getAttribute("user").toString();
                String bnk = session.getAttribute("bank").toString();
                try {
                    JsonArray array = commonService.getMyBranch(userName, bnk);
                    session.setAttribute("branch", array.get(0).getAsString());
                    session.setAttribute("full_name", array.get(1).getAsString());
                    session.setAttribute("designation", array.get(2).getAsString());
                    session.setAttribute("display_name", array.get(3).getAsString());
                    session.setAttribute("dept_branch", array.get(4).getAsString());
                    session.setAttribute("paths", array.get(5).getAsJsonArray());

                    System.out.println("Path--------"+array.get(5).getAsJsonArray());
                    return true;

                } catch (Exception ex) {
                    Logger.getLogger(WebController.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}