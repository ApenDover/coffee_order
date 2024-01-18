package ts.andrey.common.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FixUrl {

    public String enterParamsIfNeed(String url, String params) {
        if (url.contains("{")) {
            url = url.substring(0, url.indexOf('{')) + params;
        }
        return url;
    }

}
