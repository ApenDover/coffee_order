package ts.andrey.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ts.andrey.rest.ClientEndpoint;
import ts.andrey.service.DataProcessor;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final DataProcessor dataProcessor;


    @GetMapping(ClientEndpoint.MAIN_PAGE)
    public String index(Model model) {
        dataProcessor.getMenu(model);
        return "menu";
    }

    @GetMapping(ClientEndpoint.BARISTA_PAGE)
    public String barista(Model model) {
        dataProcessor.getBarista(model);
        return "barista";
    }

    @GetMapping(ClientEndpoint.NEW_ORDER)
    public String newOrder(Model model, @RequestParam("comment") String comment) {
        dataProcessor.newOrder(model, comment);
        return "orderCreate";
    }

    @GetMapping(ClientEndpoint.CLOSE_ORDER)
    public String updateOrder(@PathVariable("id") int id) {
        dataProcessor.closeOrder(id);
        return "redirect:/barista";
    }

    @GetMapping(ClientEndpoint.SET_MILK)
    public String setMilk(@PathVariable("id") int id) {
        dataProcessor.setMilk(id);
        return ClientEndpoint.REDIRECT_TO_HOME;
    }

    @GetMapping(ClientEndpoint.SET_SYRUP)
    public String setSyrup(@PathVariable("id") int id) {
        dataProcessor.setSyrup(id);
        return ClientEndpoint.REDIRECT_TO_HOME;
    }

    @GetMapping(ClientEndpoint.SET_DRINK)
    public String setDrink(@PathVariable("id") int id) {
        dataProcessor.setDrink(id);
        return ClientEndpoint.REDIRECT_TO_HOME;
    }

    @GetMapping(ClientEndpoint.SET_DESSERT)
    public String setDessert(@PathVariable("id") int id) {
        dataProcessor.setDessert(id);
        return ClientEndpoint.REDIRECT_TO_HOME;
    }

}
