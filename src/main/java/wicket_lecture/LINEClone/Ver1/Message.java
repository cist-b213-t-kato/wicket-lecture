package wicket_lecture.LINEClone.Ver1;

import java.io.Serializable;

public class Message implements Serializable{
    private String name;
    private String body;

    public Message(String name, String body){
        this.name = name;
        this.body = body;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setBody(String body){
        this.body = body;
    }

    public String getBody(){
        return this.body;
    }

}
