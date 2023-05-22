package ch.hearc.malabar_jokes;

import java.io.Serializable;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Log implements Serializable {
    private String level;
    private String message;
    private String datetime;

    public Log(@JsonProperty("level") String level, @JsonProperty("message") String message) {
        this.level = level;
        this.message = message;
        this.datetime = new java.util.Date().toString();
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Log [level=" + level + ", message=" + message + ", datetime=" + datetime + "]";
    }
}
