package uz.onlineshop.authservice.req;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
public class RequestContext implements Serializable {
    private Integer partnerId;
    private String uid;
    private String phone;
    private String realIp;
    private String forwardedForIp;
    private String userAgent;
    private String host;
    private String soapAction;
    private String requestData;
    private String responseData;
    private String deviceId;
    private String deviceName;
    private Integer status;
    private Date responseTime;
    private Long requestTimeInLong;
    private Long responseTimeInLong;
    private Date createdAt;
    private String userName;
    private String language;

}
