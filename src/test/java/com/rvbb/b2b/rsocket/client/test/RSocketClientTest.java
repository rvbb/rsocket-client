package com.rvbb.b2b.rsocket.client.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.metadata.CompositeMetadataFlyweight;
import io.rsocket.metadata.TaggingMetadataFlyweight;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.uri.UriTransportRegistry;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class RSocketClientTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static RSocket rsocket;

    private final String routingServiceMethod = "com.rvbb.b2b.rsocket.client.service.CustomerService.getCustomerById";
    private static final String rsocketServerAddress = "ws://localhost:1001/rsocket-server";
    @BeforeAll
    public static void setUp() throws Exception {
        rsocket = RSocketFactory
                .connect()
                .dataMimeType(WellKnownMimeType.APPLICATION_JSON.getString())
                .metadataMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString())
                .transport(UriTransportRegistry.clientForUri(rsocketServerAddress))
                .start()
                .block();
    }

    @AfterAll
    public static void tearDown() {
        rsocket.dispose();
    }

    /*+
        There 3 type of RSocket communicate: request/response; fire & forget; stream
    **/
    @Test
    public void testReqRes() throws Exception {
        CompositeByteBuf compositeByteBuf = compositeMetadataWithRouting(routingServiceMethod);
        byte[] jsonData = objectMapper.writeValueAsBytes(1);
        System.out.println("jsonData=" + String.valueOf(jsonData));
        rsocket.requestResponse(DefaultPayload.create(Unpooled.wrappedBuffer(jsonData), compositeByteBuf))
                .subscribe(payload -> {
                    System.out.println(payload.getDataUtf8());
                });
//        Thread.sleep(2000);
    }

    private CompositeByteBuf compositeMetadataWithRouting(String routingKey) {
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        CompositeByteBuf compositeByteBuf = allocator.compositeDirectBuffer();
        ByteBuf routingMetadata = TaggingMetadataFlyweight.createTaggingContent(allocator, Collections.singletonList(routingKey));
        CompositeMetadataFlyweight.encodeAndAddMetadata(compositeByteBuf, allocator, WellKnownMimeType.MESSAGE_RSOCKET_ROUTING, routingMetadata);
        return compositeByteBuf;
    }
}
