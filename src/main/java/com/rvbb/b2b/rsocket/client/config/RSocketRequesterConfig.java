package com.rvbb.b2b.rsocket.client.config;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import io.rsocket.uri.UriTransportRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;

@Configuration
public class RSocketRequesterConfig {

    @Value("${rsocket.server.demo-port}")
    private int rsocketDemoServerPort;

    @Value("${rsocket.server.websocket.demo-address}")
    private String rsocketServerAddress;


    /**
     * .frameDecoder(PayloadDecoder.ZERO_COPY)
     * .transport(TcpClientTransport.create(rsocketDemoServerPort))
     * .start()
     * .block();
     **/
    @Bean
    public RSocket rSocket() {
        return RSocketFactory
                .connect()
//                .dataMimeType(WellKnownMimeType.APPLICATION_JSON.getString())
//                .metadataMimeType(WellKnownMimeType.APPLICATION_JSON.getString())
                .dataMimeType(MediaType.APPLICATION_CBOR_VALUE)
                .metadataMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString())
                .transport(UriTransportRegistry.clientForUri(rsocketServerAddress))
                .start()
                .block();
    }

    @Bean
    RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
        return RSocketRequester.wrap(rSocket(), MediaType.APPLICATION_CBOR, MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString()), rSocketStrategies);
    }

}
