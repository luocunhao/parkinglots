package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
/**
 *https://{xlink_host}/v2/parks/parkinglots/{parkinglot_id}/gates
 *接口对应的bean
 * */
@Data
@Getter
@Setter
public class GatesBasicInfoBean implements Serializable{
    @NotBlank
    private String sn;
    @NotNull
    @Valid
    private List<GatesBasicInfo> gates;
}
