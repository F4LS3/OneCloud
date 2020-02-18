package de.f4ls3.netty.impl;

public enum ConfirmationType {

    /*
    * Server -> Client
    * */
    AUTH,

    /*
    * Client -> Server
    * */
    GROUP_CREATED,
    GROUP_STARTED,
    GROUP_STOPPED;

}
