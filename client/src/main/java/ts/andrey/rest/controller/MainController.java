package ts.andrey.rest.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ts.andrey.rest.ClientEndpoint;
import ts.andrey.rest.UserSession;
import ts.andrey.service.DataProcessor;
import ts.andrey.utils.CafeOrderUtil;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final DataProcessor dataProcessor;

    @GetMapping(ClientEndpoint.MAIN_PAGE)
    public String index(Model model, HttpSession session) {
        dataProcessor.getMenu(model, session);
        return "menu";
    }

    @GetMapping(ClientEndpoint.BARISTA_PAGE)
    public String barista(Model model, HttpSession session) {
        dataProcessor.getBarista(model);
        return "barista";
    }

    @GetMapping(ClientEndpoint.NEW_ORDER)
    public String newOrder(Model model, @RequestParam("comment") String comment, HttpSession session) {
        log.info("{}: new order", session.getId());
        if (CafeOrderUtil.isEmpty(getUserSession(session).getCafeOrderDto())) {
            return "redirect:/";
        }
        dataProcessor.newOrder(model, comment, getUserSession(session));
        CafeOrderUtil.clear(getUserSession(session).getCafeOrderDto());
        return "orderCreate";
    }

    @GetMapping(ClientEndpoint.CLOSE_ORDER)
    public String updateOrder(@PathVariable("id") int id, HttpSession session) {
        CafeOrderUtil.clear(getUserSession(session).getCafeOrderDto());
        dataProcessor.closeOrder(id);
        return "redirect:/barista";
    }

    @GetMapping(ClientEndpoint.SET_MILK)
    public String setMilk(@PathVariable("id") int id, HttpSession session) {
        log.info("{}: set milk {}", session.getId(), id);
        dataProcessor.setMilkDto(id, getUserSession(session));
        return ClientEndpoint.REDIRECT_TO_HOME;
    }

    @GetMapping(ClientEndpoint.SET_SYRUP)
    public String setSyrup(@PathVariable("id") int id, HttpSession session) {
        log.info("{}: set syrup {}", session.getId(), id);
        dataProcessor.setSyrupDto(id, getUserSession(session));
        return ClientEndpoint.REDIRECT_TO_HOME;
    }

    @GetMapping(ClientEndpoint.SET_DRINK)
    public String setDrink(@PathVariable("id") int id, HttpSession session) {
        log.info("{}: set drink {}", session.getId(), id);
        dataProcessor.setDrinkDto(id, getUserSession(session));
        return ClientEndpoint.REDIRECT_TO_HOME;
    }

    @GetMapping(ClientEndpoint.SET_DESSERT)
    public String setDessert(@PathVariable("id") int id, HttpSession session) {
        log.info("{}: set desert {}", session.getId(), id);
        dataProcessor.setDessertDto(id, getUserSession(session));
        return ClientEndpoint.REDIRECT_TO_HOME;
    }

    private UserSession getUserSession(HttpSession session) {
        return (UserSession) session.getAttribute("userSession");
    }

}
