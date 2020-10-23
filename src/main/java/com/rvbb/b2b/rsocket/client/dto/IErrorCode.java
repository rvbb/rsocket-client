package com.rvbb.b2b.rsocket.client.dto;

public interface IErrorCode {

    /*Rang of 0 to 200*/
    int OK = 200;
    int FAIL = 0;
    int SERVER_DOWN = 199;
    int NOT_FOUND = 198;
    int INVALID_INPUT = 197;
    int UNAUTHORIZED = 196;
    int UNAUTHENTICATED = 195;
    int NOT_PERMISSION = 194;
    int NOT_ALLOWED = 193;
    int DUPLICATED = 191;
    int SERVER_ERROR = 190;
    int UNSUPPORTED = 189;
    int EMPTY = 188;
    int TOO_LARGE = 187;
    int TOO_SMALL = 186;
    int UNKNOWN_REASON = 185;
    int UNAVAILABLE = 184;
    //add more
}
