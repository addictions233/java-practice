package com.one.bak.framework.spi;

import com.one.bak.framework.URL;
import com.one.bak.framework.protocol.Protocol;
import org.apache.dubbo.common.extension.ExtensionLoader;

public class DubboSpi {

    public static void main(String[] args) {

        ExtensionLoader<Protocol> extensionLoader =
                ExtensionLoader.getExtensionLoader(Protocol.class);

        // http
        Protocol protocol = extensionLoader.getExtension("http");

        protocol.start(new URL("localhost", 8080));
    }
}
