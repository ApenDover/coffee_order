package ts.andrey.confirguration.property;

import lombok.Data;

@Data
public class SslProperties {

    private String keyStore;

    private char[] storePassword;

    private char[] keyStorePassword;

    private String protocol = "TLSv1.2";

}
