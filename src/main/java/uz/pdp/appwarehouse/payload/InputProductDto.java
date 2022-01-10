package uz.pdp.appwarehouse.payload;

import lombok.Data;
import uz.pdp.appwarehouse.entity.Input;


import java.util.Date;

@Data
public class InputProductDto {

    private Integer productId;
    private double amount;
    private double price;
    private Date expireDate;
    private Integer inputId;
}
