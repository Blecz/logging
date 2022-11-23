package com.lopan.logging;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LogStruct {

    private String flowName;
    private String url;

    @Getter(AccessLevel.NONE)
    private Long executionTimeMilis;

    @Getter(AccessLevel.NONE)
    private Integer httpStatusCode;

    private String info;
    private String error;

    public String getHttpStatusCode() {
        return this.httpStatusCode != null ? String.valueOf(this.httpStatusCode) : null;
    }

    public String getExecutionTimeMilis() {
        return this.executionTimeMilis != null ? String.valueOf(this.executionTimeMilis) : null;
    }

}
