package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    @Autowired
    private MealRestController controller;

    @PostMapping("/meals")
    public String createUpdateMeal(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
            LocalDateTime.parse(request.getParameter("dateTime")),
            request.getParameter("description"),
            Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.isEmpty(request.getParameter("id"))) {
            controller.create(meal);
        } else {
            controller.update(meal, getId(request));
        }
        return "redirect:meals";
    }

//    @PutMapping("/meals?action=update")
//    public String updateMeal(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
//        request.setCharacterEncoding("UTF-8");
//        Meal meal = new Meal(
//                LocalDateTime.parse(request.getParameter("dateTime")),
//                request.getParameter("description"),
//                Integer.parseInt(request.getParameter("calories")));
//
//        controller.update(meal, getId(request));
//        return "redirect:meals";
//    }
//
//    @PostMapping("/meals?action=create")
//    public String addMeal(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
//        request.setCharacterEncoding("UTF-8");
//        controller.create(new Meal());
//        return "redirect:meals";
//    }

    @GetMapping("/meals")
    public String getMealsMapping(HttpServletRequest request, Model model) {

        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(request);
                controller.delete(id);
                return "redirect:meals";
            }
            case "create", "update" -> {
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                model.addAttribute("meal", meal);
//                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                return "/mealForm";
            }
            case "filter" -> {
                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
//                request.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
                model.addAttribute("meals", controller.getBetween(startDate, startTime, endDate, endTime));
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                return "/meals";
            }
            default -> {
                model.addAttribute("meals", controller.getAll());
                return "meals";
            }
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }


}
