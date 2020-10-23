package com.rvbb.b2b.rsocket.client.config;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.uri.UriTransportRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class RSocketRequesterConfig {

    @Value("${rsocket.server.demo-port}")
    private int rsocketDemoServerPort;

    @Value("${rsocket.server.demo-address}")
    private String rsocketServerAddress = "";


    /**
     * .frameDecoder(PayloadDecoder.ZERO_COPY)
     * .transport(TcpClientTransport.create(rsocketDemoServerPort))
     * .start()
     * .block();
     **/
    @Bean
    public RSocket rSocket() {
//        return RSocketFactory
//                .connect()
//                .mimeType(MimeTypeUtils.APPLICATION_JSON_VALUE, MimeTypeUtils.APPLICATION_JSON_VALUE)
//        in some case should use CBOR mimetype  .dataMimeType("application/cbor")
//                .mimeType(MetadataExtractor.ROUTING.toString(), MimeTypeUtils.APPLICATION_JSON_VALUE)
//        .dataMimeType(MimeTypeUtils.ALL_VALUE)

        return RSocketFactory
                .connect()
                .dataMimeType(WellKnownMimeType.APPLICATION_JSON.getString())
                .metadataMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString())
                .transport(UriTransportRegistry.clientForUri(rsocketServerAddress))
                .start()
                .block();
    }

    @Bean
    RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
        return RSocketRequester.wrap(rSocket(), MimeTypeUtils.APPLICATION_JSON, MimeTypeUtils.APPLICATION_JSON, rSocketStrategies);
    }
}
