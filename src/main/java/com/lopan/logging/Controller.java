package com.lopan.logging;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private final MyLogger log = new MyLogger(this.getClass());

    @GetMapping(value = "/log")
    @ResponseStatus(HttpStatus.OK)
    public String olaMundo() {
        String flowName = "Ola Mundo";

        Random rand = new Random();

        StopWatch watch = log.start(flowName, "Iniciando processo");

        Integer sleep = rand.nextInt(10);

        if (sleep > 4 && sleep < 8) {
            log.warn(flowName, "Vai demorar...");
        } else if (sleep >= 8){
            log.error(flowName, "Vai demorar muito", "Tempo de espera de " + sleep + " segundos");
        }

        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            log.error(flowName, "Erro ao esperar por um tempo aleatorio", e.getMessage());
        }

        log.stop(watch, "Processo finalizado");

        log.success(flowName, "Concluido com sucesso");

        return "{\"mensagem\": \"Verifique seus logs\"}";
    }

}
