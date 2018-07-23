package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Setter
@Getter
public class GateStatus implements Serializable{
    @NotBlank
    private String sn;
    @NotNull
    @Min(1)
    @Max(6)
    private int status;
    @NotBlank
    private String status_time;
}
