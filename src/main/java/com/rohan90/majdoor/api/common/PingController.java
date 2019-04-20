package com.rohan90.majdoor.api.common;

import com.rohan90.majdoor.api.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.ENTRY_POINT + ApiConstants.Ping.BASE)
public class PingController {

    @Autowired
    private AppDetails appDetails;

    @GetMapping(ApiConstants.Ping.INDEX)
    public RestResponse<String> pingCheck() {
        return new RestResponse<String>(true, null, "hello, im up ," + appDetails);
    }

}
