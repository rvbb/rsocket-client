package com.rvbb.b2b.rsocket.client.dto.response;

import lombok.*;

import java.util.Date;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsFeed {
    private Integer randomId;
    private Date createdDate;
}
