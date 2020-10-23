package com.rvbb.b2b.rsocket.client.controller;

import com.rvbb.b2b.rsocket.client.dto.Customer;
import com.rvbb.b2b.rsocket.client.dto.response.NewsFeed;
import com.rvbb.b2b.rsocket.client.entity.CustomerEntity;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rsocket-client")
public class RSocketClientController {

//    @Autowired
    private final RSocketRequester rSocketRequester;

    public RSocketClientController(RSocketRequester requester){
        this.rSocketRequester = requester;
    }

    /*Request & Response*/
    @GetMapping(value = "/{customerId}")
    public Publisher<CustomerEntity> current(@PathVariable("customerId") Long id) {
        return rSocketRequester
                .route("getCustomer")
                .data(id)
                .retrieveMono(CustomerEntity.class);
    }

    /*Fire then Forget*/
    @PostMapping
    public Publisher<Void> addOne() {
        Customer customer = Customer.builder().name("Mr A").age(23).phone("12345").build();
        return rSocketRequester
                .route("addCustomer")
                .data(customer)
                .send();
    }

    /*Stream*/
    @GetMapping(value = "/stream/{customerId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<CustomerEntity> feed(@PathVariable("customerId") Long id) {
        return rSocketRequester
                .route("feedCustomer")
                .data(id)
                .retrieveFlux(CustomerEntity.class);
    }

    @GetMapping(value = "/stream/random", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<NewsFeed> feed() {
        return rSocketRequester
                .route("newFeed")
//                .data()
                .retrieveFlux(NewsFeed.class);
    }

}
