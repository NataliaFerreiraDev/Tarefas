package br.com.devsimples.tarefas.controller;

import br.com.devsimples.tarefas.entity.Tarefa;
import br.com.devsimples.tarefas.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping("/tarefas")
    public ResponseEntity<List<Tarefa>> listarTodas(){
        return ResponseEntity.ok(tarefaService.listar());
    }

}
