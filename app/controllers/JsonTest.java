package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.mail.SimpleEmail;
import play.cache.Cache;
import play.libs.Mail;
import play.mvc.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ahmad Arif on 14-Mar-16.
 */
 public class JsonTest extends Controller {

    public static void index() {
        String username = Cache.get("username") == null ? null : Cache.get("username").toString();
        if(username != null) {
            session.put("username", username);
        }

        if(session.get("username") == null) {
            login();
        }

        String name = params.get("name");
        int age = params.get("age", Integer.class);

        System.out.println("Name = " + name);
        System.out.println("Age  = " + (age + 10));

        Map map = new HashMap();
        map.put("status", "success");
        map.put("message", "you know me so well!");
        renderJSON(map);

        JsonObject object = new JsonObject();
        object.addProperty("hitut", 204);
//        renderJSON(object);
    }

    public static void login() {
        if(session.get("username") != null) {
            index();
        }
        render();
    }

    public static void checkLogin(String username) {
        validation.required(username);
        validation.minSize(username, 6);

        if(validation.hasErrors()) {
//            render("JsonTest/login.html");
            render("@login");
        }

        session.put("username", username);
        Cache.set("username", username);

        index();
    }

    public static void logout() {
        session.clear();
        Cache.clear();
        login();
    }

}