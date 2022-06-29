package com.suyal.itclimiteds.modal;

public class Message {
    String UID;
    String message;
    String name;

    public Message(){}

    public Message(String UId,String message) {
//        this.UID = UID;
        this.message = message;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

