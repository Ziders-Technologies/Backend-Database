package Datastore.Datastore;

public class Data {
    private String id;
    private String  key;
    private String value;
    private String ttl;
    private String addedTime;

    public Data() {
    }

    public Data(String id, String key, String value,String ttl,String addedTime) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.ttl = ttl;
        this.addedTime = addedTime;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getAddedTime() { return addedTime; }

    public void setAddedTime(String addedTime) { this.addedTime = addedTime; }


}
