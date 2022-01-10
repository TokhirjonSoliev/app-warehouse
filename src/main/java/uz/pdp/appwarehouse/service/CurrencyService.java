package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    public Result addCurrency(Currency currency){
        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName){
            return new Result("Bunday currency birligi mavjud", false);
        }
        currencyRepository.save(currency);
        return new Result("Muvaffaqiyatli qoshildi", true);
    }

    public List<Currency> getCurrencyList(){
        List<Currency> currencyList = currencyRepository.findAll();
        return currencyList;
    }

    public Currency getCurrency(Integer currencyId){
        Optional<Currency> optionalCurrency = currencyRepository.findById(currencyId);
        return optionalCurrency.orElse(null);
    }

    public Result editCurrency(Integer currencyId, Currency currency){
        Optional<Currency> optionalCurrency = currencyRepository.findById(currencyId);
        if (optionalCurrency.isPresent()){
            Currency currency1 = optionalCurrency.get();
            currency1.setName(currency.getName());
            currencyRepository.save(currency1);
            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday currency mavjud emas", false);
    }

    public Result deleteCurrency(Integer currencyId){
        Optional<Currency> optionalCurrency = currencyRepository.findById(currencyId);
        if (optionalCurrency.isPresent()){
            currencyRepository.deleteById(currencyId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday currency mavjud emas", false);
    }
}
