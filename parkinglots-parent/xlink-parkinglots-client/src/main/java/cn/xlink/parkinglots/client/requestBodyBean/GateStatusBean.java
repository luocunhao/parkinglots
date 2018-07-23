package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 *https://{xlink_host}/v2/parks/parkinglots/{parkinglot_id}/gates/status
 *接口对应的bean
 * */
@Data
@Getter
@Setter
public class GateStatusBean implements Serializable{
    @NotBlank
    private String sn;
    @Valid
    @NonNull
    private List<GateStatus> gates;
}
