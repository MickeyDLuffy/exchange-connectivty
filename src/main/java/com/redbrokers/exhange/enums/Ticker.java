package com.redbrokers.exhange.enums;

public enum Ticker {
    MSFT("msft"),
    NFLX("nflx"),
    GOOGL("googl"),
    TSLA("tsla"),
    AAPL("aapl"),
    IBM ("ibm"),
    ORCL ("orcl"),
    AMZN ("amzn");

    private final String ticker;
    Ticker(String ticker){
        this.ticker = ticker;
    }


}
