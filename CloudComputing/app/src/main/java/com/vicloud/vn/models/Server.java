package com.vicloud.vn.models;

import com.vicloud.vn.R;

import java.io.Serializable;

/**
 * Created by huunc on 7/22/16.
 */
public class Server implements Serializable {
    private String serverName;
    private String createdAt;
    private String status;
    private String ip;
    private int iconStatus;
    private String serverID;
    private String imageName;
    private String addressMac;
    private String flavor;
    private String vmState;
    public Server(String serverName, String serverID, String status, String ip,
                  String imageName, String addressMac, String flavor, String vmState){
        this.serverName = serverName;
        this.serverID = serverID;
        this.status = status;
        this.ip = ip;
        this.imageName = imageName;
        this.addressMac = addressMac;
        this.flavor = flavor;
        this.vmState = vmState;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getAddressMac() {
        return addressMac;
    }

    public void setAddressMac(String addressMac) {
        this.addressMac = addressMac;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getVmState() {
        return vmState;
    }

    public void setVmState(String vmState) {
        this.vmState = vmState;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getIconStatus() {
        if (status.equals("Running"))
            return R.drawable.shape_running;
        else
            return R.drawable.shape_shutdown;
    }

    public void setIconStatus(int iconStatus) {
        this.iconStatus = iconStatus;
    }
}
