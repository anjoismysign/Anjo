package me.anjoismysign.anjo.entities;

public record ServerDetails(String host, int port) {
    public String toAddress() {
        return host + ":" + port;
    }
}