package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping(value = "/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @PostMapping
    public Result addCurrencyController(@RequestBody Currency currency){
        return currencyService.addCurrency(currency);
    }

    // GET LIST, GET ONE, EDIT, DELETE
    @GetMapping
    public List<Currency> getCurrencyListController(){
        return currencyService.getCurrencyList();
    }

    @GetMapping(value = "/{currencyId}")
    public Currency getCurrencyController(@PathVariable Integer currencyId){
        return currencyService.getCurrency(currencyId);
    }

    @PutMapping(value = "/{currencyId}")
    public Result editCurrencyController(@PathVariable Integer currencyId, @RequestBody Currency currency){
        return currencyService.editCurrency(currencyId, currency);
    }

    @DeleteMapping(value = "/{currencyId}")
    public Result deleteCurrencyController(@PathVariable Integer currencyId){
        return currencyService.deleteCurrency(currencyId);
    }
}
