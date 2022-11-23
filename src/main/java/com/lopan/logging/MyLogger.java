package com.lopan.logging;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.util.StopWatch;

import java.util.List;

@Getter
@Setter
public class MyLogger {

    private Logger logger;

    private static final Level SUCCESS = Level.forName(LogLevel.SUCCESS.toString(), 200);

    public MyLogger(Class clazz) {
        logger = LogManager.getLogger(clazz.getName());
    }

    public void writeLog(LogLevel level, LogStruct log)
    {
        ThreadContext.clearMap();
        ThreadContext.put("flow.name", log.getFlowName());
        ThreadContext.put("url", log.getUrl());
        ThreadContext.put("http.status", log.getHttpStatusCode());
        ThreadContext.put("execution.time", log.getExecutionTimeMilis());
        ThreadContext.put("info", log.getInfo());
        ThreadContext.put("error", log.getError());

        switch (level) {
            case INFO:
                logger.info(log.getInfo());
                break;
            case WARN:
                logger.warn(log.getInfo());
                break;
            case ERROR:
                logger.error("{} {}", log.getInfo(), log.getError());
                break;
            case SUCCESS:
                logger.log(SUCCESS, log.getInfo());
            default:
                logger.debug(log.getInfo());
                break;
        }
    }

    public void info(String flowName, String info) {
        writeLog(LogLevel.INFO, LogStruct.builder().info(info).build());
    }

    public void info(String flowName, String info, Long executionTime) {
        writeLog(LogLevel.INFO, LogStruct.builder()
                .flowName(flowName)
                .info(info)
                .executionTimeMilis(executionTime)
                .build());
    }

    public void warn(String flowName, String info) {
        writeLog(LogLevel.WARN, LogStruct.builder()
                .flowName(flowName)
                .info(info)
                .build());
    }

    public void error(String flowName, String info, String error) {
        writeLog(LogLevel.ERROR, LogStruct.builder()
                .flowName(flowName)
                .info(info)
                .error(error)
                .build());
    }

    public void success(String flowName, String info) {
        writeLog(LogLevel.SUCCESS, LogStruct.builder()
                .flowName(flowName)
                .info(info)
                .httpStatusCode(200)
                .build());
    }

    public StopWatch start(String flowName, String info) {
        writeLog(LogLevel.INFO, LogStruct.builder()
                .flowName(flowName)
                .info(info)
                .build());
        StopWatch watch = new StopWatch(flowName);
        watch.start();
        return watch;
    }

    public void stop(StopWatch watch, String info) {
        watch.stop();
        writeLog(LogLevel.INFO, LogStruct.builder()
                .flowName(watch.getId())
                .info(info)
                .executionTimeMilis(watch.getTotalTimeMillis())
                .build());
    }

}
