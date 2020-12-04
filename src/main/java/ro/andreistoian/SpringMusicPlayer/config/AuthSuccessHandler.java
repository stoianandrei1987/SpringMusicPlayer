package ro.andreistoian.SpringMusicPlayer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ro.andreistoian.SpringMusicPlayer.models.User;
import ro.andreistoian.SpringMusicPlayer.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserService service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        String uiud = service.getUidByUserName(authentication.getName());
        log.info("Success handler called with : " + request.getParameterValues("u_name")[0]);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        //response.setHeader("success", "false");
       // response.getWriter().write("{\"success\": true}");
        response.getWriter().write("{\"success\":true, \"uid\":\""+uiud+"\"}");
        response.getWriter().flush();


    }
}
