package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@Getter
@Setter
public class GatesBasicInfo implements Serializable{
    @NotBlank
    private String sn;
    @NotBlank
    private String name;
}
