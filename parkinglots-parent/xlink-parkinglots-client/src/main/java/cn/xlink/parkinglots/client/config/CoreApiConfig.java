package cn.xlink.parkinglots.client.config;



import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "core.api")
@Data
@Component
public class CoreApiConfig {

    /**
     * 校验前端传输的token
     */

    public static final String ACCESS_TOKEN_VALID_URL = "/v2/xlink/valid_token";

    public static final String API_HOST = "http://54.222.229.62:1080";//"http://api-test.xlink.io:1080";

    public static final String XLINK_ACCESS_TOKEN =
            "MERERTk1NkU3OTY2ODk5ODY0MENFMDc2NDZBRDc4NDMwOUU1MEU2MkQ1REU5MjkzNjUxMTI0MjBDOTcwMTA2Rg==";
    /**
     * API服务器
     */
    private String host;
    /**
     * API Xlink级别TOKEN
     */

    private String accessToken;
    /**
     * PROJECT 验证host
     * */
    private String projectHost;
    @Override
    public String toString() {
        return "CoreApiConfig [host=" + host + ", accessToken=" + accessToken + "]";
    }

}