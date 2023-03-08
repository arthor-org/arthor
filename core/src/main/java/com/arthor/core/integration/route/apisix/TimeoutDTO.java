package com.arthor.core.integration.route.apisix;

public class TimeoutDTO {

    private Integer connect;
    private Integer send;
    private Integer read;

    public Integer getConnect() {
        return connect;
    }

    public void setConnect(Integer connect) {
        this.connect = connect;
    }

    public Integer getSend() {
        return send;
    }

    public void setSend(Integer send) {
        this.send = send;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

}
