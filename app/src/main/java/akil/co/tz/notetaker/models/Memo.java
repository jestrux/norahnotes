package akil.co.tz.notetaker.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DevDept on 6/6/18.
 */

@Entity
public class Memo implements Serializable {
    private int id;
    private String title;
    private String body;
    private String recepientName;
    private int recepientId;
    private String senderName;
    private int senderId;
    private String type;
    private ArrayList<Attachment> attachments;

    public Memo(int id, String title, String body, String senderName, int senderId, String recepientName, int recepientId, String type, ArrayList<Attachment> attachments) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.recepientName = recepientName;
        this.recepientId = recepientId;

        this.senderName = senderName;
        this.senderId = senderId;
        this.type = type;

        this.attachments.clear();
        this.attachments.addAll(attachments);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRecepientName() {
        return recepientName;
    }

    public void setRecepientName(String recepientName) {
        this.recepientName = recepientName;
    }

    public int getRecepientId() {
        return recepientId;
    }

    public void setRecepientId(int recepientId) {
        this.recepientId = recepientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return body;
    }
}