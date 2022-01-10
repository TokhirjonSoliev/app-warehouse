package uz.pdp.appwarehouse.payload;

import lombok.Data;

import java.util.Date;

@Data
public class OutputProductDto {

    private Integer productId;
    private double amount;
    private double price;
    private Integer outputId;
}
